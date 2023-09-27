FROM amazoncorretto:11-alpine-jdk
COPY target/query-0.0.1-SNAPSHOT.jar query.jar
ENTRYPOINT ["java","-jar","/query.jar"]