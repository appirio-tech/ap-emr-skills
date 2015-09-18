
DROP TABLE db_tags;
CREATE EXTERNAL TABLE db_tags(id BIGINT, name STRING, synonyms STRING)
STORED BY 'org.apache.hadoop.hive.dynamodb.DynamoDBStorageHandler'
TBLPROPERTIES ("dynamodb.table.name" = "Tags",
"dynamodb.column.mapping" = "id:id,name:name,synonyms:synonyms");


drop table tags_export;
CREATE TABLE tags_export (id BIGINT, name STRING, synonyms STRING)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '|' LINES TERMINATED by '\n' 
STORED AS TEXTFILE
LOCATION 'hdfs:///user/supply/skills/input/tagsMap/';

INSERT OVERWRITE TABLE tags_export SELECT * from db_tags;