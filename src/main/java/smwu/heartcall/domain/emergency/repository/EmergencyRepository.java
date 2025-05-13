package smwu.heartcall.domain.emergency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import smwu.heartcall.domain.emergency.entity.Emergency;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.EmergencyErrorCode;

import java.util.List;

public interface EmergencyRepository extends JpaRepository<Emergency, Long> {
    default Emergency findByIdOrElseThrow(Long emergencyId) {
        return findById(emergencyId).orElseThrow(()
                -> new CustomException(EmergencyErrorCode.EMERGENCY_NOT_FOUND));
    }

    @Query("SELECT e FROM Emergency e WHERE e.dependent = :dependent and e.isDeleted = false")
    List<Emergency> findActiveByDependent(User dependent);
}
