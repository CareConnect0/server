FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootjar


FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/heartcall-project.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]