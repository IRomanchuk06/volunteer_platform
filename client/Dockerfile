FROM openjdk:21-jdk AS client-build

WORKDIR /app

RUN microdnf install findutils

COPY . .

RUN ./gradlew --no-daemon bootJar

FROM openjdk:21-jdk AS final-client

WORKDIR /app

COPY --from=client-build /app/client/build/libs/client-0.0.1-SNAPSHOT.jar /app/client.jar
COPY --from=client-build /app/shared_dto /app/shared_dto
COPY --from=client-build /app/shared_utils /app/shared_utils

ENTRYPOINT ["java", "-jar", "/app/client.jar"]
