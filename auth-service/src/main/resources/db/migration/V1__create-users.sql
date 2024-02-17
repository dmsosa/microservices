CREATE TABLE IF NOT EXISTS users (
    "id" TEXT PRIMARY KEY UNIQUE NOT NULL,
    "username" VARCHAR(35) UNIQUE NOT NULL,
    "email" TEXT UNIQUE NOT NULL,
    "password" TEXT NOT NULL,
    "bio" VARCHAR (5000),
    "image" TEXT[],
    "role" TEXT NOT NULL
)