FROM gradle:7.6.0-jdk17 AS build
WORKDIR /home/gradle/project
COPY . .
RUN gradle clean build --no-daemon -x test

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]