package com.tasktrace.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {

    UUID save(MultipartFile video);

    FileServiceImpl.ChunkWithMetadata fetchFile(UUID uuid);
}
