FROM openjdk:21-jdk-slim AS client-build

WORKDIR /app

RUN apt-get update && apt-get install -y findutils

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew --no-daemon clean build bootJar -x test

FROM openjdk:21-jdk-slim AS final-client

WORKDIR /app

COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

COPY --from=client-build /app/client/build/libs/client-0.0.1-SNAPSHOT.jar /app/client.jar
COPY --from=client-build /app/shared_dto /app/shared_dto
COPY --from=client-build /app/shared_utils /app/shared_utils

RUN chmod +x /app/client.jar

CMD ["./wait-for-it.sh", "server:8080", "--timeout=60", "--", "java", "-jar", "/app/client.jar"]