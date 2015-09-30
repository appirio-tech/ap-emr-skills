#!/bin/bash
#Install Sqoop - s3://<BUCKET_NAME>/install_sqoop.sh
cd
echo "Copying Sqoop from S3"
hadoop fs -copyToLocal s3://supply-emr-qa/JAR_DIR/sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz
echo "Copied Sqoop"

echo "Untar Sqoop installer"
tar -xzf sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz
echo "Untar finished"

echo "Copy Informix JDBC jar from S3"
hadoop fs -copyToLocal s3://supply-emr-qa/JAR_DIR/ifxjdbc.jar ifxjdbc.jar
echo "Copied Informix JDBC jar"

cp ifxjdbc.jar sqoop-1.4.6.bin__hadoop-2.0.4-alpha/lib/


# create input / output directories
echo "Create input/output directories"

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
echo 'Creating : hdfs:///user/supply/skills/input/userEntered/'
hadoop fs -mkdir hdfs:///user/supply/skills/input/userEntered/
echo 'Creating : hdfs:///user/supply/skills/input/challenge/'
hadoop fs -mkdir hdfs:///user/supply/skills/input/challenge/

echo 'Creating : hdfs:///user/supply/skills/output/'
hadoop fs -mkdir hdfs:///user/supply/skills/output/

echo 'Install complete'