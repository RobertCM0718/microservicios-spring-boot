FROM openjdk:17
VOLUME /tmp
ADD ./target/microservicio-productos-0.0.1-SNAPSHOT.jar microservicio-productos.jar
ENTRYPOINT ["java","-jar","/microservicio-productos.jar"]