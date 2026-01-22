FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER idevelop@163.com

RUN mkdir -p /idevelop/swagger

WORKDIR /idevelop/swagger

EXPOSE 18000

ADD ./target/idevelop-swagger.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
