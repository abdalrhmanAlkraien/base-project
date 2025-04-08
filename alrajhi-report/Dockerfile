### Use a JDK image as a base
FROM abdalrhmanalkraien/alrajhi-report-service:base-image-openjdk-17-slim
MAINTAINER alrhajhibank.com
LABEL version="1.0.0"
LABEL description="For build report service image"
LABEL author="Abdalrhman ALkraien"

#ARG JAR_FILE=./app-module/target/app-module-0.0.1-SNAPSHOT.jar
COPY app-module/target/alrajhi-report-0.0.1-SNAPSHOT.jar app.jar

RUN sh -c 'touch /app.jar'

ENTRYPOINT ["java","-XX:+UnlockExperimentalVMOptions","-XX:+UseContainerSupport","-XX:MaxRAMFraction=1","-XX:+UseG1GC","-jar", "/app.jar"]
#ENTRYPOINT ["java", "-cp","/app.jar", "com.project.multimodule.MultiModuleApplication"]