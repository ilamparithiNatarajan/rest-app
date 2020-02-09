FROM openjdk:8-jdk-alpine
#COPY . /app
#WORKDIR /app
#RUN mvn clean install
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
