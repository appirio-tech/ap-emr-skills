add jar s3://supply-emr/scripts/json-serde-1.3-jar-with-dependencies.jar;

DROP TABLE userSkills;
CREATE EXTERNAL TABLE userSkills(userId BIGINT, userHandle STRING, skills STRING)
STORED BY 'org.apache.hadoop.hive.dynamodb.DynamoDBStorageHandler'
TBLPROPERTIES ("dynamodb.table.name" = "MemberEnteredSkills",
"dynamodb.column.mapping" = "userHandle:userHandle,userId:userId,skills:skills");

DROP TABLE s3_export;
CREATE EXTERNAL TABLE s3_export(userId BIGINT, userHandle STRING, skills STRING)
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
LOCATION 's3://supply-emr/skills/export/memberEnteredSkills/';

INSERT OVERWRITE TABLE s3_export SELECT * FROM userSkills;
