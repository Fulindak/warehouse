FROM maven:3.8.5-openjdk-17 as build
WORKDIR /app
COPY /src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /target/*.jar warehouse.jar
EXPOSE 8188
ENTRYPOINT ["java", "--add-opens", "java.base/java.time=ALL-UNNAMED", "-jar", "warehouse.jar"]