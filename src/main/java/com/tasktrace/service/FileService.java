package com.tasktrace.service;

import com.tasktrace.util.Range;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {

    UUID save(MultipartFile video);

    FileServiceImpl.ChunkWithMetadata fetchChunk(UUID uuid, Range range);

    FileServiceImpl.ChunkWithMetadata fetchFile(UUID uuid);
}
