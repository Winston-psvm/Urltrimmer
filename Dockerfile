FROM openjdk:17
ARG JAR_FILE=target/Urltrimmer-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /Urltrimmer.jar
ENTRYPOINT ["java","-jar","/Urltrimmer.jar"]
EXPOSE 8080