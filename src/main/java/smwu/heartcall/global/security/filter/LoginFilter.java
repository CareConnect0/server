package smwu.heartcall.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.domain.user.entity.UserStatus;
import smwu.heartcall.global.exception.errorCode.SecurityErrorCode;
import smwu.heartcall.global.jwt.JwtProvider;
import smwu.heartcall.global.jwt.RefreshTokenService;
import smwu.heartcall.global.security.UserDetailsImpl;
import smwu.heartcall.global.security.dto.LoginRequestDto;
import smwu.heartcall.global.util.ResponseUtil;

import java.io.IOException;

@Slf4j(topic = "로그인 필터")
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public LoginFilter(JwtProvider jwtProvider, RefreshTokenService refreshTokenService) {
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword(), null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        User loginUser = userDetails.getUser();

        if(loginUser.getUserStatus() == UserStatus.ACTIVATE) {
            String username = loginUser.getUsername();
            String accessToken = jwtProvider.createAccessToken(username, loginUser.getUserRole());
            String refreshToken = jwtProvider.createRefreshToken(username);

            refreshTokenService.saveRefreshTokenInfo(username, refreshToken);
            response.addHeader(JwtProvider.AUTHORIZATION_HEADER, accessToken);
            response.addHeader(JwtProvider.REFRESH_HEADER, refreshToken);

            ResponseUtil.writeJsonResponse(response, HttpStatus.OK, "로그인 성공", authResult.getName());
        } else {
            ResponseUtil.writeJsonErrorResponse(response, SecurityErrorCode.WITHDRAWAL_USER);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.debug("로그인 실패 : {}", failed.getMessage());
        ResponseUtil.writeJsonErrorResponse(response, SecurityErrorCode.LOGIN_FAILED);
    }
}