FROM gradle:8.7.0-jdk17-alpine as build

WORKDIR /app
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

RUN gradle build --no-daemon --info || return 0

COPY ./src ./src

RUN gradle bootJar --no-daemon --info

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/movimientoms*.jar /app/movimientoms.jar

EXPOSE 8080
CMD ["java", "-jar", "movimientoms.jar"]
