package smwu.heartcall.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode{
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 유저가 존재하지 않습니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST.value(), "중복된 아이디입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다.")
    ;

    private final int statusCode;
    private final String message;
}
