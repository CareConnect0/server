package smwu.heartcall.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RelationErrorCode implements ErrorCode {
    NOT_A_GUARDIAN_OF_USER(HttpStatus.BAD_REQUEST.value(), "해당 피보호자에 대한 권한이 없습니다."),
    DEPENDENT_RELATION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 피보호자와 연결된 보호자가 없습니다.")
    ;

    private final int statusCode;
    private final String message;
}