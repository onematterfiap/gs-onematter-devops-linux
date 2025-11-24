# One Matter API - Backend

Este repositório contém o **Backend da API REST** para o projeto **One Matter**, desenvolvido em **Spring Boot 3** com **Java 17**. A API é o core do sistema, responsável pela autenticação, gerenciamento de perfis, vagas, candidaturas, testes e toda a lógica de negócio do recrutamento ético e tecnológico.

---

## 🎯 Ecossistema Skill Station: Recrutamento Ético e Tecnológico

A API Java é a espinha dorsal do ecossistema, integrando o aplicativo mobile (Frontend) e a estação de testes física (IoT).

-   **Candidatura Cega (Mobile / API Java):** A API gerencia o registro de novos **Candidatos** (`USER`), realizando a validação de CPF e E-mail, e armazena as **Skills** associadas ao perfil.
-   **Gestão por Recrutadores (`ADMIN`):** A API oferece endpoints protegidos (via JWT e `@PreAuthorize("hasRole('ADMIN')")`) para que recrutadores gerenciem **Empresas**, **Vagas**, **Questões** e **Recrutadores**.
-   **Fluxo de Testes (IoT/Skills Station):**
    -   A API expõe o endpoint `GET /testes/candidatura/{id}/questoes` para a estação IoT buscar o conteúdo da prova. Internamente, este endpoint chama a procedure `SP_REGISTRAR_INICIO` para registrar o status `EM_ANDAMENTO` na candidatura.
    -   O endpoint `POST /testes/submit-score` recebe a nota final (`score`) via corpo da requisição e chama a procedure `SP_FINALIZAR_PROVA`, atualizando o status da candidatura.

O objetivo é garantir a avaliação puramente baseada em habilidades, com um fluxo de trabalho seguro, transparente e auditável.

---

## 🔗 Informações de Acesso e Links

