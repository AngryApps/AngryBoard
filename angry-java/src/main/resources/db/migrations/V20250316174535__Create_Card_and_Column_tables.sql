CREATE TABLE IF NOT EXISTS columns
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(255)     NOT NULL,
    description TEXT,
    position    DOUBLE PRECISION NOT NULL,
    created_at  TIMESTAMP        NOT NULL,
    updated_at  TIMESTAMP        NOT NULL
);

CREATE TABLE IF NOT EXISTS cards
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(255)     NOT NULL,
    description TEXT,
    position    DOUBLE PRECISION NOT NULL,
    created_at  TIMESTAMP        NOT NULL,
    updated_at  TIMESTAMP        NOT NULL,
    column_id   UUID             NOT NULL,
    FOREIGN KEY (column_id) REFERENCES columns (id)
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_cards_column_id ON cards (column_id);
CREATE INDEX IF NOT EXISTS idx_cards_position ON cards (position);
CREATE INDEX IF NOT EXISTS idx_columns_position ON columns (position);
