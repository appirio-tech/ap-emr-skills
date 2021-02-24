# ap-emr-skills
Packaged JARs that handle map reduce job(s) for aggregating skills


### Mappers supported
1. User Enetered Skills
1. Skills from Challenges successfully participated in.

### Running locally

#### Setup

##### Hadoop Install Mac
http://zhongyaonan.com/hadoop-tutorial/setting-up-hadoop-2-6-on-mac-osx-yosemite.html

#### Build
```
mvn package -DskipTests=true
```

#### Local Test
```
hadoop jar target/ap-emr-skills-1.0-SNAPSHOT.jar com.appirio.mapreduce.skills.SkillsAggregator src/main/resources/data/tagsMap.txt src/test/resources/skills/input/userEnteredSkills.txt src/test/resources/skills/input/challengeSkills.txt src/test/resources/skills/input/stackOverflowSkills.txt /tmp/skills
```

### Running In EMR

Create Cluster with command line or create Cluster from AWS console:
```
aws emr create-cluster --name “SkillsTest3” --enable-debugging --log-uri s3://supply-emr/skills/logs/skillstest3 --release-label emr-4.0.0 --applications Name=Hive Name=Hadoop --use-default-roles --ec2-attributes KeyName=topcoder-dev-vpc-app —instance-type m3.xlarge -no-auto-terminate
```

Enable SSH on Master Node

Upload Jar file:
```
aws emr put --cluster-id <Your EMR cluster Id> --key-pair-file "<Your Key Pair File>" --src "/<Your Path to>/ap-emr-skills/target/ap-emr-skills-1.0-SNAPSHOT.jar"
```

Execute task:
```
aws emr ssh --cluster-id <Your EMR cluster Id> --key-pair-file "<Your Key Pair File>" --command "hadoop jar ap-emr-skills-1.0-SNAPSHOT.jar com.appirio.mapreduce.skills.SkillsAggregator  hdfs:///<Your Path to>/tagsMap.txt hdfs:///<Your Path to>/userEnteredSkills.txt hdfs:///<Your Path to>/challengeSkills.txt hdfs:///<Your Path to>/stackOverflowSkills.txt hdfs:///<Your Path to>/aggregatedSkills/"
```

### Overall workflow
The overall execution flow is defined in resources/jobs/job-tasks.json file, steps are:
1) Install sqoop:

- Copy sqoop and other lib files to HDFS
- Create input/output directories

2) Import Tags:

- Create db_tags and tags_export, then export tags data to hdfs:///user/supply/skills/input/tagsMap/

3) Import Challenge Skills

- Query challenges skills from INFORMIX and save it to hdfs:///user/supply/skills/input/challenge/

4) Import User Entered Skills

- Query user entered skills from DynamoDB and save it to hdfs:///user/supply/skills/input/userEntered/

5) Import Stack Overflow Skills

- Query stack overflow skills from DynamoDB and save it to hdfs:///user/supply/skills/input/stackOverflow/

6) Aggregate Skills

- This MapReduce program will aggrate user skills from HDFS location and output the results into hdfs:///user/supply/skills/output/aggregatedSkills/

7) Export Aggregated Skills

- Read aggregate skills from hdfs:///user/supply/skills/output/aggregatedSkills/ and save it to DynamoDB


### References

#### Sqoop
* Sqoop Documentation
    * Sqoop doc - https://sqoop.apache.org/docs/1.4.0-incubating/SqoopUserGuide.html#id1764646
      Cookbook - https://www.safaribooksonline.com/library/view/apache-sqoop-cookbook/9781449364618/ch04.html

* Sqoop on EMR
  http://www.slideshare.net/rohitsghatol/sqoop-onemr
  http://sqoop.apache.org/docs/1.4.6/SqoopUserGuide.html#_selecting_the_data_to_import
  http://rohitghatol.com/?p=699

