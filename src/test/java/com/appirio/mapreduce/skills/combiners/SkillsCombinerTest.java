package com.appirio.mapreduce.skills.combiners;

import com.appirio.mapreduce.skills.pojo.AggregatedSkill;
import com.appirio.mapreduce.skills.pojo.MappedSkill;
import com.appirio.mapreduce.skills.pojo.SkillSource;
import com.appirio.mapreduce.skills.pojo.UserAggregatedSkills;
import com.appirio.mapreduce.skills.reducers.SimpleSkillsReducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by parthshah on 8/30/15.
 */
public class SkillsCombinerTest {


    ReduceDriver<Text, Text, Text, Text> reduceDriver;

    @Before
    public void setUp() {
        SkillsCombiner combiner = new SkillsCombiner();
        reduceDriver = reduceDriver.newReduceDriver(combiner);
    }

    @Test
    public void testCombinerWithHiddenSkills() throws IOException{

        List<Text> values = new ArrayList<Text>();
        final Text inKey = new Text("1:albertwang");
        ObjectMapper objMapper = new ObjectMapper();

        MappedSkill skill = new MappedSkill();
        skill.setHidden(true);
        skill.setWeight(1);
        skill.setTagId(1L);
        skill.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.USER_ENTERED)));
        values.add(new Text(objMapper.writeValueAsString(skill)));

        skill.setTagId(1L);
        skill.setWeight(2.0);
        skill.setHidden(false);
        skill.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.CHALLENGE)));
        values.add(new Text(objMapper.writeValueAsString(skill)));

        reduceDriver.withInput(inKey, values);

        MappedSkill outSkill = new MappedSkill();
        outSkill.setTagId(1L);
        outSkill.setHidden(true);
        outSkill.setWeight(3.0);
        outSkill.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.CHALLENGE, SkillSource.USER_ENTERED)));

        reduceDriver.withOutput(new Pair<Text, Text>(
                inKey,
                new Text(objMapper.writeValueAsString(outSkill))
        ));
        reduceDriver.runTest();

    }

    @Test
    public void testCombinerWithAllHiddenSkills() throws IOException{

        List<Text> values = new ArrayList<Text>();
        final Text inKey = new Text("1:albertwang");
        ObjectMapper objMapper = new ObjectMapper();

        MappedSkill skill = new MappedSkill();
        skill.setHidden(true);
        skill.setWeight(1);
        skill.setTagId(1L);
        skill.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.USER_ENTERED)));
        values.add(new Text(objMapper.writeValueAsString(skill)));

        skill.setTagId(1L);
        skill.setWeight(2.0);
        skill.setHidden(true);
        skill.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.CHALLENGE)));
        values.add(new Text(objMapper.writeValueAsString(skill)));

        reduceDriver.withInput(inKey, values);

        MappedSkill outSkill = new MappedSkill();
        outSkill.setTagId(1L);
        outSkill.setHidden(true);
        outSkill.setWeight(3.0);
        outSkill.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.CHALLENGE, SkillSource.USER_ENTERED)));

        reduceDriver.withOutput(new Pair<Text, Text>(
                inKey,
                new Text(objMapper.writeValueAsString(outSkill))
        ));
        reduceDriver.runTest();

    }

    @Test
    public void testCombinerWithNoHiddenSkills() throws IOException{

        List<Text> values = new ArrayList<Text>();
        Text inKey1 = new Text("1:albertwang");
        ObjectMapper objMapper = new ObjectMapper();

        MappedSkill skill = new MappedSkill();
        skill.setHidden(false);
        skill.setWeight(1);
        skill.setTagId(1L);
        skill.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.USER_ENTERED)));
        values.add(new Text(objMapper.writeValueAsString(skill)));

        skill.setTagId(1L);
        skill.setWeight(2.0);
        skill.setHidden(false);
        skill.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.CHALLENGE)));
        values.add(new Text(objMapper.writeValueAsString(skill)));

        reduceDriver.withInput(inKey1, values);

        Text inKey2 = new Text("2:Ghostar");
        reduceDriver.withInput(inKey2, values);

        MappedSkill outSkill = new MappedSkill();
        outSkill.setTagId(1L);
        outSkill.setHidden(false);
        outSkill.setWeight(3.0);
        outSkill.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.CHALLENGE, SkillSource.USER_ENTERED)));

        reduceDriver.withOutput(new Pair<Text, Text>(
                inKey1,
                new Text(objMapper.writeValueAsString(outSkill))
        ));

        reduceDriver.withOutput(new Pair<Text, Text>(
                inKey2,
                new Text(objMapper.writeValueAsString(outSkill))
        ));
        reduceDriver.runTest();

    }

}
