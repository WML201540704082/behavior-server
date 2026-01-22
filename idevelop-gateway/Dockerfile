FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER idevelop@163.com

RUN mkdir -p /idevelop/gateway

WORKDIR /idevelop/gateway

EXPOSE 80

ADD ./target/idevelop-gateway.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
