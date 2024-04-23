FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /
COPY /src /src
COPY /cdr_files /cdr_files
COPY pom.xml /
RUN mvn -f /pom.xml clean package

FROM openjdk:17-jdk-slim
WORKDIR /
COPY --from=build /target/*.jar cdr-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "cdr-service.jar"]

