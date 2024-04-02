package com.tasktrace.configuration;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

    public static final String TASKTRACE_BUCKET_NAME = "tasktrace";

    private final String minioUrl;

    private final String minioUsername;

    private final String minioPassword;

    public MinioConfiguration(@Value("${minio.url}") String minioUrl,
                              @Value("${minio.username}") String minioUsername,
                              @Value("${minio.password}") String minioPassword) {
        this.minioUrl = minioUrl;
        this.minioUsername = minioUsername;
        this.minioPassword = minioPassword;
    }

    @Bean
    public MinioClient minioClient() throws Exception {
        MinioClient client = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioUsername, minioPassword)
                .build();

        if (!client.bucketExists(BucketExistsArgs.builder().bucket(TASKTRACE_BUCKET_NAME).build())) {
            client.makeBucket(
                    MakeBucketArgs
                            .builder()
                            .bucket(TASKTRACE_BUCKET_NAME)
                            .build()
            );
        }
        return client;
    }
}
