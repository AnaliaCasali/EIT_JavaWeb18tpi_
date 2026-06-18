FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

ENV TZ=America/Argentina/Buenos_Aires

RUN apt-get update && \
    apt-get install -y tzdata && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/target/java18tpi.war app.war
COPY --from=builder /app/target/dependency/webapp-runner.jar webapp-runner.jar

EXPOSE 8080

CMD ["java", "-jar", "webapp-runner.jar", "--port", "8080", "app.war"]