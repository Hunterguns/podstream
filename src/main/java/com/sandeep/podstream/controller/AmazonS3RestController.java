package com.sandeep.podstream.controller;

import com.amazonaws.services.s3.model.ListBucketsPaginatedResult;
import com.sandeep.podstream.service.AwsS3Service;
import io.netty.util.internal.SystemPropertyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        try{
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String key = generateFileNameWithTimeStamp(multipartFile.getOriginalFilename(), timeStamp);
            awsS3Service.uploadFile(key, multipartFile.getSize(), multipartFile.getContentType(), multipartFile.getInputStream());
            return ResponseEntity.status(HttpStatus.OK).body("File upload successful");
        }catch (Exception e){
            log.error("Exception while uploading file to s3: {}, {}", e.getMessage(), e.getStackTrace());
            return ResponseEntity.internalServerError().body("Something went wrong while uploading the file. Please try again.");
        }
    }

    @GetMapping("/listBuckets")
    public ListBucketsPaginatedResult listBucketsPaginatedResult() {
        return awsS3Service.listBuckets();
    }
}