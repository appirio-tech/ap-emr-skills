package com.appirio.mapreduce.skills.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by parthshah on 8/24/15.
 */
public class UserEnteredSkillRecordTest {

//    @Test
//    public void testSerDe() {
//        String jsonStr = "{\"userId\":1,\"userHandle\":\"testHandle\",\"skills\":[{\"tagId\":\"1\",\"hidden\":false,\"createdAt\":\"2015-11-24T12:10:10Z\",\"updatedAt\":\"2015-11-24T12:10:10Z\"},{\"tagId\":\"2\",\"hidden\":false,\"createdAt\":\"2015-11-24T12:10:10Z\",\"updatedAt\":\"2015-11-24T12:10:10Z\"},{\"tagId\":\"3\",\"hidden\":false,\"createdAt\":\"2015-11-24T12:10:10Z\",\"updatedAt\":\"2015-11-24T12:10:10Z\"}]}";
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            UserEnteredSkillRecord skillRec = mapper.readValue(jsonStr, UserEnteredSkillRecord.class);
//            assertEquals(1L, skillRec.getUserId());
//            assertEquals("testHandle", skillRec.getUserHandle());
//            List<UserEnteredSkill> skills = skillRec.getSkills();
//            assertEquals(3, skills.size());
//            assertFalse(skills.get(0).isHidden());
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//
//    }

}
