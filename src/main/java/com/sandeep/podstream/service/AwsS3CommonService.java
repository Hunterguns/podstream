package com.sandeep.podstream.service;

import com.amazonaws.services.s3.model.*;

import java.io.InputStream;

abstract public class AwsS3CommonService {
    protected GetObjectRequest buildGetObjectRequest(String bucketName, String objectKey) {
        return new GetObjectRequest(bucketName, objectKey);
    }

    protected PutObjectRequest buildPutObjectRequest(String bucketName, String objectKey, InputStream inputStream, ObjectMetadata objectMetadata) {
        return new PutObjectRequest(bucketName, objectKey, inputStream, objectMetadata);
    }

    protected DeleteObjectRequest buildDeleteObjectRequest(String bucketName, String objectKey) {
        return new DeleteObjectRequest(bucketName, objectKey);
    }

    protected ListBucketsPaginatedRequest buildListBucketsPaginatedRequest() {
        return new ListBucketsPaginatedRequest();
    }
}
