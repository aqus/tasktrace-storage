package com.tasktrace.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

public interface StorageServiceProvider {

    void save(MultipartFile file, UUID uuid) throws Exception;

    InputStream getFile(UUID uuid, long length) throws Exception;
}
