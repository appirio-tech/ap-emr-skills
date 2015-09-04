add jar s3://supply-emr/scripts/brickhouse-0.7.1-SNAPSHOT.jar;

DROP TABLE db_user_skills;
CREATE EXTERNAL TABLE db_user_skills(userId BIGINT, userHandle STRING, skills STRING)
STORED BY 'org.apache.hadoop.hive.dynamodb.DynamoDBStorageHandler'
TBLPROPERTIES ("dynamodb.table.name" = "MemberEnteredSkills",
"dynamodb.column.mapping" = "userHandle:userHandle,userId:userId,skills:skills");

CREATE TEMPORARY FUNCTION to_json AS 'brickhouse.udf.json.ToJsonUDF';
INSERT OVERWRITE DIRECTORY 'hdfs:///user/supply/skills/input/userEntered'
SELECT to_json( named_struct("userId", userId, "userHandle", userHandle, "skills", skills)) FROM db_user_skills;
