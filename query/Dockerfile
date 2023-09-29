FROM openjdk:11-alpine as build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw clean package -DskipTests

FROM bellsoft/liberica-openjre-alpine-musl:11
ARG DEPENDENCY=/app/target
COPY --from=build ${DEPENDENCY} /app/jar
ENTRYPOINT ["java","-jar","app/jar/app-0.0.1-SNAPSHOT.jar"]