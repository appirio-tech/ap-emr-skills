package com.appirio.mapreduce.skills;

/**
 * Created by parthshah on 8/31/15.
 */
public class TagHelperFactory {

    public static TagHelper getHelper() {
        System.out.println("in helper factory");
        return new TagHelperImpl();
    }
}
