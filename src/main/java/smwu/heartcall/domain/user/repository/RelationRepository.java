package smwu.heartcall.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import smwu.heartcall.domain.user.entity.DependentRelation;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.RelationErrorCode;

import java.util.List;
import java.util.Optional;

public interface RelationRepository extends JpaRepository<DependentRelation, Long> {
    Boolean existsByDependent(User dependent);
    Boolean existsByDependentAndGuardian(User dependent, User guardian);

    Optional<DependentRelation> findByDependent(User dependent);

    List<DependentRelation> findByGuardian(User guardian);

    @Query("SELECT r FROM DependentRelation r WHERE r.guardian = :user OR r.dependent = :user")
    List<DependentRelation> findByUser(@Param("user") User user);

    default DependentRelation findByDependentOrElseThrow(User dependent) {
        return findByDependent(dependent).orElseThrow(() ->
                new CustomException(RelationErrorCode.DEPENDENT_RELATION_NOT_FOUND));
    }
}
