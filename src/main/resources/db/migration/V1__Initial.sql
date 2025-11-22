create table if not exists usuarios
(
    id               bigserial primary key,
    responsavel_id   bigint,
    login            varchar(255) not null unique,
    senha            varchar(255) not null,
    perfil           varchar(255) not null
);

create table if not exists alunos
(
    id               bigserial primary key,
    responsavel_id   bigint,
    nome             varchar(255) not null,
    email            varchar(255) not null,
    matricula        varchar(255) not null unique,
    data_nascimento  date         not null,
    status           varchar(255) not null
);

create index if not exists idx_matricula on alunos (matricula);

create table if not exists disciplinas
(
    id             bigserial primary key,
    responsavel_id bigint,
    codigo         varchar(255) not null unique,
    nome           varchar(255) not null,
    carga_horaria  integer      not null,
    semestre       varchar(255) not null
);

create index if not exists idx_codigo on disciplinas (codigo);

create table if not exists matriculas
(
    id                bigserial primary key,
    responsavel_id    bigint,
    aluno_id          bigint      not null,
    disciplina_id     bigint      not null,
    data_matricula    date        not null,
    situacao          varchar(255) not null,
    nota_final        double precision,
    constraint uk_matricula_aluno_disciplina unique (aluno_id, disciplina_id)
);

alter table usuarios
    add constraint fk_usuario_responsavel foreign key (responsavel_id) references usuarios (id);

alter table alunos
    add constraint fk_aluno_responsavel foreign key (responsavel_id) references usuarios (id);

alter table disciplinas
    add constraint fk_disciplina_responsavel foreign key (responsavel_id) references usuarios (id);

alter table matriculas
    add constraint fk_matricula_responsavel foreign key (responsavel_id) references usuarios (id);

alter table matriculas
    add constraint fk_matricula_aluno foreign key (aluno_id) references alunos (id);

alter table matriculas
    add constraint fk_matricula_disciplina foreign key (disciplina_id) references disciplinas (id);
