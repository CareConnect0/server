package smwu.heartcall.domain.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.domain.user.repository.UserRepository;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.AuthErrorCode;
import smwu.heartcall.global.jwt.JwtProvider;
import smwu.heartcall.global.jwt.RefreshTokenService;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    /**
     * Access Token, RefreshToken 재발급
     */
    @Transactional
    public void reissue(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = jwtProvider.getJwtFromHeader(request, JwtProvider.AUTHORIZATION_HEADER);
        String refreshToken = jwtProvider.getJwtFromHeader(request, JwtProvider.REFRESH_HEADER);

        // 액세스가 만료되었는지 확인
        if (!jwtProvider.isExpiredAccessToken(accessToken)) {
            throw new CustomException(AuthErrorCode.NOT_EXPIRED_ACCESS_TOKEN);
        }

        // 리프레시 토큰 검증
        if (jwtProvider.validateTokenInternal(request, refreshToken)) {
            String username = jwtProvider.getUserInfoFromClaims(refreshToken).getSubject();

            refreshTokenService.checkValidRefreshToken(username, refreshToken);

            // 리프레시 토큰 기반으로 유저 찾기
            User findUser = userRepository.findByUsernameOrElseThrow(username);

            // 새 토큰 발급
            String newAccessToken = jwtProvider.createAccessToken(findUser.getUsername(),
                    findUser.getUserRole());
            String newRefreshToken = jwtProvider.createRefreshToken(findUser.getUsername());

            refreshTokenService.saveRefreshTokenInfo(findUser.getUsername(), newRefreshToken);

            response.addHeader(JwtProvider.AUTHORIZATION_HEADER, newAccessToken);
            response.addHeader(JwtProvider.REFRESH_HEADER, newRefreshToken);

        } else {
            throw new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    @Transactional
    public void logout(User user) {
        refreshTokenService.deleteRefreshTokenInfo(user.getUsername());
    }
}