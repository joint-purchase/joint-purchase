package com.jointpurchases.domain.product.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.jointpurchases.domain.product.exception.ImageFailToUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.jointpurchases.global.exception.ErrorCode.FAIL_TO_UPLOAD_FILE;
import static com.jointpurchases.global.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@Service
public class S3ImageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    // 이미지 단일업로드
    public String upload(MultipartFile multipartFile) throws IOException{

        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());

        String fileName = generateFileName(multipartFile);
        String fileUrl= "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" +fileName;

        try {
            amazonS3Client.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
            return fileUrl;
        } catch (IOException e) {
           throw new ImageFailToUploadException(INTERNAL_SERVER_ERROR);
        }
    }

    // 이미지 다중업로드
    public  List<String> upload(List<MultipartFile> multipartFiles) {
        return multipartFiles.stream()
                .map(multipartFile -> {
                    try {
                        return upload(multipartFile);
                    } catch (IOException e) {
                        throw new ImageFailToUploadException(FAIL_TO_UPLOAD_FILE);
                    }
                }).toList();
    }


    private String generateFileName(MultipartFile multipartFile) {
        return UUID.randomUUID() + "." + multipartFile.getOriginalFilename();
    }

    public void deleteImage(String fileUrl) {
        amazonS3Client.deleteObject(bucket, getDeleteKey(fileUrl));
    }

    private String getDeleteKey(String fileUrl) {
        int lastIndex = fileUrl.lastIndexOf("/");
        if (lastIndex == -1) {
            throw new ImageFailToUploadException(FAIL_TO_UPLOAD_FILE);
        }
        return fileUrl.substring(lastIndex + 1);
    }

}