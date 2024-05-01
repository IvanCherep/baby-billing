FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /
COPY /src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean package

FROM openjdk:17-jdk-slim
WORKDIR /
COPY --from=build /target/*.jar hrs-service.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "hrs-service.jar"]

