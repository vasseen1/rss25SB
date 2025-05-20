CREATE TABLE IF NOT EXISTS item (
    id SERIAL PRIMARY KEY,
    guid UUID UNIQUE NOT NULL,
    title VARCHAR(128) NOT NULL,
    published TIMESTAMPTZ,
    updated TIMESTAMPTZ,
    content_type VARCHAR(10) NOT NULL CHECK (content_type IN ('text', 'html')),
    content_src TEXT,
    image_type VARCHAR(50),
    image_href TEXT,
    image_alt VARCHAR(255),
    image_length INTEGER
);

CREATE TABLE IF NOT EXISTS category (
    id SERIAL PRIMARY KEY,
    item_id INTEGER NOT NULL REFERENCES item(id) ON DELETE CASCADE,
    term VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS author (
    id SERIAL PRIMARY KEY,
    item_id INTEGER NOT NULL REFERENCES item(id) ON DELETE CASCADE,
    name VARCHAR(64) NOT NULL,
    email VARCHAR(255),
    uri TEXT
);
