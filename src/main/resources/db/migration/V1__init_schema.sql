-- =====================================================================
-- V1__init_schema.sql
-- ---------------------------------------------------------------------
-- roles: catálogo de roles del sistema (ADMIN, USER)
-- ---------------------------------------------------------------------
CREATE TABLE roles (
    id          SMALLSERIAL  PRIMARY KEY,
    name        VARCHAR(20)  NOT NULL,
    CONSTRAINT uq_roles_name UNIQUE (name),
    CONSTRAINT ck_roles_name CHECK (name IN ('ADMIN', 'USER'))
);

-- ---------------------------------------------------------------------
-- users: credenciales de acceso (en la contraseña ocupare hash BCrypt)
-- ---------------------------------------------------------------------
CREATE TABLE users (
    id             BIGSERIAL     PRIMARY KEY,
    username       VARCHAR(50)   NOT NULL,
    email          VARCHAR(120)  NOT NULL,
    password_hash  VARCHAR(100)  NOT NULL,
    enabled        BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at     TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_users_username UNIQUE (username),
    CONSTRAINT uq_users_email    UNIQUE (email)
);

-- ---------------------------------------------------------------------
-- user_roles
-- ---------------------------------------------------------------------
CREATE TABLE user_roles (
    user_id  BIGINT    NOT NULL,
    role_id  SMALLINT  NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id)
        REFERENCES roles (id) ON DELETE RESTRICT
);

-- ---------------------------------------------------------------------
-- categories: clasificación de libros
-- ---------------------------------------------------------------------
CREATE TABLE categories (
    id           BIGSERIAL     PRIMARY KEY,
    name         VARCHAR(60)   NOT NULL,
    slug         VARCHAR(60)   NOT NULL,
    description  VARCHAR(255),
    CONSTRAINT uq_categories_name UNIQUE (name),
    CONSTRAINT uq_categories_slug UNIQUE (slug)
);

-- ---------------------------------------------------------------------
-- books: catálogo de libros
-- ---------------------------------------------------------------------
CREATE TABLE books (
    id                BIGSERIAL      PRIMARY KEY,
    title             VARCHAR(200)   NOT NULL,
    author            VARCHAR(150)   NOT NULL,
    isbn              VARCHAR(17)    NOT NULL,
    description       TEXT,
    publication_year  SMALLINT,
    pages             INTEGER,
    price             NUMERIC(10, 2) NOT NULL,
    cover_url         VARCHAR(500),
    category_id       BIGINT         NOT NULL,
    created_at        TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_books_isbn  UNIQUE (isbn),
    CONSTRAINT ck_books_price CHECK (price >= 0),
    CONSTRAINT ck_books_pages CHECK (pages IS NULL OR pages > 0),
    CONSTRAINT ck_books_year  CHECK (publication_year IS NULL
                                     OR publication_year BETWEEN 1450 AND 2100),
    CONSTRAINT fk_books_category FOREIGN KEY (category_id)
        REFERENCES categories (id) ON DELETE RESTRICT
);

-- Índices para los filtros del catálogo
CREATE INDEX idx_books_category_id ON books (category_id);
CREATE INDEX idx_books_title_lower ON books (LOWER(title));

-- ---------------------------------------------------------------------
-- Datos base
-- ---------------------------------------------------------------------
INSERT INTO roles (name) VALUES ('ADMIN'), ('USER');

INSERT INTO categories (name, slug, description) VALUES
    ('Programación',        'programacion',        'Lenguajes, arquitectura y buenas prácticas'),
    ('Ciencia ficción',     'ciencia-ficcion',     'Futuros posibles, espacio y tecnología'),
    ('Historia',            'historia',            'Acontecimientos y civilizaciones'),
    ('Desarrollo personal', 'desarrollo-personal', 'Hábitos, productividad y crecimiento');
