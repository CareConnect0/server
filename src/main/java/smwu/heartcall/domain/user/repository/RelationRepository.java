package smwu.heartcall.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.heartcall.domain.user.entity.DependentRelation;
import smwu.heartcall.domain.user.entity.User;

import javax.management.relation.Relation;
import java.util.List;

public interface RelationRepository extends JpaRepository<DependentRelation, Long> {
    Boolean existsByDependent(User dependent);

    List<DependentRelation> findByGuardian(User guardian);
}
