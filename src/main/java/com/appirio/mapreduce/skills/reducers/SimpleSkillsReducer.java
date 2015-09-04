package com.appirio.mapreduce.skills.reducers;

import com.appirio.mapreduce.skills.pojo.AggregatedSkill;
import com.appirio.mapreduce.skills.pojo.MappedSkill;
import com.appirio.mapreduce.skills.pojo.SkillSource;
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


    private class SkillTuple {
        private  Set<SkillSource> sources;
        private double weight;
        private boolean hidden;
    }
    public final static ObjectMapper mapper = new ObjectMapper();

    protected static Log log;
    public SimpleSkillsReducer() {
        log = LogFactory.getLog(this.getClass());
    }



    @Override
    protected void reduce(Text key, Iterable<Text> skills, Context context)
        throws IOException, InterruptedException {
        Iterator<Text> itr = skills.iterator();
        HashMap<Long, SkillTuple> skillMap = new HashMap<Long, SkillTuple>();

        while(itr.hasNext()) {

            MappedSkill skill = mapper.readValue(itr.next().getBytes(), MappedSkill.class);
//            skillMap.compute(skill.getTagId(), (k,v) -> v == null ? skill.getWeight(): skill.getWeight()+v);
            long tagId = skill.getTagId();
            SkillTuple tup;
            if (skillMap.containsKey(tagId)) {
                // if tuple exists , updated weight & sources set
                tup = skillMap.get(tagId);
                tup.weight += skill.getWeight();
                tup.sources.addAll(skill.getSources());
            } else {
                // create new tuple set and add source & weight
                tup = new SkillTuple();
                tup.weight = skill.getWeight();
                tup.sources = new HashSet<SkillSource>();
                tup.sources.addAll(skill.getSources());
                tup.hidden = skill.isHidden();
            }
            skillMap.put(tagId, tup);
        }

        // Create output object
        Map<Long, AggregatedSkill> aggregatedSkills = new HashMap<Long, AggregatedSkill>();
//        skillMap.forEach((k,v) -> aggregatedSkills.add(new AggregatedSkill(k,v)));
        for (Map.Entry<Long, SkillTuple> entry: skillMap.entrySet()) {
            SkillTuple tup = entry.getValue();
            aggregatedSkills.put(entry.getKey(), new AggregatedSkill(tup.weight, tup.sources, tup.hidden));
        }

        // tokenize key - userId:userHandle
        String inKey[] = key.toString().split(":", 2);
        // Fixme: handle this in a better way?
        assert(inKey.length == 2);

        UserAggregatedSkills userAgrSkills = new UserAggregatedSkills(
                new Long(inKey[0]),
                inKey[1],
                mapper.writeValueAsString(aggregatedSkills)
        );
        String output = mapper.writeValueAsString(userAgrSkills);
        context.write(new Text(output), NullWritable.get());
    }
}
