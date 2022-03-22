#!/bin/bash
jarpath=/opt/sgg/engine/ftp
nohup java -jar $jarpath/DTEF*.jar $jarpath/$1 > /dev/null  2>&1 &
echo $! > $jarpath/$1.pid