| Descrição                            | Link / Valor                                                                                                 |
| :----------------------------------- | :----------------------------------------------------------------------------------------------------------- |
| **Repositório da API (Este)**        | [https://github.com/mtslma/one-matter-api.git](https://github.com/mtslma/one-matter-api.git)                 |
| **Repositório Mobile (Frontend)**    | [https://github.com/onematterfiap/gs-onematter-mobile](https://github.com/onematterfiap/gs-onematter-mobile) |
| **URL Base da API (Padrão Local)**   | `http://localhost:8080/api`                                                                                  |
| **URL Base da API (Deploy Azure)**   | `http://68.211.72.156:8080/api`                                                                              |
| **Swagger / OpenAPI (Local)**        | `http://localhost:8080/api/swagger-ui/index.html`                                                            |
| **Swagger / OpenAPI (Deploy Azure)** | `http://68.211.72.156:8080/api/swagger-ui/index.html`                                                        |
| **Credencial Admin**                 | `admin@onematter.com` / `senhaSegura123`                                                                     |
| **Credencial Candidato (USER)**      | `candidato@onematter.com` / `senhaSegura123`                                                                 |

---

## ⚙️ Tecnologias e Arquitetura

-   **Linguagem & Framework:** Java 17, Spring Boot 3.
-   **Banco de Dados:** Oracle (JDBC Driver `ojdbc11`).
-   **Persistência:** Spring Data JPA.
-   **Segurança:** Spring Security (com autenticação stateless via JWT).
-   **Validação:** Jakarta Validation.
-   **Documentação:** SpringDoc OpenAPI 3.0 (Swagger UI).

### Estrutura de Autenticação e Autorização

-   **Perfis (`UsuarioRole`):** `ADMIN` (Recrutadores/Gerentes) e `USER` (Candidatos).
-   **Filtros JWT:** O `JwtAuthFilter` intercepta requisições, valida o token através do `TokenService`, e define o `UserDetails` no contexto do Spring Security, permitindo acesso a rotas `@PreAuthorize`.
-   **Segurança:** As rotas são protegidas usando `requestMatchers` e `@PreAuthorize("hasRole('ROLE')")`.

---

## 💾 Setup e Execução Local

### Pré-requisitos

-   **Java Development Kit (JDK) 17**.
-   **Maven** (ou usar o wrapper `mvnw` incluído).

### 1. Configuração do Banco de Dados

A API está configurada para usar um banco de dados **Oracle**. A configuração no arquivo `src/main/resources/application.yml` já está definida para as credenciais de teste com massa de dados provisionada (que inclui os usuários de acesso e dados iniciais no `data.sql`):

```yaml
  datasource:
  url: jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
  username: rm559728
  password: 250306
```

### NÃO ALTERE O APPLICATION.YML, ELE JÁ ESTÁ DEVIDAMENTE CONFIGURADO

### 2. Compilar e Rodar o Projeto

1.  **Clone o repositório:**

    ```bash
    git clone [https://github.com/mtslma/one-matter-api.git](https://github.com/mtslma/one-matter-api.git)
    cd one-matter-api
    ```

2.  **Construa e Inicie a aplicação:**
    Use o Maven Wrapper:

    ```bash

    # Limpa e instala dependências

    ./mvnw clean install

    # Inicia a aplicação Spring Boot (padrão porta 8080)

    ./mvnw spring-boot:run
    ```

---

## ☁️ Deploy no Azure com Docker

O projeto está configurado para ser empacotado em um contêiner **Docker** para facilitar o deploy em ambientes como **Azure App Services for Containers** ou **Azure Container Instances (ACI)**.

-   **URL Base da API em Produção:** `http://68.211.72.156:8080/api`

### Configuração do Docker Compose

O arquivo `docker-compose.yml` define a imagem a ser construída e as variáveis de ambiente necessárias para a conexão com o Oracle (FIAP) e as chaves JWT:

```yaml
version: '3.8'
services:
one-matter-api:
image: one-matter-api-oracle
build:
context: .
dockerfile: Dockerfile
container_name: one-matter-api-container
restart: always
ports: - "8080:8080"
environment: - ORACLE_URL=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL - ORACLE_USER=rm559728 - ORACLE_PASS=250306 - JWT_SECRET=aec3ec1f-53aa-4e82-93e7-702ab0194b80 # ...
```

### Dockerfile

O `Dockerfile` é um processo multi-stage que utiliza **Maven Alpine** para o build e **JRE Alpine** para a imagem final, garantindo um tamanho reduzido e a correção de certificados para o Maven:

```Dockerfile

# ... (BUILD STAGE com correção para Alpine Linux: RUN apk add --update ca-certificates)

FROM eclipse-temurin:17-jre-alpine

# Cria e usa o usuário sem privilégios 'userapp'

RUN adduser -h /home/userapp -s /bin/sh -D userapp
USER userapp

# Copia apenas o JAR do Build Stage

COPY --from=build /app/target/one-matter-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```

---

## 🖥️ Endpoints da API (Resumo)

A API é modularizada por controllers, com proteção de rota baseada em `ADMIN` ou `USER`.

### Endpoints de Autenticação e Perfil (Público/Autenticado)

| Rota             | Método | Descrição                                                                         | Permissão   |
| :--------------- | :----- | :-------------------------------------------------------------------------------- | :---------- |
| `/auth/login`    | POST   | Autentica e gera **JWT Token** e Refresh Token.                                   | Público     |
| `/auth/register` | POST   | Cadastra um novo `USER` (Candidato).                                              | Público     |
| `/usuarios/me`   | GET    | Busca o perfil completo do usuário logado (incluindo Skills e Formações).         | Autenticado |
| `/usuarios/me`   | PUT    | Atualiza dados básicos (`nome`, `genero`, `telefone`, `skills`) do perfil logado. | Autenticado |

### Endpoints de Candidato (`/candidato/me`)

| Rota                              | Método          | Descrição                                  |
| :-------------------------------- | :-------------- | :----------------------------------------- |
| `/vagas/{idVaga}/candidatar`      | POST            | Realiza a candidatura a uma vaga.          |
| `/candidato/me/candidaturas`      | GET             | Lista as candidaturas ativas do usuário.   |
| `/candidato/me/candidaturas/{id}` | DELETE          | **Cancela** uma candidatura (soft delete). |
| `/candidato/me/formacao`          | POST/GET/DELETE | Gerencia as formações do usuário logado.   |

### Endpoints do Fluxo de Teste (IoT/Skills Station)

| Rota                                | Método | Descrição                                                                                                                              | Permissão   |
| :---------------------------------- | :----- | :------------------------------------------------------------------------------------------------------------------------------------- | :---------- |
| `/testes/candidatura/{id}/questoes` | GET    | Busca questões do Teste e **registra o status `EM_ANDAMENTO`** na candidatura (via `SP_REGISTRAR_INICIO`).                             | Autenticado |
| `/testes/submit-score`              | POST   | Recebe a nota (`score`) e **finaliza a prova** (via `SP_FINALIZAR_PROVA`), atualizando o status da candidatura para `TESTE_SUBMETIDO`. | Autenticado |

### Endpoints de Gerenciamento (`ADMIN` Role)

| Rota                    | Recurso            | Métodos Liberados                          |
| :---------------------- | :----------------- | :----------------------------------------- |
| `/vagas`                | Vagas              | POST, PUT, DELETE (GET é público)          |
| `/empresas`             | Empresas           | GET, POST, PUT, DELETE                     |
| `/recrutadores`         | Recrutadores       | GET, POST, PUT, DELETE                     |
| `/skills`               | Skills             | POST, PUT, DELETE (GET é público)          |
| `/skills/associar-vaga` | Skills             | POST (Associa skill a uma vaga)            |
| `/questoes`             | Banco de Questões  | GET, POST, PUT, DELETE                     |
| `/admin/users`          | Gestão de Usuários | GET, POST (Cria `ADMIN`/`USER` gerenciado) |
