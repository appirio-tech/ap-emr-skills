package com.appirio.mapreduce.skills;

/**
 * Created by parthshah on 8/23/15.
 */

import com.appirio.mapreduce.skills.mappers.ChallengeSkillsMapper;
import com.appirio.mapreduce.skills.mappers.UserEnteredSkillsMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class SkillsAggregator {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        GenericOptionsParser optionParser = new GenericOptionsParser(conf, args);
        String[] remainingArgs = optionParser.getRemainingArgs();
        if (remainingArgs.length != 3){
            System.err.println("Usage: skillsAggregator <in1> <in2> <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "skill aggregator");
        job.setJarByClass(SkillsAggregator.class);


        // mappers
        job.setMapperClass(UserEnteredSkillsMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        // reducers
        job.setReducerClass(SimpleSkillsReducer.class);

        // output format
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        MultipleInputs.addInputPath(job, new Path(remainingArgs[0]),
                TextInputFormat.class, UserEnteredSkillsMapper.class);

        MultipleInputs.addInputPath(job, new Path(remainingArgs[1]),
                TextInputFormat.class, ChallengeSkillsMapper.class);

        TextOutputFormat.setOutputPath(job, new Path(remainingArgs[2]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}