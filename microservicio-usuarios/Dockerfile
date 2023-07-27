FROM openjdk:17
VOLUME /tmp
ADD ./target/microservicio-usuarios-0.0.1-SNAPSHOT.jar microservicio-usuarios.jar
ENTRYPOINT ["java","-jar","/microservicio-usuarios.jar"]