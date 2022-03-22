#!/usr/bin/env bash
CONFIG=/opt/sgg/engine/ftp/$1
PID=$(cat $CONFIG.pid)
# 安全退出
kill -15 $PID
rm -f $CONFIG
rm -f $CONFIG.pid
