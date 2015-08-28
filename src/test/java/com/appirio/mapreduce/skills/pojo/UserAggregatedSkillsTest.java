package com.appirio.mapreduce.skills.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by parthshah on 8/24/15.
 */
public class UserAggregatedSkillsTest {
    private static final ObjectMapper mapper = new ObjectMapper();
    @Test
    public void testSerDe() {
        String jsonStr = "{\"userId\":1,\"userHandle\":\"testHandle\",\"skills\":[{\"tagId\":\"1\",\"score\":79.99},{\"tagId\":\"2\",\"score\":23},{\"tagId\":\"3\",\"score\":23}]}";

        try {
            UserAggregatedSkills skillRec = mapper.readValue(jsonStr, UserAggregatedSkills.class);
            assertEquals(1L, skillRec.getUserId());
            assertEquals("testHandle", skillRec.getUserHandle());
            List<AggregatedSkill> skills = skillRec.getSkills();
            assertEquals(3, skills.size());
            assertEquals(0, Double.compare(79.99, skills.get(0).getScore()));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Test
    public void testCreate() {
        UserAggregatedSkills uas = new UserAggregatedSkills();
        uas.setUserId(new Long("1"));
        List<AggregatedSkill> skills = new ArrayList<AggregatedSkill>();
        skills.add(new AggregatedSkill(1L, 89.00));
        skills.add(new AggregatedSkill(1L, 89.00));

        uas.setSkills(skills);

        try {
            System.out.println("Obj: " + mapper.writeValueAsString(uas));
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

}
