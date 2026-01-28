# Steg 1: Bygg-miljö
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Steg 2: Kör-miljö
FROM eclipse-temurin:21-jre-jammy
COPY --from=build /target/*.jar app.jar

# Öppna porten (standard för Spring Boot är 8080)
EXPOSE 8080

# Kör applikationen
ENTRYPOINT ["java", "-jar", "app.jar"]