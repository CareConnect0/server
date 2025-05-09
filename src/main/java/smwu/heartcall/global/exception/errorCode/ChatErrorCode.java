package smwu.heartcall.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChatErrorCode implements ErrorCode {
    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "채팅방을 찾을 수 없습니다."),
    USER_NOT_PARTICIPANT(HttpStatus.BAD_REQUEST.value(), "해당 채팅방의 유저가 아닙니다.")
    ;

    private final int statusCode;
    private final String message;
}
