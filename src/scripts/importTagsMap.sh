
while getopts ":i:" opt; do
  case $opt in
    i)
      BASE_SKILL_EMR_DIR="$OPTARG"
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


aws s3 cp $BASE_SKILL_EMR_DIR/input/tagsMap/tagsMap.txt tagsMap.txt
if [[ $? != 0 ]]; then 
  echo "Failed to copy tagsMap.txt from $BASE_SKILL_EMR_DIR/input/tagsMap/tagsMap.txt"
  exit $rc
fi

echo "Copying to hdfs directory"
hadoop fs -copyFromLocal ./tagsMap.txt hdfs:///user/supply/skills/input/tagsMap/tagsMap.txt
if [[ $? != 0 ]]; then 
  echo "Failed to copy tagsMap.txt to hdfs:///user/supply/skills/input/tagsMap/tagsMap.txt"
  exit $rc
fi


if hadoop fs -test -e hdfs:///user/supply/skills/input/tagsMap/tagsMap.txt; then
 echo "Successfully created hdfs:///user/supply/skills/input/tagsMap/tagsMap.txt"
else
  echo "Failed to create hdfs:///user/supply/skills/input/tagsMap/tagsMap.txt"
  exit 2
fi