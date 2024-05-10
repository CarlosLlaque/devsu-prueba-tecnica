FROM gradle:8.7.0-jdk17-alpine as build

WORKDIR /home/gradle/src

COPY --chown=gradle:gradle . /home/gradle/src

RUN gradle bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/eurekaserver*.jar /app/eurekaserver.jar

EXPOSE 8761
CMD ["java", "-jar", "eurekaserver.jar"]
