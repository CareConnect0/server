package smwu.heartcall.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.UserErrorCode;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndName(String username, String name);
    Boolean existsByUsername(String username);

    default User findByUsernameOrElseThrow(String username) {
        return findByUsername(username).orElseThrow(()
                -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }

    default User findByUsernameAndNameOrElseThrow(String username, String name) {
        return findByUsernameAndName(username, name).orElseThrow(()
                -> new CustomException(UserErrorCode.NO_MATCHING_USER));
    }
}
