# 1. Imagen base con Java
FROM eclipse-temurin:17-jdk-alpine

# 2. Crear un directorio para la app
WORKDIR /app

# 3. Copiar pom.xml y descargar dependencias (cache eficiente)
COPY pom.xml .
RUN apk add --no-cache bash && \
    mvn dependency:go-offline -B

# 4. Copiar todo el código fuente
COPY src ./src

# 5. Construir la aplicación
RUN ./mvnw package -DskipTests

# 6. Exponer el puerto que Render usará
ENV PORT 10000
EXPOSE $PORT

# 7. Comando para ejecutar la app
CMD java -jar target/*.jar --server.port=$PORT