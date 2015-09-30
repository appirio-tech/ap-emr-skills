--
-- Importing stackoverflow skills from dynamodb
--

-- import json serde jars
add jar ${hiveconf:JAR_DIR}/brickhouse-0.7.1-SNAPSHOT.jar;
-- creating function to export as json
CREATE TEMPORARY FUNCTION to_json AS 'brickhouse.udf.json.ToJsonUDF';

-- setup import table
DROP TABLE db_stackoverflow_skills;
CREATE EXTERNAL TABLE db_stackoverflow_skills
(
  userId BIGINT, 
  topTags STRING
)
STORED BY 'org.apache.hadoop.hive.dynamodb.DynamoDBStorageHandler'
TBLPROPERTIES 
(
  "dynamodb.table.name" = "Externals.Stackoverflow",
  "dynamodb.column.mapping" = "userId:userId,topTags:topTags"
);


-- Export to hdfs directory
INSERT OVERWRITE DIRECTORY 'hdfs:///user/supply/skills/input/stackOverflow'
SELECT to_json(
  named_struct(
    "userId", userId, 
    "topTags", topTags
  )
) FROM db_stackoverflow_skills;
