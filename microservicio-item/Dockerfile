FROM openjdk:17
VOLUME /tmp
EXPOSE 8090
ADD ./target/microservicio-items-0.0.1-SNAPSHOT.jar microservicio-items.jar
ENTRYPOINT ["java","-jar","/microservicio-items.jar"]