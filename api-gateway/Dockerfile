FROM maven:3.9-eclipse-temurin-22 AS build
WORKDIR /FlowTrack
COPY pom.xml .
RUN sed -i '/<modules>/,/<\/modules>/d' pom.xml
COPY api-gateway ./api-gateway
RUN mvn clean package -DskipTests -f api-gateway/pom.xml

FROM eclipse-temurin:22-jre
WORKDIR /app
COPY --from=build /FlowTrack/api-gateway/target/*.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java","-jar", "app.jar"]