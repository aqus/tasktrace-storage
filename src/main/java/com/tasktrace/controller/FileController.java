package com.tasktrace.controller;

import com.tasktrace.service.FileService;
import com.tasktrace.service.FileServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@RestController
public class FileController {

    private final FileService fileService;

    public final Integer defaultChunkSize;

    public FileController(FileService fileService,
                          @Value("${app.default-chunk-size}") Integer defaultChunkSize) {
        this.fileService = fileService;
        this.defaultChunkSize = defaultChunkSize;
    }

    @PostMapping("api/v1/file")
    public ResponseEntity<UUID> save(@RequestParam("file") MultipartFile file) {
        UUID fileUuid = fileService.save(file);
        return ResponseEntity.ok(fileUuid);
    }

    @GetMapping("file/{uuid}")
    public ResponseEntity<byte[]> readFile(@PathVariable UUID uuid) {
        FileServiceImpl.ChunkWithMetadata chunkWithMetadata = fileService.fetchFile(uuid);
        return ResponseEntity.status(HttpStatus.OK)
                .header(CONTENT_TYPE, chunkWithMetadata.metadata().getHttpContentType())
                .header(CONTENT_LENGTH, String.valueOf(chunkWithMetadata.metadata().getSize()))
                .body(chunkWithMetadata.chunk());
    }
}
