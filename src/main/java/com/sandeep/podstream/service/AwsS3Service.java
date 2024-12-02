package com.sandeep.podstream.service;

import com.amazonaws.services.s3.model.ListBucketsPaginatedResult;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.IOException;
import java.io.InputStream;

public interface AwsS3Service {
    ListBucketsPaginatedResult listBuckets();

    void uploadFile(String keyName, long contentLength, String contentType, InputStream inputStream) throws IOException;

    S3ObjectInputStream downloadFile(String objectKey);
}
