
FROM maven:3.9-eclipse-temurin-22 AS build
WORKDIR /app

copy pom.xml .
copy my-app-api ./my-app-api
copy my-app-core ./my-app-core
copy my-app-messaging ./my-app-messaging

run mvn clean package -DskipTests

FROM eclipse-temurin:22-jre
WORKDIR /app

# Копируем только исполняемый JAR (не .original)
COPY --from=build /app/my-app-api/target/my-app-api-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]