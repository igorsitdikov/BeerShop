FROM openjdk:11
MAINTAINER java@gpsolutions.com
COPY target/beershop-0.0.1-SNAPSHOT.jar /opt/beershop.jar
ENTRYPOINT ["java", "-jar", "/opt/beershop.jar"]