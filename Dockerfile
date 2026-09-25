# ========== ETAPA 1: BUILD ==========
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY . .

# Compilar el proyecto (saltando tests)
RUN mvn clean package -DskipTests

# ========== ETAPA 2: RUN ==========
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiar el JAR generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Puerto por defecto
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]