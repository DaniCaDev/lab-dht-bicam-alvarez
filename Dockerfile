FROM maven:3.9.11-eclipse-temurin-17 AS builder
WORKDIR /build
COPY pom.xml ./
COPY src ./src
RUN mvn -B -ntp clean verify

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /build/target/biciam-1.0.0-SNAPSHOT-demo.jar /app/biciam.jar
USER 10001:10001
ENTRYPOINT ["java", "-jar", "/app/biciam.jar"]
