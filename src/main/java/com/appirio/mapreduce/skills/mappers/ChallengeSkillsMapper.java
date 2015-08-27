package com.appirio.mapreduce.skills.mappers;

import com.appirio.mapreduce.skills.pojo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.List;

/**
 * Created by parthshah on 8/24/15.
 */
public class ChallengeSkillsMapper extends Mapper<LongWritable, Text, Text, Text> {

    protected static Log log;
    private static final ObjectMapper mapper = new ObjectMapper();

    public ChallengeSkillsMapper() {
        log  = LogFactory.getLog(this.getClass());
    }

    @Override
    public void setup(Context context) throws IOException, InterruptedException {
    }

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        ChallengeSkillRecord skillRec = mapper.readValue(value.getBytes(), ChallengeSkillRecord.class);

        // create key
        String outKeyStr = skillRec.getUserId() + ":" + skillRec.getUserHandle();
        Text outKey = new Text(outKeyStr);

        for (ChallengeSkill skill: skillRec.getSkills()) {
            MappedSkill mSkill = new MappedSkill();
            mSkill.setSource(SkillSource.CHALLENGE);
            mSkill.setTagId(skill.getTagId());
            mSkill.setWeight(calculateWeight(skill.getContests()));

            Text val = new Text(mapper.writeValueAsBytes(mSkill));
            context.write(outKey, val);
        }

    }

    private double calculateWeight(List<Contest> contests) {
        double weight = 0;
        for(Contest c: contests) {
            weight += c.getWeight();
        }
        return weight;
    }
}
