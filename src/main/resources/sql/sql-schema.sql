-- Database schema creation script based on Java entity classes

-- Drop tables if they exist to avoid conflicts
DROP TABLE IF EXISTS numeric_data_entries;
DROP TABLE IF EXISTS numeric_data;
DROP TABLE IF EXISTS users1;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS categories;

-- Create categories table
CREATE TABLE categories
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Create roles table
CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

-- Insert default roles
INSERT INTO roles (name)
VALUES ('ROLE_USER');
INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');

-- Create users table
CREATE TABLE users1
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(40)  NOT NULL UNIQUE,
    email      VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Create numeric_data table (schema is specified in entity)
CREATE SCHEMA IF NOT EXISTS numeric_data;

-- Create numeric_data table (note: using public schema as default if not specified)
CREATE TABLE numeric_data.numeric_data
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Create numeric_data_entries table
CREATE TABLE numeric_data_entries
(
    id              BIGSERIAL PRIMARY KEY,
    key             VARCHAR(255),
    value           INTEGER,
    numeric_data_id BIGINT,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (numeric_data_id) REFERENCES numeric_data.numeric_data (id) ON DELETE CASCADE
);

-- Create users_roles junction table for many-to-many relationship
-- Note: This is inferred since User and Role classes would typically have a many-to-many relationship
CREATE TABLE users_roles
(
    user_id BIGINT  NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users1 (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_numeric_data_entries_numeric_data_id ON numeric_data_entries (numeric_data_id);
CREATE INDEX idx_users_roles_user_id ON users_roles (user_id);
CREATE INDEX idx_users_roles_role_id ON users_roles (role_id);
