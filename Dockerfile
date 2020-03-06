FROM openjdk:12

MAINTAINER david.allen.burch@gmail.com

ADD /build/libs/springboot-transactions-api-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "springboot-transactions-api-0.0.1-SNAPSHOT.jar"]