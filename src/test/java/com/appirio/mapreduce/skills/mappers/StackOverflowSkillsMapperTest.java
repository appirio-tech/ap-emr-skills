package com.appirio.mapreduce.skills.mappers;

import com.appirio.mapreduce.skills.TagHelper;
import com.appirio.mapreduce.skills.TagHelperFactory;
import com.appirio.mapreduce.skills.pojo.MappedSkill;
import com.appirio.mapreduce.skills.pojo.SkillSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by ramakrishnapemmaraju on 9/22/15.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({TagHelperFactory.class, TagHelper.class})
public class StackOverflowSkillsMapperTest {

    MapDriver<LongWritable, Text, Text, Text> mapDriver;

    @Mock
    private TagHelper mockTagHelper;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TagHelperFactory.class);
        when(TagHelperFactory.getHelper()).thenReturn(mockTagHelper);
        when(mockTagHelper.getTagId("Java")).thenReturn(1L);
        when(mockTagHelper.getTagId("Javascript")).thenReturn(2L);

        StackOverflowSkillsMapper mapper = new StackOverflowSkillsMapper();
        mapDriver = MapDriver.newMapDriver(mapper);
    }

    @Test
    public void testMapper() throws IOException {
        List<Pair<LongWritable, Text>> input = new ArrayList<Pair<LongWritable, Text>>();
        input.add(new Pair<LongWritable, Text>(new LongWritable(), new Text("{\"userId\":111,\"topTags\":\"Java,Javascript\"}")));
        input.add(new Pair<LongWritable, Text>(new LongWritable(), new Text("{\"userId\":222,\"topTags\":\"Java\"}")));

        mapDriver.withAll(input);

        MappedSkill skill1 = new MappedSkill();
        skill1.setHidden(false);
        skill1.setWeight(1);
        skill1.setTagId(1L);
        skill1.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.EXTERNAL_STACKOVERFLOW)));

        MappedSkill skill2 = new MappedSkill();
        skill2.setHidden(false);
        skill2.setWeight(1);
        skill2.setTagId(2L);
        skill2.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.EXTERNAL_STACKOVERFLOW)));

        MappedSkill skill3 = new MappedSkill();
        skill3.setHidden(false);
        skill3.setWeight(1);
        skill3.setTagId(1L);
        skill3.setSources(new HashSet<SkillSource>(Arrays.asList(SkillSource.EXTERNAL_STACKOVERFLOW)));

        List<Pair<Text, Text>> output = new ArrayList<Pair<Text, Text>>();

        ObjectMapper objMapper = new ObjectMapper();
        output.add(new Pair<Text, Text>(new Text("111"), new Text(objMapper.writeValueAsString(skill1))));
        output.add(new Pair<Text, Text>(new Text("111"), new Text(objMapper.writeValueAsString(skill2))));
        output.add(new Pair<Text, Text>(new Text("222"), new Text(objMapper.writeValueAsString(skill3))));
        mapDriver.withAllOutput(output);

        mapDriver.runTest();
        PowerMockito.verifyStatic(Mockito.times(1));
    }
}
