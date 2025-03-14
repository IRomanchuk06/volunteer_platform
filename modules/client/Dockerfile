FROM openjdk:21-jdk-slim AS client-build

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY shared/dto shared/dto
COPY shared/utils shared/utils
COPY modules/client/build.gradle modules/client/build.gradle
COPY modules/client/src modules/client/src

RUN ./gradlew --no-daemon modules:client:dependencies
RUN ./gradlew --no-daemon modules:client:bootJar

FROM openjdk:21-jdk-slim AS final-client

WORKDIR /app

RUN useradd -m appuser && chown -R appuser /app
USER appuser

COPY --from=client-build /app/modules/client/build/libs/client-*.jar /app/client.jar

CMD ["java", "-jar", "/app/client.jar"]