FROM openjdk:21-slim-buster
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} acc-second.jar
ENTRYPOINT ["java","-jar","acc-second.jar"]