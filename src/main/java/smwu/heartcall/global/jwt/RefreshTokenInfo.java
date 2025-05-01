package smwu.heartcall.global.jwt;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class RefreshTokenInfo implements Serializable {

    private String refreshToken;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiration;

    @Builder
    public RefreshTokenInfo(String refreshToken, Long expiration) {
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }
}
