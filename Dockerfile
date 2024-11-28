# Etapa 1: Build da aplicação
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos necessários
COPY pom.xml .
COPY src ./src

# Compila a aplicação e gera o JAR
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final para execução
FROM eclipse-temurin:17-jdk-jammy

# Define o diretório de trabalho
WORKDIR /app

# Cria o diretório de uploads
RUN mkdir -p /app/uploads

# Copia o JAR gerado na etapa de build
COPY --from=build /app/target/backend-kero-0.0.1-SNAPSHOT.jar app.jar

# Define variáveis de ambiente (ajustáveis em tempo de execução)
ENV UPLOAD_DIR=/app/uploads \
    JAVA_OPTS="-Xms256m -Xmx512m"

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dupload.dir=$UPLOAD_DIR -jar app.jar"]
