package smwu.heartcall.global.util;

public class CodeGenerator {
    public static String generateNumberCode() {
        return String.valueOf((int) ((Math.random() * 900000) + 100000)); // 6자리
    }
}
