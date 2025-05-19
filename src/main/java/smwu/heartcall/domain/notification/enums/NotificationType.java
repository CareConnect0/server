package smwu.heartcall.domain.notification.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationType {
    SCHEDULE_CREATE("${name}님의 일정이 추가되었습니다!"),
    SCHEDULE_EDIT("${name}님의 일정이 수정되었습니다!"),
    SCHEDULE_CHECK("${name}님이 일정을 확인했습니다!"),
    MESSAGE("${name}님의 메시지가 도착했습니다!"),
    EMERGENCY("${name} 님에게 도움이 필요합니다!")
    ;

    private final String titleTemplate;

    public String formatTitle(String name) {
        return titleTemplate.replace("${name}",  name);
    }
}
