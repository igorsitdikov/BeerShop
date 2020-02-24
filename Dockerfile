FROM maven:3-jdk-11 as MAVEN_BUILDER

RUN git clone --depth 1 --branch=docker https://github.com/igorsitdikov/BeerShop.git /opt/tmp && \
    cd /opt/tmp && \
    mvn install

WORKDIR /opt/tmp/

FROM openjdk:11
MAINTAINER ihar.sitdzikau@yandex.by
COPY --from=MAVEN_BUILDER /opt/tmp/target/beershop-0.0.1-SNAPSHOT.jar /opt/beershop.jar
ENTRYPOINT ["java", "-jar", "/opt/beershop.jar", "--spring.config.location=classpath:application-docker.properties"]