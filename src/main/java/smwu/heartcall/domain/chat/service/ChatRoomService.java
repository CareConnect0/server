package smwu.heartcall.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.heartcall.domain.chat.dto.*;
import smwu.heartcall.domain.chat.entity.ChatMessage;
import smwu.heartcall.domain.chat.entity.ChatRoom;
import smwu.heartcall.domain.chat.repository.ChatMessageRepository;
import smwu.heartcall.domain.chat.repository.ChatRoomRepository;
import smwu.heartcall.domain.user.entity.DependentRelation;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.domain.user.enums.UserType;
import smwu.heartcall.domain.user.repository.RelationRepository;
import smwu.heartcall.domain.user.repository.UserRepository;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.ChatErrorCode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final RelationRepository relationRepository;

    public List<TargetResponseDto> getChatTargets(User user) {
        List<DependentRelation> relations = relationRepository.findByUser(user);
        return relations.stream()
                .map(relation -> {
                    User target;
                    if (relation.getGuardian().getId().equals(user.getId())) {
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

    public ChatMessageScrollResponseDto getChatMessages(User user, Long roomId, Long lastMessageId, int size) {
        Page<ChatMessage> messagePage;
        ChatRoom chatRoom = chatRoomRepository.findByIdOrElseThrow(roomId);

        if(!chatRoom.getDependent().getId().equals(user.getId()) && !chatRoom.getGuardian().getId().equals(user.getId())) {
            throw new CustomException(ChatErrorCode.USER_NOT_PARTICIPANT);
        }

        Pageable pageable = PageRequest.of(0, size);

        if (lastMessageId == null) {
            // 처음 로딩할 때: 최신 메시지부터 size개
            messagePage = chatMessageRepository.findByChatRoomOrderByIdDesc(chatRoom, pageable);
        } else {
            // 이후 로딩: lastMessageId보다 작은 메시지부터 size개
            messagePage = chatMessageRepository.findByChatRoomAndIdLessThanOrderByIdDesc(chatRoom, lastMessageId, pageable);
        }

        boolean hasNext = messagePage.hasNext();
        return ChatMessageScrollResponseDto.of(messagePage, hasNext);
    }
}
