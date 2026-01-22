FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER idevelop@163.com

RUN mkdir -p /idevelop/develop

WORKDIR /idevelop/develop

EXPOSE 7007

ADD ./target/idevelop-develop.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
