package smwu.heartcall.global.fcm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.FcmErrorCode;
import smwu.heartcall.global.exception.errorCode.ScheduleErrorCode;
import smwu.heartcall.global.fcm.entity.FcmToken;

import java.util.List;
import java.util.Optional;

public interface FcmRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findByUser(User user);

    default FcmToken findByUserOrElseThrow(User user) {
        return findByUser(user).orElseThrow(()
                -> new CustomException(FcmErrorCode.TOKEN_NOT_FOUND));
    }

    List<FcmToken> findAllByUser(User user);
}
