#!/bin/bash
#Install Sqoop - s3://<BUCKET_NAME>/install_sqoop.sh
cd
hadoop fs -copyToLocal s3://supply-emr/scripts/sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz sqoop-1.4.6.tar.gz

tar -xzf sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz
hadoop fs -copyToLocal s3://supply-emr/scripts/ifxjdbc.jar ifxjdbc.jar

cp ifxjdbc.jar sqoop-1.4.6.bin__hadoop-2.0.4-alpha/lib/