package com.tasktrace.service;

import com.tasktrace.configuration.MinioConfiguration;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class MinioStorageServiceProvider implements StorageServiceProvider {

    private final MinioClient minioClient;

    private final Long putObjectPartSize;

    public MinioStorageServiceProvider(MinioClient minioClient,
                                       @Value("${minio.put-object-part-size}") Long putObjectPartSize) {
        this.minioClient = minioClient;
        this.putObjectPartSize = putObjectPartSize;
    }

    @Override
    public void save(MultipartFile file, UUID uuid) throws Exception {
        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .bucket(MinioConfiguration.TASKTRACE_BUCKET_NAME)
                        .object(uuid.toString())
                        .stream(file.getInputStream(), file.getSize(), putObjectPartSize)
                        .build()
        );
    }

    @Override
    public InputStream getStream(UUID uuid, long offset, long length) throws Exception {
        return minioClient.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(MinioConfiguration.TASKTRACE_BUCKET_NAME)
                        .offset(offset)
                        .length(length)
                        .object(uuid.toString())
                        .build());
    }

    @Override
    public InputStream getFile(UUID uuid, long length) throws Exception {
        return minioClient.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(MinioConfiguration.TASKTRACE_BUCKET_NAME)
                        .length(length)
                        .object(uuid.toString())
                        .build());
    }
}
