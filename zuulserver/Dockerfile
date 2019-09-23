FROM openjdk:11.0.4-jre
MAINTAINER Juan Cabello <jcabelloc@itana.pe>

RUN  apt-get update && apt-get -y install netcat-openbsd 


# Add the service itself
ARG JAR_FILE
ENV jar=${JAR_FILE}
ADD target/${JAR_FILE} /usr/share/servicios/

ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh