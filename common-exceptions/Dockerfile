FROM maven:3.9-eclipse-temurin-22 AS build
WORKDIR /FlowTrack
COPY pom.xml .
RUN sed -i '/<modules>/,/<\/modules>/d' pom.xml
COPY common-exceptions ./common-exceptions
RUN mvn clean package -DskipTests -f common-exception/pom.xml

FROM eclipse-temurin:22-jre
WORKDIR /app
COPY --from=build /FlowTrack/common-exception/target/*.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar", "app.jar"]