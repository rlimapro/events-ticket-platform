<p align="center">
  <img src="https://i.ibb.co/Kj2PLQC5/movie-tickets-black-by-Vexels.png" width="120" alt="Event Ticket Platform Logo" />
</p>

<h1 align="center">Event Ticket Platform</h1>

<p align="center">
  Plataforma completa para gestão e venda de ingressos digitais, com autenticação OAuth2, QR Codes únicos e validação em tempo real.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java%2021-ED8B00?style=flat-square&logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring%20Boot%203-6DB33F?style=flat-square&logo=springboot&logoColor=white" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white" alt="Spring Security" />
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=flat-square&logo=postgresql&logoColor=white" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/Keycloak-4D4D4D?style=flat-square&logo=keycloak&logoColor=white" alt="Keycloak" />
  <img src="https://img.shields.io/badge/React%2019-61DAFB?style=flat-square&logo=react&logoColor=black" alt="React" />
  <img src="https://img.shields.io/badge/TypeScript-3178C6?style=flat-square&logo=typescript&logoColor=white" alt="TypeScript" />
  <img src="https://img.shields.io/badge/Vite-646CFF?style=flat-square&logo=vite&logoColor=white" alt="Vite" />
  <img src="https://img.shields.io/badge/Tailwind%20CSS%204-06B6D4?style=flat-square&logo=tailwindcss&logoColor=white" alt="Tailwind CSS" />
  <img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white" alt="Docker" />
  <img src="https://img.shields.io/badge/ZXing-FF6F00?style=flat-square&logo=qrcode&logoColor=white" alt="ZXing" />
  <img src="https://img.shields.io/badge/MapStruct-ED1C24?style=flat-square&logo=java&logoColor=white" alt="MapStruct" />
</p>

---

## 📖 Sobre o Projeto

Uma plataforma completa para gestão e venda de ingressos para eventos, desenvolvida com uma arquitetura moderna e segura, focada em escalabilidade e experiência do usuário. O projeto é um **monorepo** que integra um ecossistema robusto de tecnologias para backend e frontend.

---

## 🏗️ Arquitetura do Sistema

A aplicação segue o padrão de arquitetura de **Sistemas Distribuídos** com separação clara entre as camadas de apresentação, lógica de negócio e segurança.

### Fluxo de Autenticação e Autorização (OAuth2 + OIDC)
A segurança é baseada no protocolo **OpenID Connect (OIDC)**, utilizando o **Keycloak** como provedor de identidade (Identity Provider - IdP).

1.  **Client (Frontend React):** Atua como o cliente público. Quando um usuário tenta acessar uma área restrita, ele é redirecionado para o **Keycloak**.
2.  **Authorization Server (Keycloak):** Gerencia o login do usuário e, após a autenticação bem-sucedida, emite um **Access Token (JWT)** e um **ID Token**.
3.  **Resource Server (Backend Spring Boot):** Atua como o servidor de recursos. O frontend envia o Access Token no cabeçalho das requisições HTTP (`Authorization: Bearer <token>`). O backend valida o token e as permissões (roles) do usuário para processar a requisição.

<p align="center">
  <img src="https://i.ibb.co/7dqwnN0m/Screenshot-From-2026-03-06-10-42-37.png" alt="Diagrama da Arquitetura do Sistema" width="800" />
</p>

---

## 🚀 Tecnologias Utilizadas

### Backend (Java & Spring)
*   **Java 21:** Versão LTS mais recente, aproveitando recursos modernos da linguagem.
*   **Spring Boot 3.5:** Framework principal para construção da API REST.
*   **Spring Security & OAuth2 Resource Server:** Implementação robusta de segurança e validação de tokens JWT.
*   **Spring Data JPA:** Abstração para persistência de dados.
*   **PostgreSQL:** Banco de dados relacional robusto para ambiente de produção.
*   **MapStruct:** Mapeamento eficiente entre DTOs e Entidades.
*   **Lombok:** Redução de código boilerplate.
*   **ZXing (Zebra Crossing):** Biblioteca para geração e processamento de QR Codes para os ingressos.

### Frontend (React & Modern UI)
*   **React 19:** Biblioteca UI moderna com suporte às últimas funcionalidades.
*   **TypeScript:** Tipagem estática para maior segurança e produtividade.
*   **Vite:** Ferramenta de build extremamente rápida.
*   **Tailwind CSS 4:** Estilização utilitária de última geração.
*   **Radix UI:** Componentes acessíveis e sem estilização forçada (Headless UI).
*   **React OIDC Context:** Integração simplificada com o Keycloak.
*   **React QR Scanner:** Validação de ingressos em tempo real via câmera.

### Infraestrutura e DevOps
*   **Docker & Docker Compose:** Containerização de toda a infraestrutura (Banco de dados, Keycloak, Adminer).
*   **Keycloak:** Servidor de Gerenciamento de Identidade e Acesso (IAM).
*   **PostgreSQL:** Persistência de dados.

---

## ✨ Funcionalidades Principais

*   **Gestão de Eventos:** Criação, edição e listagem de eventos com suporte a imagens e descrições detalhadas.
*   **Tipos de Ingressos:** Configuração de múltiplos tipos de ingressos por evento (ex: VIP, Meia, Inteira).
*   **Venda de Ingressos:** Fluxo completo de compra integrado com a identidade do usuário.
*   **Ingressos Digitais com QR Code:** Cada ingresso gera um QR Code único e seguro para validação.
*   **Validação em Tempo Real:** Dashboard para organizadores validarem ingressos via scanner de QR Code integrado no navegador.
*   **Dashboard do Organizador:** Visão geral dos eventos criados e controle de acessos.
*   **Controle de Acesso Baseado em Roles (RBAC):** Diferenciação entre usuários comuns (Attendees) e organizadores.

---

## 📂 Estrutura do Monorepo

```text
├── backend/            # API REST Spring Boot
│   ├── src/            # Código fonte Java
│   ├── pom.xml         # Dependências Maven
│   └── docker-compose  # Infraestrutura (DB, Keycloak)
├── frontend/           # Aplicação Web React
│   ├── src/            # Componentes, Hooks e Páginas
│   ├── tailwind.config # Configurações de estilo
│   └── package.json    # Dependências Node.js
└── README.md           # Documentação do projeto
```

---

## 🛠️ Como Executar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/event-ticket-platform.git
    ```

2.  **Suba a infraestrutura (Docker):**
    Navegue até a pasta `backend` e execute:
    ```bash
    docker-compose up -d
    ```

3.  **Execute o Backend:**
    ```bash
    cd backend
    ./mvnw spring-boot:run
    ```

4.  **Execute o Frontend:**
    ```bash
    cd frontend
    npm install
    npm run dev
    ```

---

*Este projeto foi desenvolvido para fins de portfólio, demonstrando habilidades em arquitetura fullstack, segurança com OAuth2 e desenvolvimento de sistemas escaláveis.*
