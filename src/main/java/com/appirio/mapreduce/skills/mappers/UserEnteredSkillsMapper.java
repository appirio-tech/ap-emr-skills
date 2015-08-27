package com.appirio.mapreduce.skills.mappers;

/**
 * Created by parthshah on 8/23/15.
 */

import com.appirio.mapreduce.skills.pojo.MappedSkill;
import com.appirio.mapreduce.skills.pojo.SkillSource;
import com.appirio.mapreduce.skills.pojo.UserEnteredSkill;
import com.appirio.mapreduce.skills.pojo.UserEnteredSkillRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


public class UserEnteredSkillsMapper extends Mapper<LongWritable, Text, Text, Text> {

    private static final ObjectMapper mapper = new ObjectMapper();
    private Log log;

    @Override
    public void setup(Context context) throws IOException, InterruptedException {
        log = LogFactory.getLog(this.getClass());
    }

    private static final double defaultUserSkillWeight = 1.0;

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // convert to pojo
        UserEnteredSkillRecord skillRec = mapper.readValue(value.getBytes(), UserEnteredSkillRecord.class);

        // create key
        String outKeyStr = skillRec.getUserId() + ":" + skillRec.getUserHandle();
        Text outKey = new Text(outKeyStr);

        // map
        for (UserEnteredSkill s : skillRec.getSkills()) {
            if (!s.isHidden()) {
                MappedSkill skill = new MappedSkill();
                skill.setTagId(s.getTagId());
                // defaulting the weight to 1
                skill.setWeight(defaultUserSkillWeight);
                skill.setSource(SkillSource.USER_ENTERED);
                Text val = new Text(mapper.writeValueAsBytes(skill));
                context.write(outKey, val);
            }
        }
    }

}