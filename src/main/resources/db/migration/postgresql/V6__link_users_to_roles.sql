INSERT INTO users_roles(user_id, role_id) VALUES ((select id from app_user where username='emmanouil.psanis@gmail.com'), (select id from role WHERE name='ROLE_ADMIN'));

