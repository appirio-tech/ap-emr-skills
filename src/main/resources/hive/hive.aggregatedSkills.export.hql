-- 
-- Script to export aggregated skills to dynamodb & s3
--

-- Include jar necessary for serde
add jar s3://supply-emr-qa/JAR_DIR/json-serde-1.3-jar-with-dependencies.jar;
add jar s3://supply-emr-qa/JAR_DIR/brickhouse-0.7.1-SNAPSHOT.jar;
CREATE TEMPORARY FUNCTION to_json AS 'brickhouse.udf.json.ToJsonUDF';

-- setup table to read data from
DROP TABLE IF EXISTS hive_aggregated_skills;
CREATE EXTERNAL TABLE hive_aggregated_skills
(
 userId BIGINT, 
 userHandle STRING, 
 handleLower STRING, 
 skills STRING
)
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
LOCATION 'hdfs:///user/supply/skills/output/aggregatedSkills';

-- table to export data to dynamodb
DROP TABLE IF EXISTS db_aggregated_skills;
CREATE EXTERNAL TABLE db_aggregated_skills
(
  userId BIGINT, 
  userHandle STRING, 
  handleLower STRING, 
  skills STRING
)
STORED BY 'org.apache.hadoop.hive.dynamodb.DynamoDBStorageHandler'
TBLPROPERTIES 
(
  "dynamodb.table.name" = "MemberAggregatedSkills",
  "dynamodb.column.mapping" = "userId:userId,userHandle:userHandle,handleLower:handleLower,skills:skills"
);
INSERT OVERWRITE TABLE db_aggregated_skills SELECT * FROM hive_aggregated_skills;

-- also write files to S3
INSERT OVERWRITE DIRECTORY '${hiveconf:OUTPUT_DIR}' 
SELECT to_json( named_struct("userId", userId, "userHandle", userHandle, "skills", skills)) FROM hive_aggregated_skills;
