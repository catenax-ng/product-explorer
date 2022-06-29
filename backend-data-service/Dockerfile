FROM maven:3.8.5-openjdk-18-slim as BUILD

WORKDIR /app

COPY src /app/src
COPY pom.xml /app

RUN mvn -f /app/pom.xml clean package

FROM openjdk:18-slim as RUN

COPY --from=build /app/target/backend-data-service-0.0.1.jar /app/app.jar
EXPOSE 8194
ENTRYPOINT ["java","-jar","/app/app.jar"]
