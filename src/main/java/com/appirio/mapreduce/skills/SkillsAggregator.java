package com.appirio.mapreduce.skills;

/**
 * Created by parthshah on 8/23/15.
 */

import com.appirio.mapreduce.skills.combiners.SkillsCombiner;
import com.appirio.mapreduce.skills.mappers.ChallengeSkillsMapper;
import com.appirio.mapreduce.skills.mappers.StackOverflowSkillsMapper;
import com.appirio.mapreduce.skills.mappers.UserEnteredSkillsMapper;
import com.appirio.mapreduce.skills.reducers.SimpleSkillsReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.net.URI;


public class SkillsAggregator extends Configured implements Tool {

    // input URIs
    private String userEnteredSkillsURI;
    private String challengeSkillsURI;
    private String stackOverflowSkillsURI;

    private String tagsFileURI;

    // output URI
    private String outputURI;

    private int initArgs(String[] args) {
        if (args.length != 5) {
            System.err.printf("Usage: %s <tagsMap> <userEnteredSkills> <challengeSkills> <externalStackOverflow> <output> \n", getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.err);
            return -1;
        }
        tagsFileURI = args[0];
        userEnteredSkillsURI = args[1];
        challengeSkillsURI = args[2];
        stackOverflowSkillsURI = args[3];
        outputURI = args[4];
        System.out.println("TagFilesURI: " + tagsFileURI);
        System.out.println("userEnteredSkillsURI: " + userEnteredSkillsURI);
        System.out.println("challengeSkillsURI: " + challengeSkillsURI);
        System.out.println("stackOverflowSkillsURI: " + stackOverflowSkillsURI);
        System.out.println("outputURI: " + outputURI);

        return 0;
    }

    public int run(String[] args) throws Exception {

        // init
        int initRet = this.initArgs(args);
        if (initRet != 0) {
            return initRet;
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(com.appirio.mapreduce.skills.SkillsAggregator.class);

        // mappers
        // user entered skills
        MultipleInputs.addInputPath(job, new Path(userEnteredSkillsURI),
                TextInputFormat.class, UserEnteredSkillsMapper.class);
        // challenge skills
        MultipleInputs.addInputPath(job, new Path(challengeSkillsURI),
                TextInputFormat.class, ChallengeSkillsMapper.class);
        // stackoverflow skills
        MultipleInputs.addInputPath(job, new Path(stackOverflowSkillsURI),
                TextInputFormat.class, StackOverflowSkillsMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        // Tag file - cache
        job.addCacheFile(new URI(tagsFileURI));

        // combiner
        job.setCombinerClass(SkillsCombiner.class);

        // reducers
        job.setReducerClass(SimpleSkillsReducer.class);

        // output format
        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(NullWritable.class);

        // set output
        TextOutputFormat.setOutputPath(job, new Path(outputURI));

        return job.waitForCompletion(true) ? 0 :1;
    }

    public static void main(String[] argv)throws Exception{
        int exitCode = ToolRunner.run(new SkillsAggregator(), argv);
        System.exit(exitCode);
    }
}