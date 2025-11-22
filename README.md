
# SafeWork – WebApp

Painel web administrativo do **SafeWork**, uma plataforma para monitorar segurança e bem-estar no trabalho usando visão computacional, mensageria e IA generativa.

---

## 👥 Integrantes

- Angello Turano da Costa – RM556511
- Cauã Sanches de Santana – RM558317
- Gustavo de Souza Amorim – RM556999

Curso: FIAP – Java Advanced

---

## 🎯 Visão geral

Este projeto é o **módulo Web** do SafeWork:

- Painel para time de segurança / supervisores
- Gestão de funcionários
- Gestão de alertas de segurança (criados via sistema ou fila RabbitMQ)
- Dashboard com KPIs
- Tela de apoio com IA generativa para interpretar situações de segurança

O objetivo é mostrar na prática vários tópicos da disciplina:

- Spring Boot MVC
- JPA / Hibernate / Banco relacional
- Segurança (Spring Security)
- Mensageria (RabbitMQ)
- IA generativa (Spring AI)
- Boas práticas de camadas (Controller / Service / Repository / DTO)

---

## 🧱 Tecnologias principais

- **Java 21**
- **Spring Boot 3**
  - Spring Web (MVC + Thymeleaf)
  - Spring Data JPA
  - Spring Security
  - Spring Cache (Caffeine)
  - Bean Validation
- **Banco de dados**
  - H2 (memória, para desenvolvimento)
  - Config pronto para Postgres
- **Mensageria**
  - RabbitMQ (producer + consumer de alertas)
- **IA**
  - Spring AI + ChatClient (ex.: Ollama / modelo local)
- **Outros**
  - Lombok (em algumas partes)
  - Maven
  - Docker / Docker Compose

---

## ✅ Requisitos da disciplina atendidos

- Uso de **anotações do Spring** e injeção de dependência em controllers, services e configs
- Separação em **Model / DTO / Controller / Service / Repository**
- **JPA/Hibernate** com entidades mapeadas, relacionamentos e queries
- **Bean Validation** em DTOs (EmployeeDTO, AlertDTO) com mensagens de erro exibidas na view
- **Cache** com Caffeine para lista de funcionários
- **i18n (internacionalização)** para PT-BR/EN (labels e mensagens)
- **Paginação** em listas (ex.: funcionários, alertas)
- **Spring Security**
  - Autenticação em formulário
  - Perfis de acesso (`ROLE_ADMIN`, `ROLE_SUPERVISOR`)
- **Tratamento de erros** com página dedicada (`error` / fragmento de erro) e mensagens de negócio amigáveis
- **Mensageria RabbitMQ**
  - Producer publica alertas
  - Consumer recebe mensagem e cria `Alert` na base
- **Integração com IA** via Spring AI (ChatClient) para análise de contexto de segurança
- **Deploy preparado com Docker / Docker Compose**

---

## 🧩 Funcionalidades do WebApp

### Dashboard (`/`)

- KPIs:
  - Total de funcionários cadastrados
  - Quantidade de alertas abertos
  - Alertas gerados nas últimas 24h
  - Estimativa de “horas seguras”
- Tabela com últimos alertas criados (lista reduzida)

### Funcionários (`/employees`)

- Listagem paginada de funcionários
- Cadastro de novo funcionário
- Edição de funcionário existente
- Exclusão de funcionário
  - Bloqueio amigável caso o funcionário tenha alertas associados (mensagem de negócio)
- Validações:
  - Nome obrigatório
  - E-mail obrigatório e válido
  - Cargo, departamento e status obrigatórios

### Alertas (`/alerts`)

- Listagem paginada de alertas
- Criação manual de alerta (associado a um funcionário)
- Mudança de status para **RESOLVED**
- Validação de campos:
  - Funcionário obrigatório
  - Tipo obrigatório
  - Severidade obrigatória
  - Descrição com limite de caracteres
- Alertas também podem ser criados automaticamente via **fila RabbitMQ**

### IA – Análise de Segurança (`/ai`)

- Tela de texto para o usuário descrever a situação (ex.: “operador sem luvas perto de uma serra”)
- O serviço monta um prompt para IA como **especialista em segurança do trabalho** e pede:
  1. O que está acontecendo
  2. Risco envolvido
  3. EPI faltando
  4. Recomendações

