## Controle Acadêmico — IFCE/Maracanaú

Mini sistema web em Spring Boot para gerenciamento de alunos, disciplinas e matrículas, seguindo a especificação do trabalho final de Tópicos em Java para a Web.

### Principais recursos
- CRUD completo de alunos, disciplinas e matrículas com validações (matrícula do aluno e código de disciplina únicos, impedimento de matrícula duplicada na mesma disciplina).
- Regras de integridade: exclusões respeitam vínculos existentes (ex.: não apagar disciplina com matrículas).
- Autenticação com formulário e senhas BCrypt; autorização com perfis `ADMIN` (acesso total) e `SECRETARIA` (gerencia alunos e matrículas, não altera disciplinas).
- Interface em Thymeleaf com navegação, feedback visual e estilos em `src/main/resources/static/css/main.css`.
- Flyway cria o esquema inicial e insere usuários padrão via migration repeatable.

### Pré-requisitos
- Java 21
- PostgreSQL acessível (configurado via variáveis de ambiente)

### Configuração e execução
1. Defina as variáveis de ambiente do banco (`DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`). Valores padrão: host `localhost`, porta `5432`, base `controleacademico`, usuário/senha `postgres`.
2. Rode a aplicação (Flyway é executado automaticamente):
   ```bash
   ./mvnw spring-boot:run
   ```
3. Acesse `http://localhost:8080`.

Usuários iniciais (via migration Flyway):
- ADMIN — login `admin` / senha `admin`
- SECRETARIA — login `secretaria` / senha `secretaria`

### Navegação
- `/login` — acesso com formulário.
- `/` — home com atalhos.
- `/alunos` — listagem, criação e edição de alunos.
- `/disciplinas` — listagem e gerenciamento (somente ADMIN altera).
- `/matriculas` — listagem e manutenção das matrículas (valida duplicidade).

### Observações
- Todas as criações registram qual usuário foi responsável.
- Formulários já incluem tokens CSRF.
- Testes utilizam o mesmo PostgreSQL configurado via variáveis de ambiente.
