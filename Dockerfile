FROM openjdk:17
WORKDIR /app
COPY target/search-web-service-0.0.1-SNAPSHOT.jar /app/search-web-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "search-web-service-0.0.1-SNAPSHOT.jar"]