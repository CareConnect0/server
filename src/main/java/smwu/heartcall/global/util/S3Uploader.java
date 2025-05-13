package smwu.heartcall.global.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.S3ErrorCode;

import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadEmergencyAudio(MultipartFile file, Long userId) {
        String audioDir = S3Util.createEmergencyDir(userId); // 파일 저장 경로 생성
        return uploadAudio(file, audioDir);
    }

    private String uploadAudio(MultipartFile file, String audioDir) {
        if(!S3Util.doesFileExist(file)) {
            throw new CustomException(S3ErrorCode.FILE_DOES_NOT_EXIST);
        }

        String extension = S3Util.getValidateAudioExtension(file.getOriginalFilename());
        String uploadFileName = audioDir + S3Util.createFileName(extension);
        return uploadFileToS3(file, uploadFileName);
    }

    /**
     * 실제 S3에 이미지 파일 업로드
     */
    private String uploadFileToS3(MultipartFile file, String uploadFileName) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, uploadFileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            log.error("업로드 중 오류가 발생했습니다.", e);
            throw new CustomException(S3ErrorCode.FILE_STREAM_ERROR);
        }
        return amazonS3Client.getUrl(bucket, uploadFileName).toString();
    }

    /**
     * DB에 저장된 이미지 링크로 S3의 단일 파일 삭제
     */
    public void deleteFileFromS3(String url) {
        if(url == null) {
            return;
        }
        String splitStr = ".com/";
        String fileName = url.substring(url.lastIndexOf(splitStr) + splitStr.length());

        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    /**
     * 특정 디렉토리 내의 모든 파일 삭제
     */
    public void deleteFilesFromS3(String imageDir) {
        ObjectListing objectListing = amazonS3Client.listObjects(bucket, imageDir);

        if (objectListing.getObjectSummaries().isEmpty()) {
            log.info("파일이 존재하지 않습니다.");
            return;
        }

        while(true) {
            for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
                amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, summary.getKey()));
                log.info("삭제 : " + summary.getKey());
            }

            if(objectListing.isTruncated()) {
                objectListing = amazonS3Client.listNextBatchOfObjects(objectListing);
            } else {
                break;
            }
        }
    }
}