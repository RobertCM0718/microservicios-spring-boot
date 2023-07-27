FROM openjdk:17
VOLUME /tmp
ADD ./target/microservicio-oauth-0.0.1-SNAPSHOT.jar microservicio-oauth.jar
ENTRYPOINT ["java","-jar","/microservicio-oauth.jar"]