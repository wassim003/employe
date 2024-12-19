FROM openjdk:20
COPY ./target/Project-0.0.1-SNAPSHOT.jar Project-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","Project-0.0.1-SNAPSHOT.jar"]