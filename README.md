# Workshop Spring Boot - CI/CD com Docker

Este projeto é uma aplicação Spring Boot que oferece uma API REST para gerenciamento de itens, banco de dados H2 em memória, testes automatizados e suporte a containerização com Docker. 

## 🛠 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.5.0
- Spring Data JPA
- Spring Web
- Spring Thymeleaf
- Banco de dados H2 (em memória)
- Maven
- Docker
- JUnit 5 & Mockito
- Lombok



## Como Executar

### Pré-requisitos

- Java 21
- Maven
- Docker 

### Localmente com Maven

```bash
mvn clean package
java -jar target/workshop-0.0.1-SNAPSHOT.jar
```

Acesse: [http://localhost:8080](http://localhost:8080)

### Com Docker

```bash
docker build -t workshop-app .
docker run -p 8080:8080 workshop-app
```

## Testes

O projeto possui testes automatizados com cobertura para:

- Criação e recuperação de itens via API REST
- Validação de retorno vazio
- Teste da página inicial web

Execute com:

```bash
mvn test
```

## Endpoints da API

| Método | Rota         | Descrição                    |
|--------|--------------|------------------------------|
| GET    | `/`          | Página inicial web(Thymeleaf) |
| GET    | `/items`     | Lista todos os itens         |
| POST   | `/items`     | Cria um novo item            |
| GET    | `/items/{id}`| Retorna um item por ID       |

## Banco de Dados

- Utiliza o H2 Database em memória

## 📦 Dockerfile

```dockerfile
FROM openjdk:21

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
```



**Arilson Gomes**  

---
