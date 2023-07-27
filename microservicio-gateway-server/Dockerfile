FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD ./target/microservicio-gateway-server-0.0.1-SNAPSHOT.jar microservicio-gateway-server.jar
ENTRYPOINT ["java","-jar","/microservicio-gateway-server.jar"]