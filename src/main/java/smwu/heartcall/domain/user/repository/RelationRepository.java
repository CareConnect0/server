package smwu.heartcall.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.heartcall.domain.user.entity.DependentRelation;
import smwu.heartcall.domain.user.entity.User;

public interface RelationRepository extends JpaRepository<DependentRelation, Long> {
    Boolean existsByDependent(User dependent);
}
