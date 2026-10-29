# BookHub Backend

API REST para un catálogo de libros con autenticación y roles.

- Los usuarios con rol **USER** pueden consultar el catálogo, buscar por título, filtrar por categoría, paginar resultados y ver el detalle de cada libro.
- Los usuarios con rol **ADMIN** además pueden crear, editar y eliminar libros.

> Frontend (React + TypeScript): `https://github.com/Ailtonsky/books-frontend`

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
## Puesta en marcha

### 1. Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd books-api
```

### 2. Configurar las variables de entorno

```bash
# Linux / Mac / Git Bash
cp .env.example .env

# PowerShell
Copy-Item .env.example .env
```

Abre `.env` y define al menos `POSTGRES_PASSWORD`, `JWT_SECRET` y las contraseñas de los usuarios iniciales.

### 3. Levantar la base de datos

```bash
docker compose up -d
docker compose ps   # bookhub-db debe aparecer como "healthy"
```

### 4. Ejecutar la aplicación

```bash
# Linux / Mac / Git Bash
./mvnw spring-boot:run

# PowerShell / CMD
.\mvnw.cmd spring-boot:run
```

La API queda disponible en `http://localhost:8080/api/v1`.

Al arrancar:
- **Flyway** crea las tablas y carga los roles, las categorías y 12 libros de ejemplo.
- **DataInitializer** crea los usuarios iniciales (ADMIN y USER) con las credenciales del `.env`, guardando la contraseña con hash BCrypt.

---

## Variables de entorno

| Variable | Descripción | Ejemplo |
|---|---|---|
| `POSTGRES_DB` | Nombre de la base de datos | `bookhub` |
| `POSTGRES_USER` | Usuario de PostgreSQL | `bookhub_user` |
| `POSTGRES_PASSWORD` | Contraseña de PostgreSQL (obligatoria) | `change_me` |
| `JWT_SECRET` | Clave para firmar los tokens (mínimo 32 caracteres) | `<cadena-aleatoria>` |
| `JWT_EXPIRATION_MINUTES` | Vigencia del token en minutos | `60` |
| `ADMIN_USERNAME` / `ADMIN_EMAIL` / `ADMIN_PASSWORD` | Usuario administrador inicial | `admin` / `admin@books.local` / `change_me` |
| `USER_USERNAME` / `USER_EMAIL` / `USER_PASSWORD` | Usuario normal inicial | `user` / `user@books.local` / `change_me` |
| `CORS_ALLOWED_ORIGINS` | Origen permitido del frontend | `http://localhost:5173` |

El archivo `.env` está en `.gitignore` y **nunca** se sube al repositorio. Docker Compose lo lee automáticamente, y Spring Boot lo carga con `spring.config.import=optional:file:.env[.properties]`. Ninguna credencial está escrita en el código.

> PostgreSQL solo aplica `POSTGRES_DB`, `POSTGRES_USER` y `POSTGRES_PASSWORD` la primera vez que crea el volumen. Si los cambias después, recrea la base con `docker compose down -v` y `docker compose up -d`.

---

## Conexión desde un cliente de base de datos (opcional)

Para revisar las tablas con pgAdmin, DBeaver u otro cliente:

| Campo | Valor |
|---|---|
| Host | `localhost` |
| Puerto | `5433` |
| Base de datos / Usuario / Contraseña | Los valores de `POSTGRES_*` en `.env` |

---

## Endpoints

URL base: `http://localhost:8080/api/v1`

| Método | Endpoint | Acceso | Descripción |
|---|---|---|---|
| `POST` | `/auth/login` | Público | Inicia sesión y devuelve un JWT |
| `GET` | `/books` | Público | Lista paginada de libros, con búsqueda y filtro |
| `GET` | `/books/{id}` | Público | Detalle de un libro |
| `GET` | `/categories` | Público | Lista de categorías ordenadas por nombre |
| `POST` | `/admin/books` | ADMIN | Crea un libro |
| `PUT` | `/admin/books/{id}` | ADMIN | Actualiza un libro |
| `DELETE` | `/admin/books/{id}` | ADMIN | Elimina un libro |

### Parámetros de `GET /books`

Todos son opcionales y se pueden combinar.

