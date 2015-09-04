#!/bin/bash
#Install Sqoop - s3://<BUCKET_NAME>/install_sqoop.sh
cd
hadoop fs -copyToLocal s3://supply-emr/scripts/sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz

tar -xzf sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz
hadoop fs -copyToLocal s3://supply-emr/scripts/ifxjdbc.jar ifxjdbc.jar

cp ifxjdbc.jar sqoop-1.4.6.bin__hadoop-2.0.4-alpha/lib/

export $SQOOP_INSTALL_DIR=sqoop-1.4.6.bin__hadoop-2.0.4-alpha/bin/

# create input / output directories
if hadoop fs -test -d hdfs:///user/supply ; then
 echo '"hdfs:///user/supply/" directory exists'
else
 echo 'Creating : hdfs:///user/supply/'
 hadoop fs -mkdir hdfs:///user/supply/
fi

if hadoop fs -test -d hdfs:///user/supply/skills/; then
 echo 'Deleting : hdfs:///user/supply/skills/'
 hadoop fs -rmdir hdfs:///user/supply/skills/
fi

echo 'Creating : hdfs:///user/supply/skills/'
hadoop fs -mkdir hdfs:///user/supply/skills/
echo 'Creating : hdfs:///user/supply/skills/inout/'
hadoop fs -mkdir hdfs:///user/supply/skills/input/
echo 'Creating : hdfs:///user/supply/skills/input/tagsMap/'
hadoop fs -mkdir hdfs:///user/supply/skills/input/tagsMap/
echo 'Creating : hdfs:///user/supply/skills/input/userEntered/'
hadoop fs -mkdir hdfs:///user/supply/skills/input/userEntered/
#echo 'Creating : hdfs:///user/supply/skills/input/challenge/'
#hadoop fs -mkdir hdfs:///user/supply/skills/input/challenge/

echo 'Creating : hdfs:///user/supply/skills/output/'
hadoop fs -mkdir hdfs:///user/supply/skills/output/

echo 'Setup complete'