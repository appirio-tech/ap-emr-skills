#!/bin/bash
CURRENT_DATE=$(date +D%m-%d-%y-T%H-%M-%S)

echo "hive-script --run-hive-script --args s3://supply-emr/scripts/hive.aggregatedSkills.export.hql --hiveconf CURRENT_DATE=$CURRENT_DATE"