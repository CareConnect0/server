package smwu.heartcall.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDatetimeUtil {
    public static LocalDateTime fromStringToLocalDateTime(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(datetime, formatter);
    }
}
