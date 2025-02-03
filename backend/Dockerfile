# Arquitetura arm e amd
FROM eclipse-temurin:17.0.8.1_1-jdk-jammy

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY . .

# Executar build com Maven
RUN mvn clean install -DskipTests

# Definir o comando de entrada
ENTRYPOINT ["java", "-jar", "target/projetos-0.0.1-SNAPSHOT.jar"]
