package com.appirio.mapreduce.skills;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by parthshah on 8/29/15.
 */
public class TagHelperImpl implements TagHelper {

    Map<String, Long> tagMap;

    public void init(Mapper.Context context) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        if (tagMap == null || tagMap.isEmpty()) {
            tagMap = new HashMap<String, Long>();
            URI[] files = context.getCacheFiles();
            Path tagFile = new Path(files[0]);
            FileSystem hdfs = FileSystem.get(context.getConfiguration());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(hdfs.open(tagFile)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                String[] tokens = line.split("\\|");
                Long tagId = Long.valueOf(tokens[0].trim());

                tagMap.put(tokens[1].toLowerCase().trim(), tagId);
                // also store all synonyms in the map
                if (tokens.length == 3 && tokens[2] != null && !tokens[2].equals("\\N")) {
                    List<String> synonyms = mapper.readValue(tokens[2], mapper.getTypeFactory().constructCollectionType(List.class, String.class));
                    for (String syn : synonyms) {
                        tagMap.put(syn.trim().toLowerCase(), tagId);
                    }
                }
            }
            bufferedReader.close();
        }
    }

    public Long getTagId(String tagName) {
        if (tagName == null){
            return null;
        }
        return tagMap.get(tagName.trim().toLowerCase());
    }
}
