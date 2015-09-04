add jar s3://supply-emr/scripts/json-serde-1.3-jar-with-dependencies.jar;

DROP TABLE IF EXISTS hdfs_aggregated_skills;
CREATE EXTERNAL TABLE hdfs_aggregated_skills(userId BIGINT, userHandle STRING, handleLower STRING, skills STRING, hidden)
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
LOCATION 'hdfs:///user/supply/skills/output/aggregatedSkills';

DROP TABLE IF EXISTS hive_aggregated_skills;
CREATE EXTERNAL TABLE hive_aggregated_skills(userId BIGINT, userHandle STRING, handleLower STRING, skills STRING)
STORED BY 'org.apache.hadoop.hive.dynamodb.DynamoDBStorageHandler'
TBLPROPERTIES ("dynamodb.table.name" = "MemberAggregatedSkills",
"dynamodb.column.mapping" = "userId:userId,userHandle:userHandle,handleLower:handleLower,skills:skills");

INSERT OVERWRITE TABLE hive_aggregated_skills SELECT * FROM hdfs_aggregated_skills;

CREATE TEMPORARY FUNCTION to_json AS 'brickhouse.udf.json.ToJsonUDF';
INSERT OVERWRITE DIRECTORY '${hiveconf:OUTPUT_DIR}'
STORED AS TEXTFILE
SELECT to_json( named_struct("userId", userId, "userHandle", userHandle, "skills", skills)) FROM db_user_skills;




---------------------




DROP TABLE IF EXISTS
INSERT OVERWRITE DIRECTORY 's3://supply-emr/skills/aggregatedSkills//'


set OUTPUT_DIR=s3://supply-emr/skills/aggregatedSkills/${hiveconf:CURRENT_DATE};
INSERT OVERWRITE DIRECTORY 'output/$CURRENT_DATE'
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
STORED AS TEXTFILE
SELECT to_json( named_struct("userId", userId, "userHandle", userHandle, "skills", skills)) FROM db_user_skills;


