FROM maven:3.9-eclipse-temurin-22 AS build
WORKDIR /FlowTrack
COPY pom.xml .

RUN sed -i '/<modules>/,/<\/modules>/d' pom.xml

COPY notification-service ./notification-service
RUN mvn clean package -DskipTests -f notification-service

FROM eclipse-temurin:22-jre
WORKDIR /app
COPY --from=build /FlowTrack/notification-service/target/notification-service-1.0-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar", "app.jar"]