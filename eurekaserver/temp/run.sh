#!/bin/bash
echo "********************************************************"
echo "Iniciando Eureka Server"
echo "********************************************************"
# java -Djava.security.egd=file:/dev/./urandom -jar /usr/share/servicios/$jar
java -jar /usr/share/servicios/$jar

