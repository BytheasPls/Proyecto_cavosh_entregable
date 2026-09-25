# ========== ETAPA 1: BUILD ==========
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copiar el wrapper de Maven
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Dar permisos de ejecución al wrapper
RUN chmod +x mvnw

# Descargar dependencias (cache de Docker)
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar el proyecto (saltando tests)
RUN ./mvnw clean package -DskipTests

# ========== ETAPA 2: RUN ==========
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiar el JAR generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Puerto por defecto
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]