## API REST para cadastro de Usuários e Score

### Tecnologias utilizadas:

- Java 21
- Spring Boot 3.3.3
- Autenticação e Autorização via Spring Security e JWT
- H2
- Query By Example (QBE - JPA)
- Docker
- Swagger

### Swagger
Para acessar a documentação da aplicação pelo swagger: http://localhost:8001/swagger-ui/index.html#/

### Para iniciar a aplicação


1. Compilação
- Via maven:

`mvn clean package -DskipTests`
 
`mvn clean verify` (executar os testes)

- Via maven wrapper:

`./mvnw clean package -DskipTests` (Linux/MacOS)

`mvnw.cmd clean package -DskipTests` (Windows)

2. Execução (Necessário Docker)

`docker build -t <image-name>:<tag> .`

`docker run -d --name <container-name> -p 8001:8001 <image-name>:<tag>`

3. Verificar logs da aplicação

`docker exec -it <container-id> /bin/sh` (Linux/MacOS/Git Bash)

4. Parar a aplicação

`docker stop <container-name>`

5. Healthcheck

Validar o healthcheck via `docker ps`
