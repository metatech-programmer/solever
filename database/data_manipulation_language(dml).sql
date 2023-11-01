
INSERT INTO categorias (nombre_categoria) VALUES ('ROLE_ADMIN');
INSERT INTO categorias (nombre_categoria) VALUES ('ROLE_TEEN');
INSERT INTO categorias (nombre_categoria) VALUES ('ROLE_SPECIALIST');
INSERT INTO categorias (nombre_categoria) VALUES ('ROLE_ADULT');
INSERT INTO categorias (nombre_categoria) VALUES ('ROLE_ELDERLY');


INSERT INTO usuarios (id_categoria, nombre_usuario, apellido_usuario, fecha_nacimiento_usuario, genero_usuario, numero_tel_usuario, foto_cedula_usuario, correo_usuario, password_usuario, numero_documento_usuario) VALUES (1, 'admin', 'admin', '2000-02-02', 'Hombre', '0000000000', 'cc.pdf', 'admin@a.com', '$2a$12$wDZpV6IHLfs/YlGrDUYhteihIyKT.Bm0ml8TkX/Ysv3i4D/51J7be', '0000000');
INSERT INTO usuarios (id_categoria, nombre_usuario, apellido_usuario, fecha_nacimiento_usuario, genero_usuario, numero_tel_usuario, foto_cedula_usuario, correo_usuario, password_usuario, numero_documento_usuario) VALUES (3, 'defaultSpecialist', 'specialist', '2000-02-02', 'Otro', '00000000', 'cedula.pdf', 'defaultS@d.com', '$2a$12$wDZpV6IHLfs/YlGrDUYhteihIyKT.Bm0ml8TkX/Ysv3i4D/51J7be', '00000000');
INSERT INTO usuarios (id_categoria, nombre_usuario, apellido_usuario, fecha_nacimiento_usuario, genero_usuario, numero_tel_usuario, foto_cedula_usuario, correo_usuario, password_usuario, numero_documento_usuario) VALUES (2, 'defaultUser1', 'user1', '2000-02-02', 'Otro', '00000000', 'cedula1.pdf', 'defaultU1@d.com', '$2a$12$wDZpV6IHLfs/YlGrDUYhteihIyKT.Bm0ml8TkX/Ysv3i4D/51J7be', '00000000');
INSERT INTO usuarios (id_categoria, nombre_usuario, apellido_usuario, fecha_nacimiento_usuario, genero_usuario, numero_tel_usuario, foto_cedula_usuario, correo_usuario, password_usuario, numero_documento_usuario) VALUES (4, 'defaultUser2', 'user2', '2000-02-02', 'Otro', '00000000', 'cedula2.pdf', 'defaultU2@d.com', '$2a$12$wDZpV6IHLfs/YlGrDUYhteihIyKT.Bm0ml8TkX/Ysv3i4D/51J7be', '00000000');
INSERT INTO usuarios (id_categoria, nombre_usuario, apellido_usuario, fecha_nacimiento_usuario, genero_usuario, numero_tel_usuario, foto_cedula_usuario, correo_usuario, password_usuario, numero_documento_usuario) VALUES (5, 'defaultUser3', 'user3', '2000-02-02', 'Otro', '00000000', 'cedula3.pdf', 'defaultU3@d.com', '$2a$12$wDZpV6IHLfs/YlGrDUYhteihIyKT.Bm0ml8TkX/Ysv3i4D/51J7be', '00000000');

/*==============================================================*/
/* Correos >                                                    */
/* defaultU1@d.com                                              */
/* defaultU2@d.com                                              */
/* defaultU3@d.com                                              */
/* defaultS@d.com                                               */
/* admin@a.com                                                  */
/* Todas las contraseñas son >  123                             */
/*==============================================================*/