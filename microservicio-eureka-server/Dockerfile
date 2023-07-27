FROM openjdk:17
VOLUME /tmp
EXPOSE 8761
ADD ./target/microservicio-eureka-server-0.0.1-SNAPSHOT.jar microservicio-eureka-server.jar
ENTRYPOINT ["java","-jar","/microservicio-eureka-server.jar"]