package smwu.heartcall.global.exception;

import smwu.heartcall.global.exception.errorCode.SecurityErrorCode;

public class CustomSecurityException extends CustomException {
    public CustomSecurityException(SecurityErrorCode errorCode) {
        super(errorCode);
    }
}
