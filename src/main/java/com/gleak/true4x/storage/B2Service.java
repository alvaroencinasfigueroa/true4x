package com.gleak.true4x.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.UUID;

@Service
public class B2Service {

    @Value("${backblaze.key-id}")
    private String keyId;

    @Value("${backblaze.app-key}")
    private String appKey;

    @Value("${backblaze.bucket}")
    private String bucket;

    @Value("${backblaze.endpoint}")
    private String endpoint;

    private S3Client getClient() {
        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(keyId, appKey)))
                .region(Region.US_EAST_1)
                .forcePathStyle(true)
                .build();
    }

    private S3Presigner getPresigner() {
        return S3Presigner.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(keyId, appKey)))
                .region(Region.US_EAST_1)
                .build();
    }

    public String subirArchivo(MultipartFile archivo, String carpeta) throws IOException {
        String nombreArchivo = carpeta + "/" + UUID.randomUUID() + "_" + archivo.getOriginalFilename();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(nombreArchivo)
                .contentType(archivo.getContentType())
                .build();

        getClient().putObject(request, RequestBody.fromBytes(archivo.getBytes()));

        return nombreArchivo;
    }

    public String obtenerUrlFirmada(String nombreArchivo) {
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofHours(1))
                .getObjectRequest(GetObjectRequest.builder()
                        .bucket(bucket)
                        .key(nombreArchivo)
                        .build())
                .build();

        return getPresigner().presignGetObject(presignRequest).url().toString();
    }

    public void eliminarArchivo(String nombreArchivo) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(nombreArchivo)
                .build();

        getClient().deleteObject(request);
    }
}