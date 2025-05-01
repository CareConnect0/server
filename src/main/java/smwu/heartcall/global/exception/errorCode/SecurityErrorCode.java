package smwu.heartcall.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode implements ErrorCode {
    USER_NOT_FOUND(404, "회원을 찾을 수 없습니다."),
    LOGIN_FAILED(400, "로그인에 실패했습니다."),
    WITHDRAWAL_USER(400, "탈퇴한 회원입니다."),
    INVALID_ACCESS_TOKEN(400, "해당 토큰으로 로그인이 불가능합니다."),
    EXPIRED_TOKEN(401, "만료된 토큰입니다.")
    ;

    private final int statusCode;
    private final String message;
}

