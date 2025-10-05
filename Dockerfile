FROM openjdk:21-oracle

ARG JAR_FILE
ARG JAVA_OPTS

ADD ${JAR_FILE} app.jar
ADD ./entrypoint.sh entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]
