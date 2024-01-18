FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
RUN ls -al
COPY target/*.jar app.jar
RUN ls -al
ENTRYPOINT ["java","-jar","/app.jar"]
