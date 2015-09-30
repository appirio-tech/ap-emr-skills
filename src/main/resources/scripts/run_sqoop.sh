#!/bin/bash

if [ $# -ne 4 ]
  then
    echo "Usage run_sqoop.sh <connecturl> <username> <password> <scriptfilepath>"
    exit 1
fi

connect=$1
username=$2
password=$3
s3filepath=$4

echo "connect is $connect"
echo "username is $username"
echo "scriptfilepath is $s3filepath"

cd
SQOOP_INSTALL_DIR=./sqoop-1.4.6.bin__hadoop-2.0.4-alpha/bin/

aws s3 cp $s3filepath ./sqoop.options.txt
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi

if [ ! -f "./sqoop.options.txt" ]; then
 echo "Options file is missing;"
 exit 1
fi

# verify SQOOP_INSTALL DIR is set and is valid
if [ ! -d "$SQOOP_INSTALL_DIR" ]; then
 echo "SQOOP_INSTALL_DIR - $SQOOP_INSTALL_DIR is invalid."
 exit 1
fi

# finally execute the command
echo "RUNNING SQOOP command"
$SQOOP_INSTALL_DIR/sqoop --options-file ./sqoop.options.txt --connect $connect --username $username --password $password
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
echo "Done with RUN SCOOP "