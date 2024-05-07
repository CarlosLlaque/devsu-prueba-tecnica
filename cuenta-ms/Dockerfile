FROM maven:3.9.6-eclipse-temurin-17-alpine as build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:resolve

COPY src/ /app/src/
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/cuenta-ms.jar /app

EXPOSE 8081
CMD ["java", "-jar", "cuenta-ms.jar"]
