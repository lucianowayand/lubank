FROM ubuntu:latest as build

RUN apt update
RUN apt install openjdk-17-jdk -y

COPY . .

RUN ./gradlew build

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /build/libs/lubank-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]