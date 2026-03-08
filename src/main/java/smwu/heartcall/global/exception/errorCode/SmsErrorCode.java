package smwu.heartcall.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SmsErrorCode implements ErrorCode {
    INVALID_SMS_SETTING(400, "인증코드 전송 중 오류가 발생했습니다."),
    VERIFICATION_CODE_NOT_FOUND(404, "인증번호를 한 번 더 요청해주세요."),
    INVALID_VERIFICATION_CODE(400, "인증번호가 일치하지 않습니다."),
    ;

    private final int statusCode;
    private final String message;
}