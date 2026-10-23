# AluraFood — Microsserviço de Pedidos

Microsserviço desenvolvido com **Java e Spring Boot** para a aplicação AluraFood, explorando desenvolvimento de APIs REST, persistência de dados e comunicação assíncrona entre serviços.

## Tecnologias

- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- MySQL
- Flyway
- Bean Validation
- ModelMapper
- RabbitMQ
- Maven

## Funcionalidades e conceitos

- Desenvolvimento de APIs REST para gerenciamento de pedidos.
- Persistência de dados com Spring Data JPA e MySQL.
- Gerenciamento de migrações do banco de dados com Flyway.
- Validação de dados utilizando Bean Validation.
- Conversão entre entidades e DTOs com ModelMapper.
- Consumo assíncrono de mensagens utilizando RabbitMQ.

## Comunicação assíncrona com RabbitMQ

O serviço utiliza RabbitMQ para receber mensagens relacionadas aos pagamentos.

A implementação inclui:

- **Exchange `pagamentos.ex`:** exchange do tipo `fanout`, responsável por distribuir mensagens para as filas vinculadas.
- **Fila `pagamentos.detalhes-pedido`:** recebe as mensagens destinadas ao listener de pedidos.
- **`PagamentoListener`:** utiliza `@RabbitListener` para consumir as mensagens e receber os dados convertidos para `PagamentoDTO`.
- **Conversão JSON:** configuração de um conversor de mensagens para serialização e desserialização dos dados.

O listener atualmente lê e exibe informações como identificador do pagamento, pedido associado, valor e status, demonstrando a integração assíncrona entre componentes.

## Como executar

**Pré-requisitos:** Java 17, Maven, MySQL e RabbitMQ.

Clone o repositório:

```bash
git clone https://github.com/pedrobuzolin/alurafood-pedidos.git
cd alurafood-pedidos
```

Configure as credenciais e os endereços do MySQL e do RabbitMQ no arquivo de configuração da aplicação.

Execute no Windows:

```bash
mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

A aplicação depende dos serviços externos configurados para funcionar corretamente.

## Objetivo do projeto

Aprofundar conhecimentos em desenvolvimento backend com Java e Spring, explorando persistência de dados, construção de APIs REST e integração entre microsserviços por meio de mensageria assíncrona.
