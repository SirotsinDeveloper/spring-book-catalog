CREATE TABLE books
(
    id BIGSERIAL PRIMARY KEY UNIQUE,
    author_name  VARCHAR(255),
    title        VARCHAR(255),
    published_year INTEGER
);