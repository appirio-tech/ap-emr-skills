--
-- Importing user-entered skills from dynamodb
--

-- import json serde jars
add jar ${hiveconf:JAR_DIR}/brickhouse-0.7.1-SNAPSHOT.jar;
-- creating function to export as json
CREATE TEMPORARY FUNCTION to_json AS 'brickhouse.udf.json.ToJsonUDF';

-- setup import table
DROP TABLE db_user_skills;
CREATE EXTERNAL TABLE db_user_skills
(
  userId BIGINT, 
  userHandle STRING, 
  skills STRING
)
STORED BY 'org.apache.hadoop.hive.dynamodb.DynamoDBStorageHandler'
TBLPROPERTIES 
(
  "dynamodb.table.name" = "MemberEnteredSkills",
  "dynamodb.column.mapping" = "userHandle:userHandle,userId:userId,skills:skills"
);


-- Export to hdfs directory
INSERT OVERWRITE DIRECTORY 'hdfs:///user/supply/skills/input/userEntered'
SELECT to_json(
  named_struct(
    "userId", userId, 
    "userHandle", userHandle, 
    "skills", skills
  )
) FROM db_user_skills;
