FROM openjdk:21-jdk-slim AS client-build

WORKDIR /app

RUN apt-get update && apt-get install -y findutils

COPY . .

RUN ./gradlew --no-daemon bootJar

FROM openjdk:21-jdk-slim AS final-client

WORKDIR /app

COPY --from=client-build /app/client/build/libs/client-0.0.1-SNAPSHOT.jar /app/client.jar
COPY --from=client-build /app/shared_dto /app/shared_dto
COPY --from=client-build /app/shared_utils /app/shared_utils

RUN chmod +x /app/client.jar

CMD ["java", "-jar", "/app/client.jar"]