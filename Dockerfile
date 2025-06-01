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

ARG FIREBASE_PROJECT_ID
ENV FIREBASE_PROJECT_ID=$FIREBASE_PROJECT_ID

ARG SPRING_PROFILES_ACTIVE=prod
ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE

ENV FIREBASE_CREDENTIALS=""

EXPOSE 8080
ENTRYPOINT ["/bin/sh", "-c", "\
  echo \"$FIREBASE_CREDENTIALS\" > /app/firebase.json && \
  export GOOGLE_APPLICATION_CREDENTIALS=/app/firebase.json && \
  java -jar /app/consultalicitacao.jar --server.port=$PORT \
"]
