FROM openjdk:23-jdk-slim
COPY ./target/PassTracker-0.0.1-SNAPSHOT.jar /opt/service.jar
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://database:5430/wallet
ENV POSTGRES_USER=wallet
ENV POSTGRES_PASSWORD=wallet
EXPOSE 8080
CMD java -jar /opt/service.jar