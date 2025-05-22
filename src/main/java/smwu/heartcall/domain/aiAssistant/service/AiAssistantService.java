package smwu.heartcall.domain.aiAssistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.heartcall.domain.aiAssistant.dto.*;
import smwu.heartcall.domain.aiAssistant.entity.AiAssistantMessage;
import smwu.heartcall.domain.aiAssistant.entity.AiAssistantRoom;
import smwu.heartcall.domain.aiAssistant.enums.SenderType;
import smwu.heartcall.domain.aiAssistant.repository.AiAssistantMessageRepository;
import smwu.heartcall.domain.aiAssistant.repository.AiAssistantRoomRepository;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.AiAssistantErrorCode;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AiAssistantService {
    private final SimpMessagingTemplate messagingTemplate;
    private final AiAssistantRoomRepository assistantRoomRepository;
    private final AiAssistantMessageRepository assistantMessageRepository;

    @Transactional
    public AssistantRoomResponseDto getOrCreateRoom(User user) {
        // 이미 존재하는 채팅방이 있는지 확인
        Optional<AiAssistantRoom> existingRoom = assistantRoomRepository
                .findByUser(user);

        AiAssistantRoom assistantRoom = existingRoom.orElseGet(() -> {
            AiAssistantRoom newRoom = AiAssistantRoom.builder()
                    .user(user)
                    .build();
            return assistantRoomRepository.save(newRoom);
        });

        return AssistantRoomResponseDto.of(assistantRoom);
    }

    public AssistantMessageScrollResponseDto getMessages(User user, Long roomId, Long lastMessageId, int size) {
        Page<AiAssistantMessage> messagePage;
        AiAssistantRoom chatRoom = assistantRoomRepository.findByIdOrElseThrow(roomId);

        if(!chatRoom.getUser().getId().equals(user.getId())) {
            throw new CustomException(AiAssistantErrorCode.USER_NOT_PARTICIPANT);
        }

        Pageable pageable = PageRequest.of(0, size);

        if (lastMessageId == null) {
            // 처음 로딩할 때: 최신 메시지부터 size개
            messagePage = assistantMessageRepository.findByAiAssistantRoomOrderByIdDesc(chatRoom, pageable);
        } else {
            // 이후 로딩: lastMessageId보다 작은 메시지부터 size개
            messagePage = assistantMessageRepository.findByAiAssistantRoomAndIdLessThanOrderByIdDesc(chatRoom, lastMessageId, pageable);
        }

        boolean hasNext = messagePage.hasNext();
        return AssistantMessageScrollResponseDto.of(messagePage, hasNext);
    }

    @Transactional
    public UserMessageResponseDto createUserRequestMessage(User user, UserMessageRequestDto requestDto) {
        AiAssistantRoom assistantRoom = assistantRoomRepository.findByIdOrElseThrow(requestDto.getRoomId());
        if(!assistantRoom.getUser().getId().equals(user.getId())) {
            throw new CustomException(AiAssistantErrorCode.USER_NOT_PARTICIPANT);
        }

        AiAssistantMessage message = AiAssistantMessage.builder()
                .aiAssistantRoom(assistantRoom)
                .content(requestDto.getRequestMessage())
                .senderType(SenderType.USER)
                .build();

        assistantMessageRepository.save(message);
        return UserMessageResponseDto.of(message);
    }

    @Transactional
    public AiMessageResponseDto createAiResponseMessage(AiMessageRequestDto requestDto) {
        AiAssistantRoom assistantRoom = assistantRoomRepository.findByIdOrElseThrow(requestDto.getRoomId());

        AiAssistantMessage message = AiAssistantMessage.builder()
                .aiAssistantRoom(assistantRoom)
                .content(requestDto.getResponseMessage())
                .senderType(SenderType.ASSISTANT)
                .build();

        assistantMessageRepository.save(message);

        messagingTemplate.convertAndSend("/sub/assistant/rooms/" + assistantRoom.getId(), AiMessageResponseDto.of(message));
        return AiMessageResponseDto.of(message);
    }
}
