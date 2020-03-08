FROM openjdk:12

MAINTAINER david.allen.burch@gmail.com

ADD /build/libs/transaction-tracker-api-1.0.0-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "transaction-tracker-api-1.0.0-SNAPSHOT.jar"]