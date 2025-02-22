FROM openjdk:21-jdk-slim AS server-build

WORKDIR /app

RUN apt-get update && apt-get install -y findutils

COPY . .

RUN ./gradlew --no-daemon bootJar --stacktrace

FROM openjdk:21-jdk-slim AS final-server

WORKDIR /app

COPY wait-for-it.sh /app/wait-for-it.sh
RUN apt-get update && apt-get install -y netcat-openbsd
RUN chmod +x /app/wait-for-it.sh


COPY --from=server-build /app/server/build/libs/server-0.0.1-SNAPSHOT.jar /app/server.jar
COPY --from=server-build /app/shared_dto /app/shared_dto
COPY --from=server-build /app/shared_utils /app/shared_utils

ENTRYPOINT ["bash", "-c", "./wait-for-it.sh mysql:3306 -- java -jar /app/server.jar"]