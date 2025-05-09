package smwu.heartcall.global.security.filter;

import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import smwu.heartcall.global.jwt.JwtProvider;
import smwu.heartcall.global.jwt.RefreshTokenService;
import smwu.heartcall.global.security.UserDetailsServiceImpl;

import java.util.List;
import java.util.Map;

import static smwu.heartcall.global.jwt.JwtProvider.BEARER_PREFIX;

@Configuration
@RequiredArgsConstructor
public class StompHandShakeInterceptor implements ChannelInterceptor {
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if(StompCommand.CONNECTED.equals(accessor.getCommand())) {
            List<String> authorization = accessor.getNativeHeader(JwtProvider.AUTHORIZATION_HEADER);
            if (authorization != null && !authorization.isEmpty()) {
                String accessToken = authorization.get(0).substring(BEARER_PREFIX.length());

                if (jwtProvider.validateTokenInternal(accessToken)) {
                    String username = jwtProvider.getUserInfoFromClaims(accessToken).getSubject();

                    if (refreshTokenService.isRefreshTokenPresent(username)) {
                        setAuthentication(username);
                    } else {
//                        throw new CustomSecurityException(SecurityErrorCode.INVALID_ACCESS_TOKEN);
                        return null;
                    }
                } else {
//                    throw new CustomSecurityException(SecurityErrorCode.INVALID_ACCESS_TOKEN);
                    return null;
                }
            }
        }
        return message;
    }

    private void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        var authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
