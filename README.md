## Controle Acadêmico — IFCE/Maracanaú

Mini sistema web em Spring Boot para gerenciamento de alunos, disciplinas e matrículas, seguindo a especificação do trabalho final de Tópicos em Java para a Web.

### Principais recursos
- CRUD completo de alunos, disciplinas e matrículas com validações (matrícula de aluno e código de disciplina únicos, impedimento de matrícula duplicada na mesma disciplina).
- Regras de integridade: exclusões respeitam vínculos existentes (ex.: não apagar disciplina com matrículas).
- Autenticação com formulário e senhas BCrypt; autorização com perfis `ADMIN` (acesso total) e `SECRETARIA` (gerencia alunos e matrículas, não altera disciplinas).
- Interface em Thymeleaf com navegação, mensagens de feedback e estilos isolados em `src/main/resources/static/css/main.css`.

### Pré-requisitos
- Java 21
- PostgreSQL rodando e acessível

### Configuração e execução
1. Configure as variáveis de ambiente do banco (ex.: `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`). O profile `dev` usa por padrão `.secrets/keys/app.key|app.pub` já presentes.
2. Rode a aplicação (ativa Flyway automaticamente):
   ```bash
   SPRING_PROFILES_ACTIVE=dev ./mvnw spring-boot:run
   ```
3. Acesse `http://localhost:8080`.

Usuário inicial (carga de dados do Flyway):
- Login: `admin`
- Senha: `admin`

### Navegação
- `/login` — acesso com formulário.
- `/alunos` — listagem, criação e edição de alunos.
- `/disciplinas` — listagem e gerenciamento (somente ADMIN altera).
- `/matriculas` — listagem e manutenção das matrículas (valida duplicidade).

### Observações
- Todas as criações registram qual usuário foi responsável.
- As páginas exibem mensagens de sucesso/erro e já incluem tokens CSRF nos formulários.
