FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /
COPY /src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean package

FROM openjdk:17-jdk-slim
WORKDIR /
COPY --from=build /target/*.jar brt-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "brt-service.jar"]

