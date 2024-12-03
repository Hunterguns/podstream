package com.sandeep.podstream.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.*;

import java.io.InputStream;
import java.util.Date;

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

    protected GeneratePresignedUrlRequest generatePresignedUrlRequest(String bucketName, String objectKey){
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
        return new GeneratePresignedUrlRequest(bucketName, objectKey)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
    }

    protected GetObjectMetadataRequest buildGetObjectMetadataRequest(String bucketName, String objectKey){
        return new GetObjectMetadataRequest(bucketName, objectKey);
    }
}
