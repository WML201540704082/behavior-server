FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER idevelop@163.com

RUN mkdir -p /idevelop/report

WORKDIR /idevelop/report

EXPOSE 8108

ADD ./target/idevelop-report.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
