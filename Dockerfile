FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ADD build/libs/challenge-1.0.jar challenge.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/challenge.jar"]