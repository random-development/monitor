FROM maven:3.5-jdk-8-alpine AS build 
COPY /src /usr/src/monitor/src
COPY pom.xml /usr/src/monitor
COPY Dockerfile /usr/src/monitor
RUN mvn -f /usr/src/monitor/pom.xml clean install

FROM openjdk:8-jre-alpine
COPY --from=build /usr/src/monitor/target/monitor-1.0-SNAPSHOT.jar monitor.jar
ENTRYPOINT ["java","-jar","/monitor.jar"]
