FROM eclipse-temurin:23-jdk AS build

WORKDIR /app

COPY . .

RUN ./gradlew --no-daemon bootJar

FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY --from=build /app/client/build/libs/client-0.0.1-SNAPSHOT.jar /app/app.jar
COPY --from=build /app/shared_dto /app/shared_dto
COPY --from=build /app/shared_utils /app/shared_utils

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
