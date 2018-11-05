FROM cemmersb/centos-jdk8:latest
MAINTAINER hanzhaoyang hangzhaoyang@jd.com

ADD target/user-thrift-service-0.0.1-SNAPSHOT.jar /user-thrift-serice.jar

ENTRYPOINT ["java", "-jar", "/user-thrift-serice.jar"]