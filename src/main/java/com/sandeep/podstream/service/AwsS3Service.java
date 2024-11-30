package com.sandeep.podstream.service;

import com.amazonaws.Response;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListBucketsPaginatedResult;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface AwsS3Service {
    ListBucketsPaginatedResult listBuckets();

    void uploadFile(String keyName, long contentLength, String contentType, InputStream inputStream) throws IOException;

    S3ObjectInputStream downloadFile(String objectKey);
}
