package smwu.heartcall.global.util;

import org.springframework.web.multipart.MultipartFile;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.S3ErrorCode;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class S3Util {

    private static final String USER_DIR = "user";
    private static final String EMERGENCY_DIR = "emergency";



    /**
     * 파일 존재 여부 검사
     */
    public static boolean doesFileExist(MultipartFile multipartFile) {
        return !(multipartFile == null || multipartFile.isEmpty());
    }

    /**
     * 파일 확장자 검사
     */
    public static String getValidateAudioExtension(String fileName) {
        List<String> validExtensionList = Arrays.asList("wav", "flac", "mp3");

        int extensionIndex = fileName.lastIndexOf(".");

        String extension = fileName.substring(extensionIndex + 1).toLowerCase();

        if (!validExtensionList.contains(extension)) {
            throw new CustomException(S3ErrorCode.INVALID_EXTENSION);
        }

        return extension;
    }

    /**
     * 비상 호출 디렉토리 구성
     * @param userId 디렉토리에 사용될 유저 고유번호
     * @return 구성된 디렉토리 경로
     */
    public static String createEmergencyDir(Long userId) {
        return EMERGENCY_DIR + "/" + USER_DIR + "/" + userId + "/";
    }

    /**
     * 파일명 랜덤 생성
     */
    public static String createFileName(String extension) {
        return UUID.randomUUID().toString().concat("." + extension);
    }
}
