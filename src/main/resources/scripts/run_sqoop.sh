#!/bin/bash

jdbcdriver=$1
connect=$2
user=$3
password=$4

cd 
SQOOP_INSTALL_DIR=./sqoop-1.4.6.bin__hadoop-2.0.4-alpha/bin/

# verify SQOOP_INSTALL DIR is set and is valid
if [ ! -d "$SQOOP_INSTALL_DIR" ]; then
 echo "SQOOP_INSTALL_DIR - $SQOOP_INSTALL_DIR is invalid."
 exit 1
fi

# finally execute the command
echo "RUNNING SQOOP command"
$SQOOP_INSTALL_DIR/sqoop import --driver $jdbcdriver --connect $connect --username $user --password $password --verbose --query 'SELECT project_result.user_id userId, coder.handle handle, project_result.project_id challengeId, project_technology.name FROM tcs_dw:project_result INNER JOIN tcs_dw:project as project ON project.project_id = project_result.project_id INNER JOIN topcoder_dw:coder as coder ON project_result.user_id = coder.coder_id INNER JOIN tcs_dw:project_technology ON project_technology.project_id = project.project_id WHERE project_result.passed_review_ind = 1 AND $CONDITIONS' --boundary-query 'select min(coder.coder_id), max(coder.coder_id) from topcoder_dw:coder as coder' --target-dir hdfs:///user/supply/skills/input/challenge --split-by project_result.project_id
#fixme
#rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
echo "Done with RUN SCOOP "
