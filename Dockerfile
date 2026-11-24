FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src src
RUN mvn -q package -DskipTests
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN useradd --system --uid 1001 tend
COPY --from=build /app/target/tend-backend-*.jar app.jar
USER tend
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
