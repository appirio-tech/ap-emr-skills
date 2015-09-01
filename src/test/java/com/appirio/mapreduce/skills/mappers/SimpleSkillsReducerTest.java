package com.appirio.mapreduce.skills.mappers;

import com.appirio.mapreduce.skills.SimpleSkillsReducer;
import com.appirio.mapreduce.skills.pojo.AggregatedSkill;
import com.appirio.mapreduce.skills.pojo.MappedSkill;
import com.appirio.mapreduce.skills.pojo.SkillSource;
import com.appirio.mapreduce.skills.pojo.UserAggregatedSkills;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
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
public class SimpleSkillsReducerTest {


    ReduceDriver<Text, Text, Text, NullWritable> reduceDriver;

    @Before
    public void setUp() {
        SimpleSkillsReducer reducer = new SimpleSkillsReducer();
        reduceDriver = reduceDriver.newReduceDriver(reducer);
    }

    @Test
    public void testReducer() throws IOException{

        List<Text> values = new ArrayList<Text>();
        final Text inKey = new Text("1:albertwang");
        ObjectMapper objMapper = new ObjectMapper();

        MappedSkill skill = new MappedSkill();
        skill.setHidden(false);
        skill.setWeight(1);
        skill.setTagId(1L);
        skill.setSource(SkillSource.USER_ENTERED);
        values.add(new Text(objMapper.writeValueAsString(skill)));

        skill.setTagId(1L);
        skill.setWeight(2.0);
        skill.setSource(SkillSource.CHALLENGE);
        values.add(new Text(objMapper.writeValueAsString(skill)));

        reduceDriver.withInput(inKey, values);


        AggregatedSkill skill1 = new AggregatedSkill(
                1L,
                3.0,
                new HashSet<SkillSource>(Arrays.asList(
                        SkillSource.USER_ENTERED,
                        SkillSource.CHALLENGE
                ))
        );
        List<AggregatedSkill> skills = new ArrayList<AggregatedSkill>();
        skills.add(skill1);

        UserAggregatedSkills userSkills = new UserAggregatedSkills();
        userSkills.setSkills(skills);
        userSkills.setUserHandle("albertwang");
        userSkills.setUserId(1L);

        reduceDriver.withOutput(new Pair<Text, NullWritable>(
                new Text(objMapper.writeValueAsString(userSkills)),
                NullWritable.get()
        ));
        reduceDriver.runTest();

    }
}
