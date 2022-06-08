FROM maven:3.8.3-openjdk-17 as BUILD

WORKDIR /app

COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

FROM openjdk:17-alpine as RUN

COPY --from=build /app/target/**.jar /app/self-description-hub-integration.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/app/self-description-hub-integration.jar"]