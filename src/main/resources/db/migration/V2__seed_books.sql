-- =====================================================================
-- V2__seed_books.sql
-- Libros de ejemplo para que el catálogo no aparezca vacío.
-- 3 libros por categoría. Los ISBN son valores de ejemplo.
-- La categoría se obtiene por su slug
-- =====================================================================

INSERT INTO books (title, author, isbn, description, publication_year, pages, price, cover_url, category_id)
VALUES
    -- Programación
    ('Clean Code', 'Robert C. Martin', '978-0-13-235088-4',
     'Principios y prácticas para escribir código legible, mantenible y fácil de cambiar.',
     2008, 464, 749.00, NULL,
     (SELECT id FROM categories WHERE slug = 'programacion')),

    ('The Pragmatic Programmer', 'David Thomas, Andrew Hunt', '978-0-13-595705-9',
     'Consejos prácticos sobre la carrera y el oficio del desarrollo de software.',
     2019, 352, 689.00, NULL,
     (SELECT id FROM categories WHERE slug = 'programacion')),

    ('Effective Java', 'Joshua Bloch', '978-0-13-468599-1',
     'Buenas prácticas del lenguaje Java explicadas en recomendaciones concretas.',
     2018, 416, 799.00, NULL,
     (SELECT id FROM categories WHERE slug = 'programacion')),

    -- Ciencia ficción
    ('Dune', 'Frank Herbert', '978-0-44-117271-9',
     'En un planeta desértico, la lucha por un recurso escaso decide el destino de un imperio.',
     1965, 688, 399.00, NULL,
     (SELECT id FROM categories WHERE slug = 'ciencia-ficcion')),

    ('Fundación', 'Isaac Asimov', '978-0-55-338257-3',
     'Un matemático predice la caída de un imperio galáctico y planea acortar la era oscura.',
     1951, 296, 329.00, NULL,
     (SELECT id FROM categories WHERE slug = 'ciencia-ficcion')),

    ('Neuromante', 'William Gibson', '978-0-44-156959-5',
     'Un hacker caído en desgracia acepta un último trabajo en un mundo dominado por corporaciones.',
     1984, 271, 349.00, NULL,
     (SELECT id FROM categories WHERE slug = 'ciencia-ficcion')),

    -- Historia
    ('Sapiens', 'Yuval Noah Harari', '978-0-06-231609-7',
     'Recorrido por la historia de la humanidad desde los primeros homínidos hasta hoy.',
     2011, 464, 459.00, NULL,
     (SELECT id FROM categories WHERE slug = 'historia')),

    ('Armas, gérmenes y acero', 'Jared Diamond', '978-0-39-331755-8',
     'Explica por qué algunas sociedades dominaron a otras a partir de la geografía y el medio.',
     1997, 528, 489.00, NULL,
     (SELECT id FROM categories WHERE slug = 'historia')),

    ('La visión de los vencidos', 'Miguel León-Portilla', '978-6-07-020114-1',
     'La conquista de México narrada a partir de testimonios indígenas.',
     1959, 224, 199.00, NULL,
     (SELECT id FROM categories WHERE slug = 'historia')),

    -- Desarrollo personal
    ('Hábitos atómicos', 'James Clear', '978-0-73-521129-2',
     'Cómo pequeños cambios constantes generan grandes resultados a largo plazo.',
     2018, 320, 379.00, NULL,
     (SELECT id FROM categories WHERE slug = 'desarrollo-personal')),

    ('Mindset', 'Carol S. Dweck', '978-0-34-547232-8',
     'La diferencia entre una mentalidad fija y una mentalidad de crecimiento.',
     2006, 320, 349.00, NULL,
     (SELECT id FROM categories WHERE slug = 'desarrollo-personal')),

    ('Deep Work', 'Cal Newport', '978-1-45-555509-1',
     'Estrategias para concentrarse sin distracciones en tareas cognitivamente exigentes.',
     2016, 304, 369.00, NULL,
     (SELECT id FROM categories WHERE slug = 'desarrollo-personal'));
