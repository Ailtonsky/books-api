# BookHub Backend

API REST para un catálogo de libros con autenticación y roles.

- Los usuarios con rol **USER** pueden consultar el catálogo, buscar por título, filtrar por categoría, paginar resultados y ver el detalle de cada libro.
- Los usuarios con rol **ADMIN** además pueden crear, editar y eliminar libros.

> Frontend (React + TypeScript): `<enlace al repositorio del frontend>`

---

## Tecnologías

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 17 |
| Framework | Spring Boot 4.1 (Web MVC, Data JPA, Security, Validation) |
| Base de datos | PostgreSQL 18.2 |
| Migraciones | Flyway |
| Autenticación | JWT (stateless) + BCrypt |
| Utilidades | Lombok |
| Contenedores | Docker Compose (PostgreSQL + pgAdmin) |
| Build | Maven (wrapper incluido) |

---

## Requisitos

- **Java 17** o superior (`java -version`)
- **Docker Desktop** con Docker Compose
- No es necesario instalar Maven: el proyecto incluye el wrapper (`mvnw`)

---
## Project Status

Under development.