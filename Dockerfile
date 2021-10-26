FROM openjdk:11
ADD /target/KafkaModificateXML-0.0.1-SNAPSHOT.jar KafkaModificateXML-0.0.1-SNAPSHOT.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","KafkaModificateXML-0.0.1-SNAPSHOT.jar"]