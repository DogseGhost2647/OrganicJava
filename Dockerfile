# Usamos la imagen de Java 17
FROM eclipse-temurin:17-jdk-alpine

# Crear carpeta de la app
WORKDIR /app

# Copiar pom.xml primero para bajar dependencias
COPY pom.xml .

# Instalar bash y Maven
RUN apk add --no-cache bash maven

# Descargar dependencias offline
RUN mvn dependency:go-offline -B

# Copiar el resto del código
COPY . .

# Build del proyecto
RUN mvn clean package -DskipTests

# Puerto que escucha Spring Boot
ENV PORT 8080
EXPOSE 8080

# Variables de entorno para DB
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-d4tu1f717kns73csrvog-a:5432/organic_8o66
ENV SPRING_DATASOURCE_USERNAME=organic_8o66_user
ENV SPRING_DATASOURCE_PASSWORD=hxp5VvHqkkVmayVS0DEjelGI2BE3slGj

# Comando de arranque
CMD ["java", "-jar", "target/OrganicJava-0.0.1-SNAPSHOT.jar"]