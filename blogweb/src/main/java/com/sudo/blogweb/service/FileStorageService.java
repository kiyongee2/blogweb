package com.sudo.blogweb.service;

import com.sudo.blogweb.dto.UploadResponse;

public interface FileStorageService {
    UploadResponse store(byte[] bytes, String filename);
}
