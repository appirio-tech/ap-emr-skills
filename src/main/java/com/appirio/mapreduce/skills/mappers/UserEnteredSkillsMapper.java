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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;


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
        String outKeyStr = skillRec.getUserId();
        Text outKey = new Text(outKeyStr);

        Map<Long, UserEnteredSkill> skillsMap = mapper.readValue(
                skillRec.getSkills(),
                mapper.getTypeFactory().constructMapType(Map.class, Long.class, UserEnteredSkill.class));

        // re-using the same skill object since we are adding values as Text by converting to string
        log.error("MapSize: " + skillsMap.size());
        for (Map.Entry<Long, UserEnteredSkill> entry: skillsMap.entrySet()) {
            log.error("Entry: "+ entry.getKey());
            MappedSkill skill = new MappedSkill();
            skill.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.USER_ENTERED)));
            // defaulting the weight to 1
            skill.setWeight(defaultUserSkillWeight);
            skill.setTagId(entry.getKey());
            skill.setHidden(entry.getValue().isHidden());
            Text val = new Text(mapper.writeValueAsBytes(skill));
            context.write(outKey, val);
        }
    }

}