#!/bin/sh

PROJECT_PATH=$PWD
BUILD_OPTION=0
START_OPTION=0
BAD_OPTION=0

while [[ $# -gt 0 ]]; do
  key="$1"
  case $key in
    -b|--build)
      BUILD_OPTION=1
      shift
      ;;
    -s|--start)
      START_OPTION=1
      shift
      ;;
    *)
      echo "Bad usage!"
      echo "Usage:"
      echo "$0 [OPTIONS]"
      echo "Options: "
      echo '-b (--build) - builds jar'
      echo '-s (--start) - starts application'
      break
      ;;
  esac
done

echo "BUILD_OPTION=$BUILD_OPTION"
echo "START_OPTION=$START_OPTION"