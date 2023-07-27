FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD ./target/microservicio-zuul-server-0.0.1-SNAPSHOT.jar microservicio-zuul-server.jar
ENTRYPOINT ["java","-jar","/microservicio-zuul-server.jar"]