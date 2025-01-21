FROM amazoncorretto:17
WORKDIR /app
COPY build/libs/TafDatastoreService-0.0.1-SNAPSHOT.jar TafDatastoreService.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "TafDatastoreService.jar"]
