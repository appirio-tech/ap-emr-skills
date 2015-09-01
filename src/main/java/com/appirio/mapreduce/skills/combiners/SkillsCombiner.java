package com.appirio.mapreduce.skills.combiners;

import com.appirio.mapreduce.skills.pojo.MappedSkill;
import com.appirio.mapreduce.skills.pojo.SkillSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

/**
 * Created by parthshah on 8/23/15.
 */
public class SkillsCombiner extends Reducer<Text, Text, Text, Text> {


    private class SkillTuple {
        private  Set<SkillSource> sources;
        private double weight;
        private boolean hidden;
    }

    public final static ObjectMapper mapper = new ObjectMapper();

    protected static Log log;
    public SkillsCombiner() {
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
                tup.hidden = tup.hidden || skill.isHidden();
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
        MappedSkill skill = new MappedSkill();
        for (Map.Entry<Long, SkillTuple> entry: skillMap.entrySet()) {
            skill.setTagId(entry.getKey());
            skill.setHidden(entry.getValue().hidden);
            skill.setWeight(entry.getValue().weight);
            skill.setSources(entry.getValue().sources);
            context.write(key, new Text(mapper.writeValueAsString(skill)));
        }
    }
}
