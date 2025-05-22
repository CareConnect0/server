package smwu.heartcall.global.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import smwu.heartcall.global.exception.CustomSecurityException;
import smwu.heartcall.global.exception.errorCode.SecurityErrorCode;
import smwu.heartcall.global.jwt.JwtProvider;
import smwu.heartcall.global.jwt.RefreshTokenService;
import smwu.heartcall.global.security.UserDetailsServiceImpl;

import java.util.List;

import static smwu.heartcall.global.jwt.JwtProvider.BEARER_PREFIX;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandShakeInterceptor implements ChannelInterceptor {
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        log.info("preSend 호출됨 - Command: {}", accessor.getCommand());
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            List<String> authorization = accessor.getNativeHeader(JwtProvider.AUTHORIZATION_HEADER);
            if (authorization != null && !authorization.isEmpty()) {
                String accessToken = authorization.get(0).substring(BEARER_PREFIX.length());

                if (jwtProvider.validateTokenInternal(accessToken)) {
                    String username = jwtProvider.getUserInfoFromClaims(accessToken).getSubject();

                    if (refreshTokenService.isRefreshTokenPresent(username)) {
                        setAuthentication(username, accessor);
                    } else {
                        throw new CustomSecurityException(SecurityErrorCode.INVALID_ACCESS_TOKEN);
                    }
                } else {
                    throw new CustomSecurityException(SecurityErrorCode.INVALID_ACCESS_TOKEN);
                }
            }
        }
        log.info("Principal set : {}", accessor.getUser());
        return message;
    }

    private void setAuthentication(String username, StompHeaderAccessor accessor){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        // Accessor (STOMP 접근자)에 사용자 정보 저장
        accessor.setUser(authentication);
        accessor.getSessionAttributes().put("user", authentication);  // 세션에 인증 객체 저장
        // Authentication 객체를 SecurityContextHolder에 설정해 현재 보안 컨텍스트에 인증 정보 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}