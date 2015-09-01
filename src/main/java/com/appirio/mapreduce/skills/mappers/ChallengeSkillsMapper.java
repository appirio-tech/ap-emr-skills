package com.appirio.mapreduce.skills.mappers;

import com.appirio.mapreduce.skills.TagHelper;
import com.appirio.mapreduce.skills.TagHelperFactory;
import com.appirio.mapreduce.skills.pojo.MappedSkill;
import com.appirio.mapreduce.skills.pojo.SkillSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by parthshah on 8/24/15.
 */
public class ChallengeSkillsMapper extends Mapper<LongWritable, Text, Text, Text> {

    protected static Log log;
    private static final ObjectMapper mapper = new ObjectMapper();
    private TagHelper tagHelper;

    public ChallengeSkillsMapper() {
        log  = LogFactory.getLog(this.getClass());
    }

    @Override
    public void setup(Context context) throws IOException, InterruptedException {
        tagHelper = TagHelperFactory.getHelper();
        tagHelper.init(context);
    }

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // 152342,kyky,7437797,Java
        String[] inText = value.toString().split(",");

        String outKeyStr = inText[0] + ":" + inText[1];
        Text outKey = new Text(outKeyStr);

        MappedSkill mSkill = new MappedSkill();
        mSkill.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.CHALLENGE)));
        mSkill.setHidden(false);
        mSkill.setTagId(tagHelper.getTagId(inText[3]));
        mSkill.setWeight(1);

        Text val = new Text(mapper.writeValueAsBytes(mSkill));
        context.write(outKey, val);
    }
}
