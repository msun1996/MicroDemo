FROM cemmersb/centos-jdk8
MAINTAINER msun msun@jd.com

ADD target/api-gateway-zuul-0.0.1-SNAPSHOT.jar /api-gateway-zuul.jar

ENTRYPOINT ["java", "-jar", "/api-gateway-zuul.jar"]

