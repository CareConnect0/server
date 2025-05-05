package smwu.heartcall.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.heartcall.domain.user.entity.DependentRelation;
import smwu.heartcall.domain.user.entity.User;

import java.util.List;

public interface RelationRepository extends JpaRepository<DependentRelation, Long> {
    Boolean existsByDependent(User dependent);
    Boolean existsByDependentAndGuardian(User dependent, User guardian);

    List<DependentRelation> findByGuardian(User guardian);
}
