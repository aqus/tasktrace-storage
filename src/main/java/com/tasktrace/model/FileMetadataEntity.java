package com.tasktrace.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "files_metadata")
public class FileMetadataEntity {

    @Id
    @Column(name = "file_id", nullable = false)
    private String id;

    @Column(name = "file_size", nullable = false)
    private long size;

    @Column(name = "http_content_type", nullable = false)
    private String httpContentType;

    public FileMetadataEntity(String id, long size, String httpContentType) {
        this.id = id;
        this.size = size;
        this.httpContentType = httpContentType;
    }

    public FileMetadataEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getHttpContentType() {
        return httpContentType;
    }

    public void setHttpContentType(String httpContentType) {
        this.httpContentType = httpContentType;
    }
}
