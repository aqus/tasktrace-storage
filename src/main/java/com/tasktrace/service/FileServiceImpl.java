package com.tasktrace.service;

import com.tasktrace.exception.EntityNotFoundException;
import com.tasktrace.exception.StorageException;
import com.tasktrace.model.FileMetadataEntity;
import com.tasktrace.repository.FileMetadataRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class FileServiceImpl implements FileService {

    private static Logger LOG = Logger.getLogger(FileServiceImpl.class.getName());

    private final FileMetadataRepository fileMetadataRepository;

    private final StorageServiceProvider storageServiceProvider;

    public FileServiceImpl(FileMetadataRepository fileMetadataRepository,
                           StorageServiceProvider storageServiceProvider) {
        this.fileMetadataRepository = fileMetadataRepository;
        this.storageServiceProvider = storageServiceProvider;
    }

    @Override
    public UUID save(MultipartFile file) {
        try {
            UUID fileUuid = UUID.randomUUID();
            FileMetadataEntity metadata = new FileMetadataEntity(fileUuid.toString(), file.getSize(),
                    file.getContentType());
            fileMetadataRepository.save(metadata);
            storageServiceProvider.save(file, fileUuid);
            return fileUuid;
        } catch (Exception ex) {
            LOG.severe("Exception occurred when trying to save the file " + ex);
            throw new StorageException(ex);
        }
    }

    @Override
    public ChunkWithMetadata fetchFile(UUID uuid) {
        FileMetadataEntity fileMetadata = fileMetadataRepository.findById(uuid.toString())
                .orElseThrow(() -> new EntityNotFoundException("File with id %s is not found".formatted(uuid)));
        return new ChunkWithMetadata(fileMetadata, readFile(uuid, fileMetadata.getSize()));
    }

    private byte[] readFile(UUID uuid, long fileSize) {
        try(InputStream inputStream = storageServiceProvider.getFile(uuid, fileSize)) {
            return inputStream.readAllBytes();
        } catch (Exception exception) {
            LOG.severe("Exception occurred when trying to read file with ID = " + uuid);
            throw new StorageException(exception);
        }
    }

    public record ChunkWithMetadata(FileMetadataEntity metadata, byte[] chunk) {}
}
