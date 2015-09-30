
DROP TABLE hive_tags;
CREATE EXTERNAL TABLE hive_tags(tagId BIGINT, domain STRING, name STRING, synonyms STRING, status STRING, priority SMALLINT, categories STRING)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n'
LOCATION 's3://supply-emr/test-input/tagsMap/';

drop table db_tags;
CREATE EXTERNAL TABLE db_tags(id BIGINT, domain STRING, name STRING, synonyms STRING, status STRING, priority BIGINT, categories STRING)
STORED BY 'org.apache.hadoop.hive.dynamodb.DynamoDBStorageHandler'
TBLPROPERTIES ("dynamodb.table.name" = "Tags", "dynamodb.throughput.write.percent"="10",
"dynamodb.column.mapping" = "id:id,domain:domain,name:name,synonyms:synonyms,status:status,priority:priority,categories:categories");

insert overwrite table db_tags select tagId, domain, name, case when synonyms='' then null else synonyms end as synonyms, status, priority, categories from hive_tags;