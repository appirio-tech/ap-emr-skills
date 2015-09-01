package com.appirio.mapreduce.skills;

import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by parthshah on 8/29/15.
 */
public interface TagHelper {

    void init(Mapper.Context context) throws IOException, InterruptedException;

    long getTagId(String name);

}
