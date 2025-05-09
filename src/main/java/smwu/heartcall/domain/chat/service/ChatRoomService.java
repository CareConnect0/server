package smwu.heartcall.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.heartcall.domain.chat.dto.ChatRoomRequestDto;
import smwu.heartcall.domain.chat.dto.ChatRoomResponseDto;
import smwu.heartcall.domain.chat.dto.TargetResponseDto;
import smwu.heartcall.domain.chat.entity.ChatRoom;
import smwu.heartcall.domain.chat.repository.ChatRoomRepository;
import smwu.heartcall.domain.user.entity.DependentRelation;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.domain.user.enums.UserType;
import smwu.heartcall.domain.user.repository.RelationRepository;
import smwu.heartcall.domain.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final RelationRepository relationRepository;

    public List<TargetResponseDto> getChatTargets(User user) {
        List<DependentRelation> relations = relationRepository.findByUser(user);
        return relations.stream()
                .map(relation -> {
                    User target;
                    if (relation.getGuardian().equals(user)) {
                        target = relation.getDependent();
                    } else {
                        target = relation.getGuardian();
                    }
                    return TargetResponseDto.of(target);
                })
                .toList();
    }

    @Transactional
    public ChatRoomResponseDto getOrCreateChatRoom(User user, ChatRoomRequestDto requestDto) {
        User targetUser = userRepository.findByIdOrElseThrow(requestDto.getTargetId());

        User guardian, dependent;

        if (user.getUserType() == UserType.GUARDIAN) {
            guardian = user;
            dependent = targetUser;
        } else {
            guardian = targetUser;
            dependent = user;
        }

        // 이미 존재하는 채팅방이 있는지 확인
        Optional<ChatRoom> existingRoom = chatRoomRepository
                .findByGuardianAndDependent(guardian, dependent);

        ChatRoom chatRoom = existingRoom.orElseGet(() -> {
            ChatRoom newRoom = ChatRoom.builder()
                    .guardian(guardian)
                    .dependent(dependent)
                    .build();
            return chatRoomRepository.save(newRoom);
        });

        return ChatRoomResponseDto.of(chatRoom);
    }
}
