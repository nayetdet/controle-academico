## Controle Acadêmico — Trabalho Final Tópicos em Java para a Web (IFCE/Maracanaú)

Mini sistema web desenvolvido como trabalho final da disciplina **Tópicos em Java para a Web**, do IFCE – Campus Maracanaú.  
O objetivo é demonstrar domínio de **Spring Boot**, **JPA/Hibernate**, **Spring Security** e **Thymeleaf** na construção de um mini sistema de controle acadêmico.

---

### 1. Escopo do Sistema

O sistema implementa um controle acadêmico mínimo com as seguintes funcionalidades:
- CRUD completo de **Alunos**, **Disciplinas** e **Matrículas**.
- Regras básicas de negócio (integridade referencial e validações).
- Controle de acesso com **autenticação** e **autorização** via Spring Security.
- Telas Thymeleaf para todas as operações principais.

---

### 2. Tecnologias Utilizadas

- **Java 21**
- **Spring Boot** (Web, Thymeleaf, Validation)
- **Spring Data JPA / Hibernate**
- **Spring Security** (com BCrypt)
- **Flyway** (migrações de banco)
- **PostgreSQL**
- Maven

---

### 3. Modelo de Domínio e Regras de Negócio

Entidades principais:
- **Usuário**
  - Campos: `id`, `login`, `senha` (BCrypt), `role`.
  - Responsável pelas credenciais de acesso ao sistema.
  - Todas as operações de **cadastro** (create) de Aluno, Disciplina e Matrícula registram qual usuário realizou a ação.

- **Aluno**
  - Campos: `id (Long)`, `nome (String)`, `matricula (String, única)`, `email (String)`, `dataNascimento (LocalDate)`, `status` (`ATIVO` / `INATIVO`).

- **Disciplina**
  - Campos: `id (Long)`, `codigo (String, único)`, `nome (String)`, `cargaHoraria (Integer)`, `semestre (String)`.

- **Matrícula**
  - Campos: `id (Long)`, `aluno` (ManyToOne para Aluno), `disciplina` (ManyToOne para Disciplina), `dataMatricula (LocalDate)`, `situacao` (`CURSANDO`, `APROVADO`, `REPROVADO`, `TRANCADO`), `notaFinal (Double, opcional)`.

Regras de integridade e negócio:
- Impede que um mesmo aluno seja matriculado mais de uma vez, simultaneamente, na mesma disciplina (validação na camada de serviço e/ou constraint única no banco).
- Impede exclusões que violem integridade (ex.: não permite excluir disciplina com matrículas associadas, conforme especificação).

---

### 4. Segurança — Spring Security

**Autenticação**
- Login via formulário customizado em `/login`.
- Senhas persistidas com criptografia **BCrypt** no banco de dados.

**Autorização / Perfis de Acesso**
- `ROLE_ADMIN`
  - Acesso completo (CRUD) sobre Alunos, Disciplinas e Matrículas.
- `ROLE_SECRETARIA`
  - Pode gerenciar **Alunos** e **Matrículas**.
  - Não pode gerenciar ou excluir **Disciplinas** (apenas visualização, conforme regra de negócio).

**Controle de Acesso (URLs)**
- Páginas públicas:
  - `/` (home)
  - `/login` (formulário de autenticação)
- Endpoints restritos (sugestão/implementação típica):
  - `/admin/**` — apenas `ROLE_ADMIN`.
  - `/alunos/**`, `/disciplinas/**`, `/matriculas/**` — `ROLE_ADMIN` e `ROLE_SECRETARIA`, respeitando as permissões de cada perfil.

Usuários iniciais (criados via Flyway):
- ADMIN — login `admin` / senha `admin`
- SECRETARIA — login `secretaria` / senha `secretaria`

---

### 5. Telas e Navegação (Thymeleaf)

Páginas mínimas exigidas pela especificação e atendidas pelo sistema:
- **Tela de login**
  - Formulário de autenticação em `/login`.

- **Home**
  - Página inicial em `/` com atalhos para as principais áreas do sistema.

- **Alunos**
  - `/alunos` — listagem de alunos (listar todos).
  - Link para **criar novo** aluno.
  - Ações: **editar** / **excluir**.

- **Disciplinas**
  - `/disciplinas` — listagem de disciplinas.
  - Formulário de cadastro/edição de disciplina.
  - Acesso de escrita restrito conforme role (SECRETARIA não altera).

- **Matrículas**
  - `/matriculas` — listagem de matrículas.
  - Exibe: aluno, disciplina, situação, nota.
  - Tela para cadastrar/editar matrículas respeitando as regras de negócio (impede duplicidade).

Toda a camada de apresentação é construída com **Thymeleaf**, com estilos em  
`src/main/resources/static/css/main.css`.

---

### 6. Pré-requisitos

- Java 21 instalado
- PostgreSQL em execução e acessível
- Maven (ou uso do wrapper `mvnw`)

---

### 7. Configuração do Banco de Dados

O projeto utiliza variáveis de ambiente para configurar o acesso ao PostgreSQL:
- `DB_HOST` (padrão: `localhost`)
- `DB_PORT` (padrão: `5432`)
- `DB_NAME` (padrão: `controleacademico`)
- `DB_USERNAME` (padrão: `postgres`)
- `DB_PASSWORD` (padrão: `postgres`)

As migrações do **Flyway** criam o esquema inicial e inserem os usuários padrão.

---

### 8. Como Executar o Projeto

Você pode subir o ambiente em modo **desenvolvimento (dev)** ou **homologação (homolog)**.

**Opção 1 — Ambiente de desenvolvimento (Docker + Maven local)**
1. Certifique-se de ter **Docker**, **Docker Compose** e **Maven** (ou o wrapper `mvnw`) instalados.
2. Suba apenas o banco de dados PostgreSQL com o compose de desenvolvimento:
   ```bash
   docker compose -f docker-compose.dev.yml up
   ```
3. Com o banco no ar, na raiz do projeto, execute a aplicação com Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Acesse a aplicação em:
   - `http://localhost:8080`
5. Faça login com um dos usuários iniciais:
   - `admin` / `admin`
   - `secretaria` / `secretaria`

**Opção 2 — Ambiente de homologação (Docker completo)**
1. Certifique-se de ter **Docker** e **Docker Compose** instalados.
2. Na raiz do projeto, utilize o arquivo `docker-compose.homolog.yml`, que sobe a aplicação e o banco:
   ```bash
   docker compose -f docker-compose.homolog.yml up
   ```
3. Aguarde os containers subirem e acesse:
   - `http://localhost:8080`
4. Autentique-se com os mesmos usuários iniciais (`admin` / `secretaria`).
