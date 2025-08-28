FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM bellsoft/liberica-runtime-container:jre-17.0.5-musl
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "--enable-preview", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "app.jar"]
