FROM maven:3.8.6-amazoncorretto-17 AS build
LABEL maintainer="admfazzolo@gmail.com"

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
RUN ls -l /app/target/

FROM openjdk:17-slim
WORKDIR /app

RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    && rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/*.jar /app/consultalicitacao.jar

ARG FIREBASE_PROJECT_ID
ENV FIREBASE_PROJECT_ID=$FIREBASE_PROJECT_ID

COPY firebase-credentials.json /app/firebase-credentials.json
ENV GOOGLE_APPLICATION_CREDENTIALS=/app/firebase-credentials.json

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/consultalicitacao.jar", "--server.port=${PORT}"]