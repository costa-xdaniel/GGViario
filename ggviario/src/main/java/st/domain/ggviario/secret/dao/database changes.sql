PRAGMA foreign_keys = 0;

CREATE TABLE sqlitestudio_temp_table AS SELECT *
                                          FROM T_OBJECTTYPE;

DROP TABLE T_OBJECTTYPE;

CREATE TABLE T_OBJECTTYPE (
    tobj_id    INTEGER PRIMARY KEY,
    tobj_desc  TEXT,
    tobj_state INTEGER NOT NULL
                       DEFAULT (1)
);

INSERT INTO T_OBJECTTYPE (
                             tobj_id,
                             tobj_desc,
                             tobj_state
                         )
                         SELECT tobj_id,
                                tobj_desc,
                                tobj_state
                           FROM sqlitestudio_temp_table;

DROP TABLE sqlitestudio_temp_table;

PRAGMA foreign_keys = 1;

--
-- File generated with SQLiteStudio v3.1.1 on Sat Feb 4 10:52:33 2017
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: T_OBJECTTYPE
CREATE TABLE T_OBJECTTYPE (tobj_id INTEGER PRIMARY KEY, tobj_desc TEXT, tobj_state INTEGER NOT NULL DEFAULT (1));
INSERT INTO T_OBJECTTYPE (tobj_id, tobj_desc, tobj_state) VALUES (1, 'Tipo documento', 1);
INSERT INTO T_OBJECTTYPE (tobj_id, tobj_desc, tobj_state) VALUES (2, 'Residencia', 1);
INSERT INTO T_OBJECTTYPE (tobj_id, tobj_desc, tobj_state) VALUES (3, 'Keys', 1);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;


PRAGMA foreign_keys = 0;

CREATE TABLE sqlitestudio_temp_table AS SELECT *
                                          FROM T_OBJECT;

DROP TABLE T_OBJECT;

CREATE TABLE T_OBJECT (
    obj_id      INTEGER PRIMARY KEY,
    obj_tobj_id INTEGER NOT NULL
                        CONSTRAINT fk_object_to_typeobject REFERENCES T_OBJECTTYPE (tobj_id),
    obj_obj_id  INTEGER DEFAULT NULL
                        CONSTRAINT fk_object_to_object REFERENCES T_OBJECT (obj_id),
    obj_user_id INTEGER NOT NULL
                        CONSTRAINT fk_object_to_user REFERENCES T_USER (user_id),
    obj_desc    TEXT    NOT NULL,
    obj_state   INTEGER DEFAULT (1)
                        NOT NULL,
    UNIQUE (
        obj_tobj_id,
        obj_desc
    )
);

INSERT INTO T_OBJECT (
                         obj_id,
                         obj_tobj_id,
                         obj_obj_id,
                         obj_user_id,
                         obj_desc,
                         obj_state
                     )
                     SELECT obj_id,
                            obj_tobj_id,
                            obj_obj_id,
                            obj_user_id,
                            obj_desc,
                            obj_state
                       FROM sqlitestudio_temp_table;

DROP TABLE sqlitestudio_temp_table;

PRAGMA foreign_keys = 1;

CREATE TABLE T_GENDER (
    gender_id   INTEGER PRIMARY KEY
                        NOT NULL,
    gender_desc VARCHAR NOT NULL
);

--
-- File generated with SQLiteStudio v3.1.1 on Sat Feb 4 10:48:06 2017
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: T_GENDER
CREATE TABLE T_GENDER (gender_id INTEGER PRIMARY KEY NOT NULL, gender_desc VARCHAR NOT NULL);
INSERT INTO T_GENDER (gender_id, gender_desc) VALUES (1, 'Feminino');
INSERT INTO T_GENDER (gender_id, gender_desc) VALUES (2, 'Masculino');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;


PRAGMA foreign_keys = 0;

CREATE TABLE sqlitestudio_temp_table AS SELECT *
                                          FROM T_CLIENT;

DROP TABLE T_CLIENT;

CREATE TABLE T_CLIENT (
    cli_id               INTEGER,
    cli_user_id          INTEGER      NOT NULL
                                      DEFAULT (1)
                                      CONSTRAINT fk_client_to_user REFERENCES T_USER (user_id),
    cli_obj_residence    INTEGER      NOT NULL
                                      CONSTRAINT fk_client_to_objectresidence REFERENCES T_OBJECT,
    cli_obj_typedocument INTEGER      CONSTRAINT fk_client_to_objecttypedocument REFERENCES T_OBJECT,
    cli_gender_id        INTEGER      CONSTRAINT fk_client_to_gender NOT NULL
                                      CONSTRAINT fk_client_to_gender REFERENCES T_GENDER (gender_id),
    cli_name             VARCHAR (32) NOT NULL,
    cli_surname          VARCHAR (64),
    cli_contact          VARCHAR (16),
    cli_mail             VARCHAR (48),
    cli_document         VARCHAR (32)
);

INSERT INTO T_CLIENT (
                         cli_id,
                         cli_user_id,
                         cli_obj_residence,
                         cli_obj_typedocument,
                         cli_name,
                         cli_surname,
                         cli_contact,
                         cli_mail,
                         cli_document
                     )
                     SELECT cli_id,
                            cli_user_id,
                            cli_obj_residence,
                            cli_obj_typedocument,
                            cli_name,
                            cli_surname,
                            cli_contact,
                            cli_mail,
                            cli_document
                       FROM sqlitestudio_temp_table;

DROP TABLE sqlitestudio_temp_table;

PRAGMA foreign_keys = 1;


create view VER_OBJECTS  as
  select *
    from T_OBJECT
    order by obj_desc asc


PRAGMA foreign_keys = 0;

CREATE TABLE sqlitestudio_temp_table AS SELECT *
                                          FROM T_CLIENT;

DROP TABLE T_CLIENT;

CREATE TABLE T_CLIENT (
    cli_id               INTEGER      PRIMARY KEY
                                      NOT NULL,
    cli_user_id          INTEGER      NOT NULL
                                      DEFAULT (1)
                                      CONSTRAINT fk_client_to_user REFERENCES T_USER (user_id),
    cli_obj_residence    INTEGER      NOT NULL
                                      CONSTRAINT fk_client_to_objectresidence REFERENCES T_OBJECT,
    cli_obj_typedocument INTEGER      CONSTRAINT fk_client_to_objecttypedocument REFERENCES T_OBJECT,
    cli_gender_id        INTEGER      CONSTRAINT fk_client_to_gender NOT NULL
                                      CONSTRAINT fk_client_to_gender REFERENCES T_GENDER (gender_id),
    cli_name             VARCHAR (32) NOT NULL,
    cli_surname          VARCHAR (64),
    cli_contact          VARCHAR (16),
    cli_mail             VARCHAR (48),
    cli_document         VARCHAR (32)
);

INSERT INTO T_CLIENT (
                         cli_id,
                         cli_user_id,
                         cli_obj_residence,
                         cli_obj_typedocument,
                         cli_gender_id,
                         cli_name,
                         cli_surname,
                         cli_contact,
                         cli_mail,
                         cli_document
                     )
                     SELECT cli_id,
                            cli_user_id,
                            cli_obj_residence,
                            cli_obj_typedocument,
                            cli_gender_id,
                            cli_name,
                            cli_surname,
                            cli_contact,
                            cli_mail,
                            cli_document
                       FROM sqlitestudio_temp_table;

DROP TABLE sqlitestudio_temp_table;

PRAGMA foreign_keys = 1;










