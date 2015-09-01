package com.appirio.mapreduce.skills.mappers;

import com.appirio.mapreduce.skills.pojo.MappedSkill;
import com.appirio.mapreduce.skills.pojo.SkillSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
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
public class UserEnteredSkillsMapperTest {


    MapDriver<LongWritable, Text, Text, Text> mapDriver;

    @Before
    public void setUp() {
        UserEnteredSkillsMapper mapper = new UserEnteredSkillsMapper();
        mapDriver = MapDriver.newMapDriver(mapper);
    }

    @Test
    public void testMapper() throws IOException{
        mapDriver.withInput(new LongWritable(), new Text(
                "{\"skills\":\"{ \\\"1\\\": {\\\"createdAt\\\": 1092834894000, \\\"hidden\\\": false,\\\"updatedAt\\\": 1092834894000}, \\\"2\\\": {\\\"createdAt\\\": 1092834894000, \\\"hidden\\\": false,\\\"updatedAt\\\": 1092834894000}}\",\"userhandle\":\"albertwang\",\"userid\":1}"));

        MappedSkill skill1 = new MappedSkill();
        skill1.setHidden(false);
        skill1.setWeight(1);
        skill1.setTagId(1L);
        skill1.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.USER_ENTERED)));

        MappedSkill skill2 = new MappedSkill();
        skill2.setHidden(false);
        skill2.setWeight(1);
        skill2.setTagId(2L);
        skill2.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.USER_ENTERED)));
        List<Pair<Text, Text>> output = new ArrayList<Pair<Text, Text>>();
        final Text outKey = new Text("1:albertwang");
        ObjectMapper objMapper = new ObjectMapper();
        output.add(new Pair<Text, Text>(outKey, new Text(objMapper.writeValueAsString(skill1))));
        output.add(new Pair<Text, Text>(outKey, new Text(objMapper.writeValueAsString(skill2))));
        mapDriver.withAllOutput(output);

        mapDriver.runTest();
    }
}
