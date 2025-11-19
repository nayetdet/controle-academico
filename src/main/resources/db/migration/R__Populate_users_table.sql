INSERT INTO usuarios (responsavel_id, login, senha, perfil)
VALUES (NULL, 'admin', '$2a$12$TO4OPUJJz9QrAkvMpcGCd.9hN5dqAx6Ct32lZdOdywRXciHXEnNEG', 'ADMIN')
ON CONFLICT (login) DO NOTHING;
