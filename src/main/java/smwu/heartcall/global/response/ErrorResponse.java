package smwu.heartcall.global.response;

import smwu.heartcall.global.exception.errorCode.ErrorCode;

public class ErrorResponse<T> extends BasicResponse<T> {

    public ErrorResponse(ErrorCode errorCode) {
        super(errorCode.getStatusCode(), errorCode.getMessage(), null);
    }

    public ErrorResponse(ErrorCode errorCode, T data) {
        super(errorCode.getStatusCode(), errorCode.getMessage(), data);
    }
}
