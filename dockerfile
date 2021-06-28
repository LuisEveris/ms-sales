FROM openjdk:8-jdk-alpine
EXPOSE 8003
COPY "target/ms-sales-0.0.1-SNAPSHOT.jar" "app.jar"
ENTRYPOINT ["java","-jar","app.jar"]