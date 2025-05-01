package smwu.heartcall.global.exception;

import lombok.Getter;
import smwu.heartcall.global.exception.errorCode.ErrorCode;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
