FROM openjdk:21-jdk-slim AS server-build

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY shared/dto shared/dto
COPY shared/utils shared/utils
COPY modules/server/build.gradle modules/server/build.gradle
COPY modules/server/src modules/server/src

RUN ./gradlew --no-daemon modules:server:dependencies
RUN ./gradlew --no-daemon modules:server:bootJar

FROM openjdk:21-jdk-slim AS final-server

WORKDIR /app

RUN useradd -m appuser && chown -R appuser /app
USER appuser

COPY --from=server-build /app/modules/server/build/libs/server-*.jar /app/server.jar
ADD scripts/wait-for-it.sh /app/scripts/wait-for-it.sh

ENTRYPOINT ["/app/scripts/wait-for-it.sh", "mysql:3306", "--timeout=60", "--", "java", "-jar", "/app/server.jar"]