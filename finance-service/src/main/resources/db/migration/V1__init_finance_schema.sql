CREATE TABLE IF NOT EXISTS numeric_data_entry (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    key VARCHAR(255),
    value DECIMAL(19, 2),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS category (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(300) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    numeric_data_entry_id UUID REFERENCES numeric_data_entry(id) ON DELETE SET NULL
);

CREATE INDEX idx_finance_user_id ON numeric_data_entry(user_id);
