FROM maven:3.9.4-eclipse-temurin-17-alpine
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests
CMD ["java","-jar","target/tu-app.jar"]