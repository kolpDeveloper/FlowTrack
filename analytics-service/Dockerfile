FROM maven:3.9-eclipse-temurin-22 AS build
WORKDIR /FlowTrack
COPY pom.xml .
RUN sed -i '/<modules>/,/<\/modules>/d' pom.xml
COPY analytics-service ./analytics-service
RUN mvn clean package -DskipTests -f analytics-service/pom.xml
FROM eclipse-temurin:22-jre
WORKDIR /app
COPY --from=build /FlowTrack/analytics-service/target/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar", "app.jar"]