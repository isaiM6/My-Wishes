#Use the following if you are an m1 mac. Don't know if we need it.
FROM --platform=linux/amd64 openjdk:17
# If not on m1 mac
VOLUME /tmp
COPY target/CST438_Project2-0.0.1-SNAPSHOT.jar  CST438_Project2-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","CST438_Project2-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080:8080
