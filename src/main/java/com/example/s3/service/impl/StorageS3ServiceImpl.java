package com.example.s3.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StorageS3ServiceImpl implements StorageS3Service {

	@Value("${application.bucket.name}")
	private String bucketName;

	@Autowired
	private AmazonS3 amazonS3;

	@Override
	public String uploadFile(MultipartFile file) throws IOException {
		String fileName = System.currentTimeMillis() + " " + file.getOriginalFilename();
		File fileObj = convertMultipartFileToFile(file);
		amazonS3.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
		boolean delete = fileObj.delete();
		if (delete) {
			log.info("File uploaded and file has been deleted successfully.");
		}
		return "File uploaded : " + fileName;
	}

	@Override
	public byte[] downloadFile(String fileName) {
		S3Object s3Object = amazonS3.getObject(bucketName, fileName);
		S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
		try {
			return IOUtils.toByteArray(s3ObjectInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

	@Override
	public String deleteFileFromS3(String fileName) {
		amazonS3.deleteObject(bucketName, fileName);
		return "File deleted " + fileName;
	}

	private File convertMultipartFileToFile(MultipartFile file) throws IOException {
		File convertedFile = new File(file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
			fos.write(file.getBytes());
		} catch (IOException e) {
			log.error("Error converting multipartFile to file", e);
		}
		return convertedFile;
	}

}
