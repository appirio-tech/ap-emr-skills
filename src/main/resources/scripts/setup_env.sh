#!/bin/bash
#Install Sqoop - s3://<BUCKET_NAME>/install_sqoop.sh

if [ $# -ne 1 ]
  then
    echo "Usage setup_env.sh <jardirpath>"
    exit 1
fi

jarDir=$1
echo "jarDir passed $jarDir"

echo "Downloading Sqoop.tar from $jarDir/sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz"
cd
hadoop fs -copyToLocal $jarDir/sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz

if [ -f "./sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz" ]; then
  echo "Sqoop tar copied"
else
  echo "Sqoop copy failed"
  exit 1
fi

tar -xzf sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz
hadoop fs -copyToLocal $jarDir/ifxjdbc.jar ifxjdbc.jar

if [ -f "./ifxjdbc.jar" ]; then
  echo "ifxjdbc.jar copied"
else
  echo "ifxjdbc.jar copy failed"
  exit 2
fi
cp ifxjdbc.jar sqoop-1.4.6.bin__hadoop-2.0.4-alpha/lib/

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

echo 'Creating : hdfs:///user/supply/skills/input/'
hadoop fs -mkdir hdfs:///user/supply/skills/input/
echo 'Creating : hdfs:///user/supply/skills/input/tagsMap/'
hadoop fs -mkdir hdfs:///user/supply/skills/input/tagsMap/
echo 'Creating : hdfs:///user/supply/skills/input/userEntered/'
hadoop fs -mkdir hdfs:///user/supply/skills/input/userEntered/
#fixme
#echo 'Creating : hdfs:///user/supply/skills/input/challenge/'
#hadoop fs -mkdir hdfs:///user/supply/skills/input/challenge/
echo 'Creating : hdfs:///user/supply/skills/input/stackOverflow'
hadoop fs -mkdir hdfs:///user/supply/skills/input/stackOverflow/

echo 'Creating : hdfs:///user/supply/skills/output/'
hadoop fs -mkdir hdfs:///user/supply/skills/output/

hadoop fs -ls hdfs:///user/supply/skills/

echo 'Setup complete'
