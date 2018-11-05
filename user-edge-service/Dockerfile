FROM cemmersb/centos-jdk8
MAINTAINER msun msun@jd.com

ADD target/user-edge-service-0.0.1-SNAPSHOT.jar /user-edge-service.jar

ENTRYPOINT ["java", "-jar", "/user-edge-service.jar"]
