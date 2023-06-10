#!/bin/bash

start-dfs.sh
start-yarn.sh
mr-jobhistory-daemon.sh start historyserver

echo "---master--"
ssh master jps
echo "--slave1--"
ssh slave1 jps
echo "--slave2--"
ssh slave2 jps
echo "--slave3--"
ssh slave3 jps
