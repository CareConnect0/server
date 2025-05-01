package smwu.heartcall.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.heartcall.domain.user.dto.SignUpRequestDto;
import smwu.heartcall.domain.user.dto.WithdrawRequestDto;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.domain.user.entity.UserRole;
import smwu.heartcall.domain.user.entity.UserStatus;
import smwu.heartcall.domain.user.repository.UserRepository;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.UserErrorCode;
import smwu.heartcall.global.jwt.RefreshTokenService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignUpRequestDto requestDto) {
        User user = User.builder()
                .name(requestDto.getName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .username(requestDto.getUsername())
                .phoneNumber(requestDto.getPhoneNumber())
                .userType(requestDto.getUserType())
                .userStatus(UserStatus.ACTIVATE)
                .userRole(UserRole.ROLE_USER)
                .build();

        userRepository.save(user);
    }

    public void verifyUsername(String username) {
        if(userRepository.existsByUsername(username)) {
            throw new CustomException(UserErrorCode.DUPLICATE_USERNAME);
        }
    }

    @Transactional
    public void withdraw(User user, WithdrawRequestDto requestDto) {
        String currentPassword = user.getPassword();
        String inputPassword = requestDto.getPassword();

        checkPasswordMatch(currentPassword, inputPassword);
        user.withdraw();
        userRepository.save(user);

        refreshTokenService.deleteRefreshTokenInfo(user.getUsername());
    }

    private void checkPasswordMatch(String realPassword, String inputPassword) {
        if(!passwordEncoder.matches(inputPassword, realPassword)) { // 인코딩 이전 값, 인코딩 이후 값
            throw new CustomException(UserErrorCode.PASSWORD_MISMATCH);
        }
    }
}
