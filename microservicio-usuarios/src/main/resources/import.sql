INSERT INTO usuarios (username, password, activo, nombre, apellido, email) VALUES ('andres','$2a$10$AYBsh4V6o5a81Ho5ANffQedP0L0C3VTz00ZinfV4lSSWZWNzXYKzG',true, 'Andres', 'Guzman','profesor@bolsadeideas.com');
INSERT INTO usuarios (username, password, activo, nombre, apellido, email) VALUES ('admin','$2a$10$AYBsh4V6o5a81Ho5ANffQedP0L0C3VTz00ZinfV4lSSWZWNzXYKzG',true, 'John', 'Doe','jhon.doe@bolsadeideas.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 2);