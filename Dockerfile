# Etapa 1: Build da aplicação
FROM maven:3.8.6-amazoncorretto-17 AS build
LABEL maintainer="admfazzolo@gmail.com"

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
WORKDIR /app
RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    && rm -rf /var/lib/apt/lists/*
COPY --from=build /app/target/*.jar /app/consultalicitacao.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/consultalicitacao.jar"]
