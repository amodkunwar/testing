package com.example.s3.service.impl;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface StorageS3Service {

	String uploadFile(MultipartFile file) throws IOException;

	byte[] downloadFile(String fileName);

	String deleteFileFromS3(String fileName);

}
