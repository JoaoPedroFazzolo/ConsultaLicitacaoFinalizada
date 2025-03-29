FROM eclipse-temurin:17
LABEL maintainer="admfazzolo@gmail.com"
WORKDIR /app
COPY target/itensPregao-0.0.1-SNAPSHOT.jar /app/consultalicitacao.jar
ENTRYPOINT ["java", "-jar", "consultalicitacao.jar"]
EXPOSE 8080