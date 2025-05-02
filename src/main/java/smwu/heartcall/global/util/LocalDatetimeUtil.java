package smwu.heartcall.global.util;

import java.time.LocalDateTime;

public class LocalDatetimeUtil {
    public static LocalDateTime fromStringToLocalDateTime(String datetime) {
        return LocalDateTime.parse(datetime);
    }
}
