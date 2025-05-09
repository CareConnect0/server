package smwu.heartcall.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import smwu.heartcall.domain.user.entity.DependentRelation;
import smwu.heartcall.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface RelationRepository extends JpaRepository<DependentRelation, Long> {
    Boolean existsByDependent(User dependent);
    Boolean existsByDependentAndGuardian(User dependent, User guardian);

    List<DependentRelation> findByGuardian(User guardian);

    @Query("SELECT r FROM DependentRelation r WHERE r.guardian = :user OR r.dependent = :user")
    List<DependentRelation> findByUser(@Param("user") User user);
}
