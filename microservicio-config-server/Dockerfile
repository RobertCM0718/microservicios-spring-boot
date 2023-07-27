FROM openjdk:17
VOLUME /tmp
EXPOSE 8888
ADD ./target/microservicio-config-server-0.0.1-SNAPSHOT.jar microservicio-config-server.jar
ENTRYPOINT ["java","-jar","/microservicio-config-server.jar"]