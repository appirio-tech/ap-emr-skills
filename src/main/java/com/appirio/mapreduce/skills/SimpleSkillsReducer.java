package com.appirio.mapreduce.skills;

import com.appirio.mapreduce.skills.pojo.AggregatedSkill;
import com.appirio.mapreduce.skills.pojo.MappedSkill;
import com.appirio.mapreduce.skills.pojo.UserAggregatedSkills;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

/**
 * Created by parthshah on 8/23/15.
 */
public class SimpleSkillsReducer extends Reducer<Text, Text, Text, NullWritable> {
    public final static ObjectMapper mapper = new ObjectMapper();

    protected static Log log;
    public SimpleSkillsReducer() {
        log = LogFactory.getLog(this.getClass());
    }



    @Override
    protected void reduce(Text key, Iterable<Text> skills, Context context)
        throws IOException, InterruptedException {
        Iterator<Text> itr = skills.iterator();
        HashMap<Long, Double> skillMap = new HashMap<Long, Double>();

        while(itr.hasNext()) {
            MappedSkill skill = mapper.readValue(itr.next().getBytes(), MappedSkill.class);
            skillMap.compute(skill.getTagId(), (k,v) -> v == null ? skill.getWeight(): skill.getWeight()+v);
//            long tagId = skill.getTagId();
//            skillMap.put(tagId, skillMap.containsKey(tagId) ? skillMap.get(tagId)+skill.getWeight() : skill.getWeight());
        }

        // Create output object
        List<AggregatedSkill> aggregatedSkills = new ArrayList<AggregatedSkill>();
//        skillMap.forEach((k,v) -> aggregatedSkills.add(new AggregatedSkill(k,v)));
        for (Map.Entry<Long, Double> entry: skillMap.entrySet()) {
            aggregatedSkills.add(new AggregatedSkill(entry.getKey(), entry.getValue()));
        }

        // tokenize key
        String inKey[] = key.toString().split(":", 2);
        assert(inKey.length == 2);

        UserAggregatedSkills userAgrSkills = new UserAggregatedSkills(new Long(inKey[0]), inKey[1], aggregatedSkills);
        String output = mapper.writeValueAsString(userAgrSkills);
        log.error(output);
        context.write(new Text(output), NullWritable.get());
    }
}
