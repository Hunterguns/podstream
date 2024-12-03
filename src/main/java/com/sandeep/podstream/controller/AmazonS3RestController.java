package com.sandeep.podstream.controller;

import com.amazonaws.services.s3.model.ListBucketsPaginatedResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.sandeep.podstream.service.AwsS3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.sandeep.podstream.utils.ApplicationUtils.generateFileNameWithTimeStamp;

@RestController
@RequestMapping("/s3")
@Slf4j
public class AmazonS3RestController {

    @Autowired
    AwsS3Service awsS3Service;

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        try {
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String key = generateFileNameWithTimeStamp(multipartFile.getOriginalFilename(), timeStamp);
            awsS3Service.uploadFile(key, multipartFile.getSize(), multipartFile.getContentType(), multipartFile.getInputStream());
            return ResponseEntity.status(HttpStatus.OK).body("File upload successful");
        } catch (Exception e) {
            log.error("Exception while uploading file to s3: {}, {}", e.getMessage(), e.getStackTrace());
            return ResponseEntity.internalServerError().body("Something went wrong while uploading the file. Please try again.");
        }
    }

    @GetMapping("/listBuckets")
    public ListBucketsPaginatedResult listBucketsPaginatedResult() {
        return awsS3Service.listBuckets();
    }

    @GetMapping("/download/{fileKey}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("fileKey") String fileKey){
        S3Object s3Object = awsS3Service.downloadFile(fileKey);
        S3ObjectInputStream objectContent = s3Object.getObjectContent();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileKey)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(objectContent));
    }

    @GetMapping("/getUrl/{fileKey}")
    public ResponseEntity<String> getFileUrl(@PathVariable("fileKey") String fileKey){
        return ResponseEntity.ok().body(awsS3Service.getPresignedUrl(fileKey));
    }

    @DeleteMapping("/delete/{fileKey}")
    public ResponseEntity<String> delete(@PathVariable("fileKey") String fileKey){
        awsS3Service.deleteFile(fileKey);
        return ResponseEntity.ok("File deleted successfully");
    }

    @GetMapping("/getMetadata/{fileKey}")
    public ResponseEntity<ObjectMetadata> getObjectMetadata(@PathVariable("fileKey") String fileKey){
        return ResponseEntity.ok(awsS3Service.getObjectMetaData(fileKey));
    }
}