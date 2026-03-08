package smwu.heartcall.domain.aiAssistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.heartcall.domain.aiAssistant.entity.AiAssistantRoom;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.AiAssistantErrorCode;

import java.util.Optional;

public interface AiAssistantRoomRepository extends JpaRepository<AiAssistantRoom, Long> {
    Optional<AiAssistantRoom> findByUser(User user);

    default AiAssistantRoom findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(()
                -> new CustomException(AiAssistantErrorCode.ASSISTANT_ROOM_NOT_FOUND));
    }
}
