package smwu.heartcall.domain.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.SmsErrorCode;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class SmsRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String SMS_PREFIX = "sms : ";

    private static final int VERIFICATION_LIMIT_IN_SECONDS = 300; // 5분

    public void saveVerificationCode(String phoneNumber, String verificationCode) {
        String key = createSmsPrefix(phoneNumber);
        redisTemplate.opsForValue()
                .set(key, verificationCode, Duration.ofSeconds(VERIFICATION_LIMIT_IN_SECONDS));
    }

    public String getVerificationCode(String phoneNumber) {
        String verificationCode = (String) redisTemplate.opsForValue().get(createSmsPrefix(phoneNumber));
        if(verificationCode == null) {
            throw new CustomException(SmsErrorCode.VERIFICATION_CODE_NOT_FOUND);
        }
        return verificationCode;
    }

    public void removeVerificationCode(String phoneNumber) {
        redisTemplate.delete(createSmsPrefix(phoneNumber));
    }

    private String createSmsPrefix(String phoneNumber) {
        return SMS_PREFIX + phoneNumber;
    }
}
