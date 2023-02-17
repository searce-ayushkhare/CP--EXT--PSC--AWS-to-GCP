package org.example;

import com.google.cloud.storage.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        createBucketWithStorageClassAndLocation("gcp-cf-sample", "bkt-sample-1");
    }

    private static void createBucketWithStorageClassAndLocation(String projectId, String bucketName) {
        Storage storage = StorageOptions
                .newBuilder()
                .setProjectId(projectId)
                .build()
                .getService();

        StorageClass storageClass = StorageClass.COLDLINE;
        String location = "ASIA";

        Bucket bucket = storage.create(
                BucketInfo.newBuilder(bucketName)
                        .setStorageClass(storageClass)
                        .setLocation(location)
                        .build()
        );

        System.out.println(
                "Created bucket "
                        + bucket.getName()
                        + " in "
                        + bucket.getLocation()
                        + " with storage class "
                        + bucket.getStorageClass()
                        + "with generated ID as :: "
                        + bucket.getGeneratedId()
        );

    }

    public static void uploadObjectFromMemory(
            String projectId, String bucketName, String objectName, String contents) throws IOException {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID of your GCS bucket
        // String bucketName = "your-unique-bucket-name";

        // The ID of your GCS object
        // String objectName = "your-object-name";

        // The string of contents you wish to upload
        // String contents = "Hello world!";

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        byte[] content = contents.getBytes(StandardCharsets.UTF_8);
        storage.createFrom(blobInfo, new ByteArrayInputStream(content));

        System.out.println(
                "Object "
                        + objectName
                        + " uploaded to bucket "
                        + bucketName
                        + " with contents "
                        + contents);
    }

}