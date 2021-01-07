FROM maven:3.6.3-jdk-11-slim
COPY src /home/critter/src
COPY pom.xml /home/critter/pom.xml
RUN mvn -f /home/critter/pom.xml package -Dmaven.test.skip=true
COPY wait_for_it/wait-for-it.sh /home/critter/wait-for-it.sh
