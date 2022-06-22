FROM maven:3.8.3-openjdk-17-slim

WORKDIR /app
COPY pom.xml pom.xml
COPY src src

RUN ls -al

EXPOSE 8080

ENTRYPOINT ["mvn","spring-boot:run"]