FROM maven:3-jdk-11 as MAVEN_BUILDER
RUN git clone --depth 1 https://github.com/igorsitdikov/BeerShop.git /tmp && \
    cd tmp && \
    mvn install

WORKDIR /tmp/

FROM openjdk:11
MAINTAINER ihar.sitdzikau@yandex.by
COPY --from=MAVEN_BUILDER /tmp/target/beershop-0.0.1-SNAPSHOT.jar /opt/beershop.jar
ENTRYPOINT ["java", "-jar", "/opt/beershop.jar"]