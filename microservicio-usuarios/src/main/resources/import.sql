INSERT INTO `usuarios` (username, password, activo, nombre, apellido, email) VALUES ('andres','12345',1, 'Andres', 'Guzman','profesor@bolsadeideas.com');
INSERT INTO `usuarios` (username, password, activo, nombre, apellido, email) VALUES ('admin','12345',1, 'John', 'Doe','jhon.doe@bolsadeideas.com');

INSERT INTO `roles` (nombre) VALUES ('ROL_USER');
INSERT INTO `roles` (nombre) VALUES ('ROL_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, rol_id) VALUES (1, 1);
INSERT INTO `usuarios_roles` (usuario_id, rol_id) VALUES (2, 1);
INSERT INTO `usuarios_roles` (usuario_id, rol_id) VALUES (2, 2);