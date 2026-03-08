package smwu.heartcall.global.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import smwu.heartcall.global.exception.CustomSecurityException;
import smwu.heartcall.global.exception.errorCode.CommonErrorCode;
import smwu.heartcall.global.exception.errorCode.ErrorCode;
import smwu.heartcall.global.util.ResponseUtil;

import java.io.IOException;

@Slf4j(topic = "인증 예외 처리")
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        Exception exception = (Exception) request.getAttribute("exception");

        if (exception instanceof CustomSecurityException e) {
            ResponseUtil.writeJsonErrorResponse(response, e.getErrorCode());
            return;
        }

        log.warn("인증 실패: {}", authException.getMessage(), authException);

        // 인증 관련 예외 메시지를 담아서 클라이언트에 반환
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        String errorMessage = authException.getMessage();

        ResponseUtil.writeJsonErrorResponse(response, errorCode, errorMessage);
    }
}
