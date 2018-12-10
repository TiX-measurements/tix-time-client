#!bin/sh

if [[ $# -ne 5 ]]; then
    echo "sh ./install.sh <username> <password> <installation name> <local receiving port> <logs directory>"
    exit -1
fi
echo "Installing ... Please wait"
gradle :tix-time-client-cli:jar >/dev/null
if [[ $? -eq 0 ]];
then
	echo "tix-time-client build ... Succesfull"
else
	echo "tix-time-client build ...FAILED"
	echo "Please, Verify you have gradle 3.3 installed and java 8"
	exit -1
fi
java -jar tix-time-client-cli/build/libs/tix-time-client-cli.jar $1 $2 $3 $4 $5
exit 0
