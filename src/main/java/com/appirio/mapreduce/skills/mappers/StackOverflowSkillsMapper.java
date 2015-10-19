package com.appirio.mapreduce.skills.mappers;

/**
 * Created by rpemmaraju on 9/16/15.
 */

import com.appirio.mapreduce.skills.TagHelper;
import com.appirio.mapreduce.skills.TagHelperFactory;
import com.appirio.mapreduce.skills.pojo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.*;


public class StackOverflowSkillsMapper extends Mapper<LongWritable, Text, Text, Text> {

    private static final ObjectMapper mapper = new ObjectMapper();
    private TagHelper tagHelper;
    private Log log;

    @Override
    public void setup(Context context) throws IOException, InterruptedException {
        log = LogFactory.getLog(this.getClass());
        tagHelper = TagHelperFactory.getHelper();
        tagHelper.init(context);
    }

    private static final double defaultStackOverflowSkillWeight = 1.0;

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // convert to pojo
        StackOverflowSkillRecord skillRec = mapper.readValue(value.getBytes(), StackOverflowSkillRecord.class);

        // create key
        String outKeyStr = String.valueOf(skillRec.getUserId());
        Text outKey = new Text(outKeyStr);

        log.error("UserId : "+skillRec.getUserId());
        String topTags = skillRec.getTopTags();
        List<String> tagsList = new ArrayList<String>();
        if (topTags != null && !topTags.equals("")) {
            tagsList = Arrays.asList(topTags.split(","));

            log.error("Number of Tags: " + tagsList.size());
            for (String tag : tagsList) {
                Long tagId = tagHelper.getTagId(tag);
                if (tagId != null) {
                    MappedSkill skill = new MappedSkill();
                    skill.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.EXTERNAL_STACKOVERFLOW)));
                    skill.setTagId(tagId);
                    skill.setWeight(defaultStackOverflowSkillWeight);

                    Text val = new Text(mapper.writeValueAsBytes(skill));
                    context.write(outKey, val);
                } else {
                    log.error("[StackOverflow] Unable to retrieve TagId for '" + tag.toString() + "'. Skipping");
                }
            }
        } else {
            log.error("[StackOverflow] No Tags found for User:"+skillRec.getUserId());
        }
    }

}