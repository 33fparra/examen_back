FROM eclipse-temurin:17-alpine

RUN mkdir /app

WORKDIR /app

COPY target/EcoPunto-0.0.1-SNAPSHOT.jar /app/EcoPunto.jar

EXPOSE 8080

CMD java -jar EcoPunto.jar
