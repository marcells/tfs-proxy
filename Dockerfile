FROM dockerfile/java:oracle-java8 
 
# Install maven
RUN apt-get update
RUN apt-get install -y maven
 
WORKDIR /code
 
ADD TFS-SDK-11.0.0/native /tfsnative
ADD lib /code/lib
ADD pom.xml /code/pom.xml

RUN ["mvn", "dependency:resolve"]

ADD src /code/src
RUN ["mvn", "package"]

EXPOSE 4567

CMD ["java", "-jar", "target/TfsTest-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]