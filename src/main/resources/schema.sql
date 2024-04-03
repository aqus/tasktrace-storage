CREATE TABLE IF NOT EXISTS files_metadata (
    file_id VARCHAR(100) NOT NULL PRIMARY KEY,
    file_size BIGINT NOT NULL,
    http_content_type VARCHAR(100) NOT NULL
);
