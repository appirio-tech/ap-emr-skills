package com.appirio.mapreduce.skills;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by parthshah on 8/29/15.
 */
public class TagHelperImpl implements TagHelper {

    Map<String, Long> tagMap;

    public void init(Mapper.Context context) throws IOException, InterruptedException {
        if (tagMap == null || tagMap.isEmpty()) {
            tagMap = new HashMap<String, Long>();
            URI[] files = context.getCacheFiles();
            Path tagFile = new Path(files[0]);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(tagFile.toString()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(",");
                tagMap.put(tokens[1], new Long(tokens[0]));
            }
            bufferedReader.close();
        }
    }

    public long getTagId(String tagName) {
        return tagMap.get(tagName);
    }
}
