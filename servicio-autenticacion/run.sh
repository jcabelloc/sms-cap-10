#!/bin/bash

echo "********************************************************"
echo "Waiting for the eureka server to start on port $EUREKASERVER_PORT"
echo "********************************************************"
while ! nc -z fintech-eurekaserver $EUREKASERVER_PORT; do sleep 3; done
echo ">>>>>>>>>>>> Eureka Server has started"


echo "********************************************************"
echo "Waiting for the configuration server to start on port $CONFIGSERVER_PORT"
echo "********************************************************"
while ! nc -z fintech-configurationserver $CONFIGSERVER_PORT; do sleep 3; done
echo ">>>>>>>>>>>> Configuration Server has started"

# echo "********************************************************"
# echo "Waiting for the database server to start on port $DATABASESERVER_PORT"
# echo "********************************************************"
# while ! nc -z database $DATABASESERVER_PORT; do sleep 3; done
# echo ">>>>>>>>>>>> Database Server has started"

echo "********************************************************"
echo "Starting Service with Configuration Service :  $CONFIGSERVER_URI via Eureka :  $EUREKASERVER_URI ON PORT: $SERVER_PORT";
echo "********************************************************"
java -Dserver.port=$SERVER_PORT 										\
	-Deureka.client.serviceUrl.defaultZone=$EUREKASERVER_URI            \
	-Dspring.cloud.config.uri=$CONFIGSERVER_URI 						\
	-Dspring.profiles.active=$PROFILE 									\
	-jar /usr/share/servicios/$jar
	
