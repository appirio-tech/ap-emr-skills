package com.appirio.mapreduce.skills;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by parthshah on 9/3/15.
 */
public class TagHelperImplTest {

    @Before
    public void setUp() {

    }

    @Test
    public void testInit() {
        ObjectMapper mapper = new ObjectMapper();
        String val = "[\"DEVELOP\",\"DATA_SCIENCE\"]";
        try {
            List<String> strList = mapper.readValue(val, mapper.getTypeFactory().constructCollectionType(List.class, String.class));
            Assert.assertEquals(2, strList.size());
        } catch (Exception exp) {
            exp.printStackTrace();;
        }

    }
}
