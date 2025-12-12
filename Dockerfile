FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY pom.xml .
RUN apk add --no-cache bash maven
RUN mvn dependency:go-offline -B
COPY . .
RUN mvn clean package -DskipTests
EXPOSE 8080

# Variables de entorno para la DB
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-d4tu1f717kns73csrvog-a.virginia-postgres.render.com:5432/organic_8o66
ENV SPRING_DATASOURCE_USERNAME=organic_8o66_user
ENV SPRING_DATASOURCE_PASSWORD=hxp5VvHqkkVmayVS0DEjelGI2BE3slGj

ENTRYPOINT ["java","-jar","target/app.jar"]