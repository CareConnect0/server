package smwu.heartcall.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum EmergencyErrorCode implements ErrorCode {
    EMERGENCY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 비상 호출을 찾을 수 없습니다."),
    EMERGENCY_ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "해당 비상 호출에 대한 권한이 없습니다.")
    ;

    private final int statusCode;
    private final String message;
}
