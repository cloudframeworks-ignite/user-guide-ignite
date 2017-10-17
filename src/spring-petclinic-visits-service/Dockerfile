FROM java:8-jdk
MAINTAINER Elvis Zhang

ADD ./target/spring-petclinic-visits-service-1.5.4.jar /opt/
CMD ["java", "-Xmx1024m", "-jar", "/opt/spring-petclinic-visits-service-1.5.4.jar"]