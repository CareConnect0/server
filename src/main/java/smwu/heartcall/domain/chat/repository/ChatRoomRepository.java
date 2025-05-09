package smwu.heartcall.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.heartcall.domain.chat.entity.ChatRoom;
import smwu.heartcall.domain.user.entity.User;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByGuardianAndDependent(User guardian, User dependent);
}
