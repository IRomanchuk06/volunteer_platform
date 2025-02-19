FROM openjdk:21-jdk-slim

WORKDIR /app

COPY gradlew gradlew.bat ./
COPY gradle/ gradle/
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew

COPY server/build.gradle server/Dockerfile ./server/
COPY server/ ./server/
COPY shared_dto/ ./shared_dto/
COPY shared_utils/ ./shared_utils/

RUN ./gradlew --no-daemon bootJar

EXPOSE 8080

CMD ["java", "-jar", "build/libs/volunteer_platform-0.0.1-SNAPSHOT.jar"]
