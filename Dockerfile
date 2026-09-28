# Build: JDK 8, the version Revaluate was written for.
FROM maven:3.9-eclipse-temurin-8 AS build
WORKDIR /src
COPY . .
RUN mvn -B -q -DskipTests package

# Run
FROM eclipse-temurin:8-jre
WORKDIR /app
COPY --from=build /src/resources/target/resources-1.0.jar revaluate-api.jar
COPY resources/src/main/resources/config_local.yaml config.yaml
EXPOSE 8080 8081
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "exec java -DENVIRONMENT=local $JAVA_OPTS -jar revaluate-api.jar server config.yaml"]
