FROM openjdk:21-jdk AS server-build

WORKDIR /app

RUN microdnf install findutils

COPY . .

RUN ./gradlew --no-daemon bootJar --stacktrace

FROM openjdk:21-jdk AS final-server

WORKDIR /app

COPY --from=server-build /app/server/build/libs/server-0.0.1-SNAPSHOT.jar /app/server.jar
COPY --from=server-build /app/shared_dto /app/shared_dto
COPY --from=server-build /app/shared_utils /app/shared_utils

ENTRYPOINT ["java", "-jar", "/app/server.jar"]
