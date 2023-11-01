
/*==============================================================*/
/* Crear base de datos con nombre 'db_solever'                  */
/* Ususrio : 'Spring'       Password : 'admin'                  */
/*==============================================================*/

/*==============================================================*/
/* Table: categorias                                            */
/*==============================================================*/
create table categorias (
   id_categoria         SERIAL not null,
   nombre_categoria     VARCHAR(100)         not null,
   constraint PK_CATEGORIAS primary key (id_categoria)
);

/*==============================================================*/
/* Table: categorias_temas                                      */
/*==============================================================*/
create table categorias_temas (
   id_categoria         INT4                 not null,
   id_tema              INT4                 not null,
   constraint PK_CATEGORIAS_TEMAS primary key (id_categoria, id_tema)
);

/*==============================================================*/
/* Table: temas                                                 */
/*==============================================================*/
create table temas (
   id_tema              SERIAL not null,
   id_usuario           INT4                 not null,
   nombre_tema          VARCHAR(200)         not null,
   cupos_tema           NUMERIC              not null,
   hora_tema            TIME                 not null,
   descripcion_tema     TEXT                 not null,
   link_meet_tema       VARCHAR(255)         not null,
   imagen_tema          VARCHAR(255)         not null,
   constraint PK_TEMAS primary key (id_tema)
);

/*==============================================================*/
/* Table: usuarios                                              */
/*==============================================================*/
create table usuarios (
   id_usuario           SERIAL not null,
   id_categoria         INT4                 not null,
   nombre_usuario       VARCHAR(255)         not null,
   apellido_usuario     VARCHAR(255)         not null,
   fecha_nacimiento_usuario DATE                 not null,
   genero_usuario       VARCHAR(50)          not null,
   numero_tel_usuario   VARCHAR(20)          not null,
   numero_documento_usuario   VARCHAR(50)          not null,
   foto_cedula_usuario  VARCHAR(300)         not null,
   correo_usuario       VARCHAR(255)         not null,
   password_usuario     VARCHAR(300)         not null,
   constraint PK_USUARIOS primary key (id_usuario)
);

alter table categorias_temas
   add constraint FK_CATEGORI_REFERENCE_CATEGORI foreign key (id_categoria)
      references categorias (id_categoria)
      on delete restrict on update cascade;

alter table categorias_temas
   add constraint FK_CATEGORI_REFERENCE_TEMAS foreign key (id_tema)
      references temas (id_tema)
      on delete restrict on update cascade;

alter table temas
   add constraint FK_TEMAS_REFERENCE_USUARIOS foreign key (id_usuario)
      references usuarios (id_usuario)
      on delete restrict on update cascade;

alter table usuarios
   add constraint FK_USUARIOS_REFERENCE_CATEGORI foreign key (id_categoria)
      references categorias (id_categoria)
      on delete restrict on update cascade;

