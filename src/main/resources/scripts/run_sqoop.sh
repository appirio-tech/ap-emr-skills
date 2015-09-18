#!/bin/bash

cd 
SQOOP_INSTALL_DIR=./sqoop-1.4.6.bin__hadoop-2.0.4-alpha/bin/
#SCRIPT_FILE = s3://supply-emr-qa/scripts/sqoop.challengeSkills

while getopts ":f:" opt; do
  case $opt in
    f)
      SCRIPT_FILE="$OPTARG"
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      exit 1
      ;;
    :)
      echo "Option -$OPTARG requires an argument." >&2
      exit 1
      ;;
  esac
done

if [ -z "$SCRIPT_FILE" ]; then
 echo "-f file is required."
 exit 1
fi
if [ -f "./scoop.options.txt" ]; then
  rm ./sqoop.options.txt
fi

echo "Fetching $SCRIPT_FILE... "
aws s3 cp $SCRIPT_FILE ./sqoop.options.txt
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

$SQOOP_INSTALL_DIR/sqoop --options-file ./sqoop.options.txt
rc=$?; if [[ $rc != 0 ]]; then exit $rc; fi
