FROM openjdk:17-jdk
WORKDIR /app
COPY target/itensPregao-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
