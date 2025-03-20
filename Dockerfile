FROM openjdk:23-jdk-slim
COPY ./target/PassTracker-0.0.1-SNAPSHOT.jar /opt/service.jar
EXPOSE 8080
CMD java -jar /opt/service.jar