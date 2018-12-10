#!/bin/bash

if [[ $# -ne 5 ]]; then
    echo "Usage: ./tix-time-client.sh <username> <password> <installation name> <local receiving port> <logs directory>"
    exit -1
fi
echo "Compiling tix-time-client"
gradle :tix-time-client-cli:jar
if [[ $? -eq 0 ]];
then
	echo "tix-time-client build... Successful"
else
	echo "tix-time-client build... FAILED"
	exit -1
fi
echo "Starting tix-time-client"
java -jar tix-time-client-cli/build/libs/tix-time-client-cli.jar $1 $2 $3 $4 $5
exit 0
