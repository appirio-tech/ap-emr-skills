# ap-emr-skills
Packaged JARs that handle map reduce job(s) for aggregating skills


### Mappers supported
1. User Enetered Skills
1. Skills from Challenges successfully participated in.

### Running locally

#### Setup

TBD

#### Build


#### Test
```
hadoop jar target/ap-emr-skills-1.0-SNAPSHOT.jar com.appirio.mapreduce.skills.SkillsAggregator src/test/resources/skills/input/userEnteredSkills.txt src/test/resources/skills/input/challengeSkills.txt /tmp/skills
```

### EMR Jobs
