CREATE DATABASE kaldi;

-- ============================================
-- DROP TABLES (RESET SCHEMA)
-- ============================================
DROP TABLE IF EXISTS messages CASCADE;
DROP TABLE IF EXISTS conversations CASCADE;
DROP TABLE IF EXISTS rooms CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- ============================================
-- EXTENSIONS
-- ============================================
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ============================================
-- USERS TABLE
-- ============================================
CREATE TABLE users (
    uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    created TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0
);

-- ============================================
-- ROOMS TABLE
-- ============================================
CREATE TABLE rooms (
    uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    description VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL UNIQUE,
    room_type VARCHAR(50) NOT NULL,
    created TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0
);

-- Index to speed up lookups by room_type
CREATE INDEX idx_rooms_room_type ON rooms(room_type);

-- ============================================
-- CONVERSATIONS TABLE
-- ============================================
CREATE TABLE conversations (
    uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    room_id UUID NOT NULL,
    operator UUID,
    customer_username VARCHAR(255) NOT NULL,
    customer_role VARCHAR(50) NOT NULL, -- enum stored as string
    status VARCHAR(50) NOT NULL,
    created TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    CONSTRAINT fk_conversations_room FOREIGN KEY (room_id) REFERENCES rooms(uuid) ON DELETE CASCADE,
    CONSTRAINT fk_conversations_operator FOREIGN KEY (operator) REFERENCES users(uuid) ON DELETE SET NULL
);

-- Indexes for performance
CREATE INDEX idx_conversations_room_id ON conversations(room_id);
CREATE INDEX idx_conversations_operator ON conversations(operator);
CREATE INDEX idx_conversations_status ON conversations(status);
CREATE INDEX idx_conversations_created ON conversations(created DESC);
CREATE INDEX idx_conversations_customer_username ON conversations(customer_username);

-- ============================================
-- MESSAGES TABLE
-- ============================================
CREATE TABLE messages (
    uuid UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    content TEXT NOT NULL,
    conversation_id UUID NOT NULL,
    user_role VARCHAR(50) NOT NULL,
    created TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    CONSTRAINT fk_messages_conversation FOREIGN KEY (conversation_id) REFERENCES conversations(uuid) ON DELETE CASCADE
);

-- Indexes for performance
CREATE INDEX idx_messages_conversation_id ON messages(conversation_id);
CREATE INDEX idx_messages_created ON messages(created DESC);

-- ============================================
-- SEED DATA
-- ============================================
INSERT INTO rooms (description, name, room_type) VALUES
('Technical Room', 'TECHNICAL', 'TECHNICAL'),
('Service requests Room', 'SERVICES', 'SERVICES'),
('General Chat Room', 'CONVERSATIONAL', 'CONVERSATIONAL');