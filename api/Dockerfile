FROM openjdk:11.0.10-jre-buster

# App file
WORKDIR /app
COPY /target/api-*.jar api-app.jar

# Remove root powers
RUN chown -R nobody:nogroup *
USER nobody

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "api-app.jar"]