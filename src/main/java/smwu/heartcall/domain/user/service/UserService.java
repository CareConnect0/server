package smwu.heartcall.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.heartcall.domain.user.dto.DependentDetailResponseDto;
import smwu.heartcall.domain.user.dto.LinkRequestDto;
import smwu.heartcall.domain.user.dto.SignUpRequestDto;
import smwu.heartcall.domain.user.dto.WithdrawRequestDto;
import smwu.heartcall.domain.user.entity.DependentRelation;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.domain.user.enums.UserRole;
import smwu.heartcall.domain.user.enums.UserStatus;
import smwu.heartcall.domain.user.enums.UserType;
import smwu.heartcall.domain.user.repository.RelationRepository;
import smwu.heartcall.domain.user.repository.UserRepository;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.UserErrorCode;
import smwu.heartcall.global.jwt.RefreshTokenService;

import javax.management.relation.Relation;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final RelationRepository relationRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignUpRequestDto requestDto) {
        verifyUsername(requestDto.getUsername());

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

    @Transactional
    public void linkGuardian(User dependent, LinkRequestDto requestDto) {
        checkAlreadyLinkedDependent(dependent);

        User guardian = userRepository.findByUsernameOrElseThrow(requestDto.getGuardianUsername());

        validateUserIsGuardian(guardian);

        DependentRelation relation = DependentRelation.builder()
                .dependent(dependent)
                .guardian(guardian)
                .build();

        relationRepository.save(relation);
    }

    public List<DependentDetailResponseDto> getDependentList(User guardian) {
        List<DependentRelation> relations = relationRepository.findByGuardian(guardian);
        return relations.stream().map(DependentDetailResponseDto::of).toList();
    }

    private void checkPasswordMatch(String realPassword, String inputPassword) {
        if(!passwordEncoder.matches(inputPassword, realPassword)) { // 인코딩 이전 값, 인코딩 이후 값
            throw new CustomException(UserErrorCode.PASSWORD_MISMATCH);
        }
    }

    private void checkAlreadyLinkedDependent(User dependent) {
        if(relationRepository.existsByDependent(dependent)) {
            throw new CustomException(UserErrorCode.DEPENDENT_USER_ALREADY_LINKED);
        }
    }

    private void validateUserIsGuardian(User guardian) {
        if(!guardian.getUserType().equals(UserType.GUARDIAN)) {
            throw new CustomException(UserErrorCode.USER_IS_NOT_GUARDIAN);
        }
    }
}
