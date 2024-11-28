FROM openjdk:17-jdk-alpine
EXPOSE 8082

# Set Nexus repository details
ARG NEXUS_URL=http://192.168.137.200:8081/repository/maven-releases
ARG JAR_PATH=tn/esprit/tp-foyer/1.0.0/tp-foyer-1.0.0.jar

# Download the JAR file from Nexus
RUN apk add --no-cache curl && \
    curl -o tp-foyer-1.0.0.jar "$NEXUS_URL/$JAR_PATH"

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "/tp-foyer-1.0.0.jar"]
