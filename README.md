# Gerenciador de Projetos

As tecnologias usadas para o projeto foram:
- MySQL
- Java
- Spring Boot
- Flyway Migration
- React

## Documentação Swagger

A documentação Swagger da aplicação está disponível em:
[Swagger UI](http://localhost:8080/swagger-ui/index.html#/)

## Requisitos

Para rodar o projeto, você precisará ter instalado:
- Java 17 (encoding: UTF-8)
- Docker
- Node.js

## Instruções para Rodar o Projeto

### Backend

1. Clone o repositório:
   ```sh
   git clone https://github.com/anthonyMeds/gerenciador-projetos

2. Suba o Docker Compose do banco de dados:
cd gerenciador-projetos/backend
docker-compose up -d

2. Suba a aplicação:
cd gerenciador-projetos/backend
./mvnw spring-boot:run


----- 
FRONTEND

1. Certifique-se de que o Node.jsesteja instalado.

2. Navegue até o diretório do frontend:

3. npm install 

4. npm start

5. Acesse o front-end em : 

http://localhost:3000/



APIs Disponíveis
As APIs disponíveis são para CRUD de tarefas e projetos.
