FROM openjdk:11-jdk-slim
COPY target/critter-0.0.1-SNAPSHOT.jar critter.jar
COPY wait_for_it/wait-for-it.sh wait-for-it.sh