# Build the jar using Gradle
FROM gradle:jdk13 AS build
MAINTAINER david.allen.burch@gmail.com
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon 

# Copy and run the jar
FROM openjdk:13
COPY --from=build /home/gradle/src/build/libs/transaction-tracker-api-1.0.0-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "transaction-tracker-api-1.0.0-SNAPSHOT.jar"]
