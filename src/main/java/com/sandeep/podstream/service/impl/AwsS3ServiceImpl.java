package com.sandeep.podstream.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sandeep.podstream.service.AwsS3CommonService;
import com.sandeep.podstream.service.AwsS3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class AwsS3ServiceImpl extends AwsS3CommonService implements AwsS3Service {

    @Value("aws.s3.bucket-name")
    private String bucketName;
    @Autowired
    AmazonS3 s3Client;

    public void uploadFile(String keyName, long contentLength, String contentType, InputStream inputStream) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(contentLength);
        objectMetadata.setContentType(contentType);
        s3Client.putObject(buildPutObjectRequest(bucketName, keyName, inputStream, objectMetadata));
    }
}
