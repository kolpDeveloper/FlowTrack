FROM maven:3.9-eclipse-temurin-22 AS build
WORKDIR /FlowTrack
COPY pom.xml .


#Удаляем секцию modules из родительского pom.xml
RUN sed -i '/<modules>/,/<\/modules>/d' pom.xml

COPY eureka-server ./eureka-server

RUN mvn clean package -DskipTests -f eureka-server/pom.xml

FROM eclipse-temurin:22-jre
WORKDIR /app
RUN apt-get update && apt-get install -y curl || apk add --no-cache curl
COPY --from=build /FlowTrack/eureka-server/target/*.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar", "app.jar"]