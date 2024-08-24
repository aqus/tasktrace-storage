package com.tasktrace.repository;

import com.tasktrace.model.FileMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadataEntity, String> {
}
