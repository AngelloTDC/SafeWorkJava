# SafeWork WebApp (Java Advanced - FIAP)

Painel Web Administrativo do SafeWork, feito em Spring Boot MVC + Thymeleaf.

## Requisitos atendidos
- Spring annotations + DI
- Model/DTO
- JPA
- Bean Validation
- Cache (Caffeine)
- i18n PT-BR/EN
- Paginação
- Spring Security (login + roles)
- Global exception handler
- Mensageria RabbitMQ (consumer cria alertas)
- IA generativa com Spring AI (Ollama)
- Deploy pronto via Docker

## Como rodar local
1. Suba o RabbitMQ (opcional, mas recomendado):
   ```bash
   docker-compose up -d
   ```
2. Rode o app:
   ```bash
   mvn spring-boot:run
   ```
3. Acesse:
   - WebApp: http://localhost:8080
   - H2 Console: http://localhost:8080/h2
   - RabbitMQ UI: http://localhost:15672 (guest/guest)

## Login
- admin / admin123 (ROLE_ADMIN)
- supervisor / supervisor123 (ROLE_SUPERVISOR)

## IA (Ollama)
Instale e rode:
```bash
ollama serve
ollama pull llama3.1
```

Depois use a tela **Análise IA**.

## Enviar alerta por fila
Envie uma mensagem JSON para a exchange `safework.alerts.x` com routing key `alerts.created`.

Exemplo:
```json
{
  "employeeId": 1,
  "type": "NO_HELMET",
  "severity": "HIGH",
  "description": "Sem capacete detectado pela câmera 2."
}
```
