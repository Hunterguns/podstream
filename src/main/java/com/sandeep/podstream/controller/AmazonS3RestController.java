package com.sandeep.podstream.controller;

import com.amazonaws.services.s3.model.ListBucketsPaginatedResult;
import com.sandeep.podstream.service.AwsS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/s3")
public class AmazonS3RestController {

    @Autowired
    AwsS3Service awsS3Service;

//    @PostMapping(value = "/upload")
//    public String uploadFile(@RequestBody MultipartFile multipartFile) throws IOException {
//        awsS3Service.uploadFile(multipartFile.getName(), multipartFile.getSize(), );
//    }

    @GetMapping("/listBuckets")
    public ListBucketsPaginatedResult listBucketsPaginatedResult(){
        return awsS3Service.listBuckets();
    }
}

