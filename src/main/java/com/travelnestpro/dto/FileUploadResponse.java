package com.travelnestpro.dto;

public record FileUploadResponse(
        String fileName,
        String fileUrl,
        long size
) {
}
