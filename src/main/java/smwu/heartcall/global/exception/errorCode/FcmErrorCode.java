package smwu.heartcall.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FcmErrorCode implements ErrorCode {
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "회원의 FCM 토큰을 찾을 수 없습니다."),
    TOKEN_ALREADY_EXISTS(HttpStatus.BAD_REQUEST.value(), "회원의 FCM 토큰이 이미 존재합니다."),
    FCM_BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "메시지 전송 중 오류가 발생했습니다.")
    ;

    private final int statusCode;
    private final String message;
}
