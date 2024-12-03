package com.sandeep.podstream.service;

import com.amazonaws.services.s3.model.ListBucketsPaginatedResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

import java.io.IOException;
import java.io.InputStream;

public interface AwsS3Service {
    ListBucketsPaginatedResult listBuckets();

    void uploadFile(String keyName, long contentLength, String contentType, InputStream inputStream) throws IOException;

    S3Object downloadFile(String objectKey);

    String getPresignedUrl(String objectKey);

    void deleteFile(String objectKey);

    ObjectMetadata getObjectMetaData(String objectKey);
}
