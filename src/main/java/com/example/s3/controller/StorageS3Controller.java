package com.example.s3.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.s3.service.impl.StorageS3Service;

@RestController
@RequestMapping("/pruthvi/s3")
public class StorageS3Controller {

	@Autowired
	private StorageS3Service s3Service;

	@PostMapping(value = "/upload")
	public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) throws IOException {
		return new ResponseEntity<>(s3Service.uploadFile(file), HttpStatus.OK);
	}

	@GetMapping("/download/{fileName}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
		byte[] data = s3Service.downloadFile(fileName);
		ByteArrayResource arrayResource = new ByteArrayResource(data);
		return ResponseEntity.ok().contentLength(data.length).header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment;filename=\"" + fileName + "\"").body(arrayResource);

	}

	@DeleteMapping("/delete/{fileName}")
	public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
		return new ResponseEntity<>(s3Service.deleteFileFromS3(fileName), HttpStatus.OK);
	}

}
