# Crud

Este projeto é uma aplicação Kotlin que integra com o Amazon SQS e um banco de dados MySQL. Ele fornece um CRUD (Create, Read, Update, Delete) para gerenciar usuários.

## Índice

- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Instalação](#instalação)
- [Uso](#uso)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Testes](#testes)
- [Contribuição](#contribuição)
- [Licença](#licença)

## Tecnologias Utilizadas

- Kotlin
- Spring Boot
- MySQL
- AWS SDK (para SQS)
- JUnit (para testes)

## Instalação

Siga os passos abaixo para configurar o projeto em sua máquina local:

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/leandroperdizo/crud-test-kotlin
   cd crud-test-kotlin
   ```text

2. **Configure o banco de dados**:
    - Crie um banco de dados MySQL chamado `crud`.
    - Atualize as credenciais no arquivo `src/main/resources/application.properties`:
      ```properties
      spring.application.name=crud-test-kotlin
      spring.datasource.url=jdbc:mysql://localhost:3306/crud
      spring.datasource.username=root
      spring.datasource.password=1234
      spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
      spring.jpa.hibernate.ddl-auto=update
      sqs.queue.url=https://sqs.us-east-1.amazonaws.com/123456789012/my-queue
      ```text

3. **Adicione as dependências**:
    - Certifique-se de que as dependências estão corretamente configuradas no seu `build.gradle.kts` ou `pom.xml`.

4. **Execute a aplicação**:
   ```bash
   ./gradlew bootRun
   ```text

## Uso

Descreva como usar seu projeto. Inclua exemplos de chamadas de API, como enviar e receber mensagens do SQS, e como interagir com o banco de dados.

### Exemplos de API

- **Criar Usuário**:
  ```http
  POST /user
  Content-Type: application/json

  {
      "name": "Alice",
      "email": "alice@example.com"
  }
  ```text

- **Listar Usuários**:
  ```http
  GET /user
  ```text

- **Buscar Usuário por ID**:
  ```http
  GET /user/{id}
  ```text

- **Atualizar Usuário**:
  ```http
  PUT /user/{id}
  Content-Type: application/json

  {
      "name": "Alice Updated",
      "email": "alice.updated@example.com"
  }
  ```text

- **Deletar Usuário**:
  ```http
  DELETE /user/{id}
  ```text

## Estrutura do Projeto

/crud-test-kotlin
│
├── src
│   ├── main
│   │   ├── kotlin
│   │   │   └── com
│   │   │       └── example
│   │   │           ├── controller
│   │   │           ├── service
│   │   │           ├── repository
│   │   │           └── model
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── kotlin
│           └── com
│               └── example
│                   └── UserServiceTest.kt

    
