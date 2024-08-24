package com.tasktrace.service;

import com.tasktrace.model.FileMetadataEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {

    UUID save(MultipartFile video);

    ChunkWithMetadata fetchFile(UUID uuid);

    record ChunkWithMetadata(FileMetadataEntity metadata, byte[] chunk) {}
}
