package com.appirio.mapreduce.skills;

/**
 * Created by parthshah on 8/31/15.
 */
public class TagHelperFactory {

    public static TagHelper getHelper() {
        return new TagHelperImpl();
    }
}
