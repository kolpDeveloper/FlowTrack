CREATE TABLE IF NOT EXISTS message (
    id UUID PRIMARY KEY,
    to_email VARCHAR(255),
    subject VARCHAR(255),
    message TEXT
);

CREATE INDEX idx_notification_email ON message(to_email);
