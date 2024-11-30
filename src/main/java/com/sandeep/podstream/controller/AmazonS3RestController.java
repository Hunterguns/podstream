package com.sandeep.podstream.controller;

import com.amazonaws.services.s3.model.ListBucketsPaginatedResult;
import com.sandeep.podstream.service.AwsS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/s3")
public class AmazonS3RestController {

    @Autowired
    AwsS3Service awsS3Service;

    @GetMapping("/listBuckets")
    public ListBucketsPaginatedResult listBucketsPaginatedResult() {
        return awsS3Service.listBuckets();
    }
}