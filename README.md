# ap-emr-skills
Packaged JARs that handle map reduce job(s) for aggregating skills


### Mappers supported
1. User Enetered Skills
1. Skills from Challenges successfully participated in.

### Running locally

#### Setup

##### Hadoop Install Mac
http://zhongyaonan.com/hadoop-tutorial/setting-up-hadoop-2-6-on-mac-osx-yosemite.html

##### AWS CLI
Create Cluster:
```
aws emr create-cluster --name “SkillsTest3” --enable-debugging --log-uri s3://supply-emr/skills/logs/skillstest3 --release-label emr-4.0.0 --applications Name=Hive Name=Hadoop --use-default-roles --ec2-attributes KeyName=topcoder-dev-vpc-app —instance-type m3.xlarge -no-auto-terminate
```

#### Build


#### Test
```
hadoop jar target/ap-emr-skills-1.0-SNAPSHOT.jar com.appirio.mapreduce.skills.SkillsAggregator src/test/resources/skills/input/userEnteredSkills.txt src/test/resources/skills/input/challengeSkills.txt /tmp/skills
```


### References

#### Sqoop
* Sqoop Documentation
  * Sqoop doc - https://sqoop.apache.org/docs/1.4.0-incubating/SqoopUserGuide.html#id1764646
Cookbook - https://www.safaribooksonline.com/library/view/apache-sqoop-cookbook/9781449364618/ch04.html

* Sqoop on EMR
http://www.slideshare.net/rohitsghatol/sqoop-onemr
http://sqoop.apache.org/docs/1.4.6/SqoopUserGuide.html#_selecting_the_data_to_import
http://rohitghatol.com/?p=699

