# Backend - Link Hub

Este é o backend do projeto **URL Shortener com QR Code**, desenvolvido em arquitetura de **microserviços** com **Spring Boot**, **Spring Cloud** e **Docker**.  
O sistema permite encurtar URLs, armazená-las em banco de dados e gerar QR Codes para acesso rápido.

---

## 🏗 Arquitetura

- **API Gateway** → Ponto único de entrada para as requisições HTTP.
- **Eureka Server** → Serviço de registro e descoberta de microserviços.
- **Shortener Service** → Cria e gerencia URLs encurtadas (com persistência no banco).
- **QR Code Service** → Gera imagens de QR Code com base no link encurtado.

---

## 🚪 Endpoints principais

> Todos os endpoints são acessíveis **via API Gateway** (porta `8080` localmente).

### Shortener Service (`/api/links`)

| Método | Rota                  | Descrição                      | Corpo da Requisição                      |
|--------|-----------------------|--------------------------------|------------------------------------------|
| **POST** | `/api/links/shorten`   | Encurta uma URL                 | `{ "originalLink": "https://site.com" }` |
| **GET**  | `/api/links/{shortLink}` | Redireciona para a URL original | -                                        |
| **GET**  | `/api/links/clicks`      | Retorna a contagem de cliques de um link encurtado (via parâmetro `fullLink`) | - (usar query param: `fullLink`) |

**Exemplo de resposta (POST /shorten):**
```json
{
  "id": 1,
  "shortLink": "http://localhost:8080/api/links/abc123",
  "originalLink": "https://meusite.com",
  "createdAt": "2025-08-11T14:35:00",
  "qrCodeLink": "http://localhost:8080/api/qrcode/generate?url=http://localhost:8080/api/links/abc123"
}
```

### Como obter a contagem de cliques de um link encurtado

Para saber quantas vezes um link encurtado foi acessado, use o endpoint:

GET /api/links/clicks

Você deve passar a URL completa do link encurtado no parâmetro de consulta `url`.


**Exemplo de requisição:**

```http
GET http://localhost:8080/api/links/clicks?url=http://localhost:8080/api/links/abc123
```