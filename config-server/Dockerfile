FROM maven:3.9-eclipse-temurin-22 AS build
WORKDIR /FlowTrack
COPY pom.xml .
RUN sed -i '/<modules>/,/<\/modules>/d' pom.xml
COPY config-server ./config-server
RUN mvn clean package -DskipTests -f config-server/pom.xml
FROM eclipse-temurin:22-jre
WORKDIR /app
COPY --from=build /FlowTrack/config-server/target/config-server-1.0-SNAPSHOT.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar", "app.jar"]