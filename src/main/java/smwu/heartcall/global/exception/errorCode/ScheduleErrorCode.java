package smwu.heartcall.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ScheduleErrorCode implements ErrorCode {
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "회원을 찾을 수 없습니다."),
    ;

    private final int statusCode;
    private final String message;
}
