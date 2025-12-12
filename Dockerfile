# ===== Etapa 1: Build con Maven =====
FROM maven:3.9.4-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copia el pom.xml y descarga dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el código fuente
COPY src ./src

# Compila y genera el JAR (sin tests para que sea más rápido)
RUN mvn package -DskipTests

# ===== Etapa 2: Imagen final ligera =====
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia el JAR generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Puerto que expondrá tu aplicación
EXPOSE 8080

# Comando para correr la app
CMD ["java", "-jar", "app.jar"]
