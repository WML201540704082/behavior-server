FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER idevelop@163.com

RUN mkdir -p /idevelop/admin

WORKDIR /idevelop/admin

EXPOSE 7002

ADD ./target/idevelop-admin.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
