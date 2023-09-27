FROM amazoncorretto:11-alpine-jdk
COPY target/command-0.0.1-SNAPSHOT.jar command.jar
ENTRYPOINT ["java","-jar","/command.jar"]