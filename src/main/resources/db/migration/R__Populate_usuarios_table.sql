insert into usuarios (login, senha, perfil)
values ('admin', '$2y$10$FFO57hAi1iQATDPsXCjayOnnTc7Tz/YxRWGJbW3livOAh8S.krLv2', 'ADMIN')
on conflict (login) do nothing;

insert into usuarios (login, senha, perfil, responsavel_id)
select 'secretaria', '$2y$10$x5j809XjSSvC.wXTvMF5oOjsvvS/YTbUMjvuJRnVR/SM1XtqUWpa2', 'SECRETARIA', u.id
from usuarios u
where u.login = 'admin'
on conflict (login) do nothing;