A resposta estruturada é exibida na própria tela, como apoio ao supervisor.

---

## 🔐 Login

A aplicação vem com usuários fixos para teste:

- `admin / admin123` – acesso administrativo
- `supervisor / supervisor123` – acesso de supervisor

---

## 🗄 Banco de dados

Por padrão, o app usa **H2 em memória**:

- URL: `jdbc:h2:mem:safework`
- Usuário: `sa`
- Senha: *(vazio)*

Console H2:

- `http://localhost:8080/h2`
  > As credenciais estão em `application.yml`.

Na inicialização, um **DataInitializer** cria:

- Funcionários de exemplo
- Alguns alertas iniciais para o dashboard não ficar vazio

---

## 📨 RabbitMQ – fila de alertas

### Subindo RabbitMQ com Docker

Na raiz do projeto:

```bash
docker-compose up -d
```

- Management UI: `http://localhost:15672`
  - usuário: `guest`
  - senha: `guest`

### Troca de mensagens

Configuração usada no código (RabbitMQConfig):

- **Exchange**: `safework.alerts.x`
- **Routing key**: `alerts.created`
- **Fila**: `safework.alerts.q`

### Exemplo de payload

Envie uma mensagem JSON para a exchange `safework.alerts.x` com a routing key `alerts.created`:

```json
{
  "employeeId": 1,
  "type": "NO_HELMET",
  "severity": "HIGH",
  "description": "Sem capacete detectado pela câmera 2."
}
```

O `AlertConsumer` recebe essa mensagem, monta um `AlertDTO` e grava um novo alerta na base.

---

## 🤖 IA (Spring AI / Ollama)

Exemplo com **Ollama** rodando localmente:

1. Instale o Ollama e rode o servidor:

   ```bash
   ollama serve
   ```

2. Baixe um modelo (exemplo):

   ```bash
   ollama pull llama3.1
   ```

3. Configure o modelo no `application.yml` do projeto (nome do modelo etc.).

Depois disso, na tela **IA de ajuda geral sobre EPI's** (`/ai`), basta:

- Escrever uma breve descrição do cenário em português
- A IA retorna uma análise com:
  - Situação
  - Risco
  - EPI faltando
  - Recomendações

---

## 🏃 Como rodar o projeto

Pré-requisitos:

- Java 21
- Maven
- Docker (opcional, recomendado para RabbitMQ)

### 1. Subir RabbitMQ (opcional, mas recomendado)

```bash
docker-compose up -d
```

### 2. Rodar a aplicação

Com Maven:

```bash
mvn spring-boot:run
```

Ou pelo IntelliJ:

- Rodar a classe `SafeWorkWebAppApplication`.

### 3. Acessar

- WebApp: `http://localhost:8080`
- H2 Console: `http://localhost:8080/h2`
- RabbitMQ UI: `http://localhost:15672`

---

## 🗂 Estrutura geral do projeto (visão rápida)

- `br.com.safework.controller` – controllers MVC (Dashboard, Employee, Alert, AI)
- `br.com.safework.service` – regras de negócio (EmployeeService, AlertService, AiService)
- `br.com.safework.model` – entidades JPA (Employee, Alert, enums)
- `br.com.safework.dto` – DTOs usados nos formulários e integração
- `br.com.safework.repository` – interfaces Spring Data JPA
- `br.com.safework.messaging` – producer/consumer e modelos de mensagem para RabbitMQ
- `br.com.safework.config` – configurações (DataInitializer, RabbitMQ, segurança, cache, i18n)
- `src/main/resources/templates` – páginas Thymeleaf (dashboard, employees, alerts, ai, fragments)
- `src/main/resources/i18n` – arquivos de mensagens (PT-BR / EN)

---

## 📌 Observações finais

- O foco é **demonstrar os pontos cobrados em Java Advanced** aplicados num cenário de segurança do trabalho.
- A aplicação foi pensada para ser fácil de subir para a banca: subir RabbitMQ, rodar Spring Boot, logar e navegar pelos fluxos principais.

Este README serve como roteiro para rodar o sistema e também como guia de apresentação para a banca.