| Parámetro | Tipo | Descripción | Ejemplo |
|---|---|---|---|
| `search` | string | Búsqueda por título, sin distinguir mayúsculas | `java` |
| `categoryId` | number | Filtra por categoría | `1` |
| `page` | number | Página, inicia en 0 | `0` |
| `size` | number | Elementos por página (por defecto 8, máximo 50) | `8` |
| `sort` | string | Campo y dirección de orden (por defecto `title`) | `price,desc` |

```
GET /api/v1/books?search=java&categoryId=1&page=0&size=8
```

La respuesta incluye `hasNext` para implementar el botón "Cargar más".

### Autenticación

```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "<ADMIN_PASSWORD del .env>"
}
```

La respuesta incluye `accessToken`, su vigencia en segundos y los datos del usuario con sus roles. Los endpoints de `/admin/**` requieren el encabezado:

```
Authorization: Bearer <accessToken>
```

### Ejemplo: crear un libro

```http
POST /api/v1/admin/books
Authorization: Bearer <accessToken>
Content-Type: application/json

{
  "title": "Refactoring",
  "author": "Martin Fowler",
  "isbn": "978-0-13-475759-9",
  "description": "Técnicas para mejorar el diseño de código existente.",
  "publicationYear": 2018,
  "pages": 448,
  "price": 699.00,
  "coverUrl": null,
  "categoryId": 1
}
```

`PUT /admin/books/{id}` recibe el mismo cuerpo y reemplaza todos los campos.

### Códigos de respuesta

| Código | Significado |
|---|---|
| `200` / `201` / `204` | Operación exitosa |
| `400` | Datos inválidos (incluye el detalle de cada campo) o parámetro con formato incorrecto |
| `401` | Credenciales incorrectas, sin token o token inválido |
| `403` | Autenticado, pero sin permisos (USER en `/admin/**`) |
| `404` | Libro o categoría no encontrado |
| `409` | El ISBN ya pertenece a otro libro |

Todos los errores tienen el mismo formato:

```json
{
  "status": 404,
  "message": "Libro no encontrado con id: 999",
  "errors": null,
  "timestamp": "2026-10-27T21:40:00Z"
}
```

### Colección de Postman

En `docs/postman/` están la colección con todos los endpoints y casos de prueba, y el environment `Local`. Para usarlos: **Import** en Postman, selecciona el environment y ejecuta primero los logins, que guardan los tokens automáticamente.

---

## Base de datos

Esquema normalizado en 3FN con 5 tablas: `users`, `roles`, `user_roles`, `categories` y `books`.

El diagrama entidad-relación, la descripción de las tablas y las decisiones de diseño están en [`docs/DATABASE.md`](docs/DATABASE.md).

Las migraciones están en `src/main/resources/db/migration/`:

| Versión | Contenido |
|---|---|
| `V1__init_schema.sql` | Tablas, restricciones, índices, roles y categorías |
| `V2__seed_books.sql` | 12 libros de ejemplo |

Hibernate se ejecuta con `ddl-auto=validate`: Flyway es la única fuente de verdad del esquema, y la aplicación no arranca si alguna entidad no coincide con las tablas.

---

## Estructura del proyecto

```
src/main/java/com/turing/books_api
├── config/        Inicialización de usuarios (DataInitializer)
├── controller/    Controladores REST (públicos, autenticación y administración)
├── dto/           Objetos de entrada y salida de la API
├── entity/        Entidades JPA
├── exception/     Excepciones y manejador global de errores
├── repository/    Acceso a datos (Spring Data JPA)
├── security/      Configuración de Spring Security y JWT
└── service/       Interfaces de la lógica de negocio
    └── impl/      Implementaciones
```

---

## Seguridad

- Contraseñas almacenadas con **BCrypt**; nunca en texto plano.
- Autenticación **stateless** con JWT; no se usan sesiones en el servidor.
- Un filtro (middleware) valida el token en cada petición y carga el usuario con sus roles.
- Los permisos se verifican **en el backend**: `/admin/**` exige rol ADMIN, aunque se llame a la API directamente.
- Validación de datos de entrada con Bean Validation.
- La API responde con DTOs: las entidades no se exponen y nunca se devuelve la contraseña ni su hash.
- Los errores no previstos responden un mensaje genérico, sin detalles internos.
- CORS restringido al origen del frontend.
- Credenciales y secretos fuera del código, en variables de entorno.

---

## Comandos útiles

```bash
docker compose down        # detiene el contenedor (conserva los datos)
docker compose down -v     # detiene y borra los datos (la BD se recrea al reiniciar)
docker compose logs -f     # ver logs de PostgreSQL
```
