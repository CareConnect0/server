package smwu.heartcall.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.heartcall.domain.chat.dto.MessageResponseDto;
import smwu.heartcall.domain.chat.dto.SaveMessageRequestDto;
import smwu.heartcall.domain.chat.entity.ChatMessage;
import smwu.heartcall.domain.chat.entity.ChatRoom;
import smwu.heartcall.domain.chat.repository.ChatMessageRepository;
import smwu.heartcall.domain.chat.repository.ChatRoomRepository;
import smwu.heartcall.domain.notification.service.NotificationService;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    public void sendMessage(Long roomId, SaveMessageRequestDto requestDto) {
//        User sender = userRepository.findByIdOrElseThrow(requestDto.getSenderId());
//        ChatRoom chatRoom = chatRoomRepository.findByIdOrElseThrow(roomId);
//
//        User receiver;
//        if (sender.getId().equals(chatRoom.getGuardian().getId())) {
//            receiver = chatRoom.getDependent();
//        } else {
//            receiver = chatRoom.getGuardian();
//        }
//
//        ChatMessage chatMessage = ChatMessage.builder()
//                .chatRoom(chatRoom)
//                .sender(sender)
//                .content(requestDto.getContent())
//                .build();
//
//        chatMessageRepository.save(chatMessage);
//
//        messagingTemplate.convertAndSend("/sub/chats/rooms/" + roomId, MessageResponseDto.of(chatMessage));
//        notificationService.sendChatNotifications(sender, receiver, requestDto.getContent());
    }

    @Transactional
    public void sendMessage(User sender, Long roomId, SaveMessageRequestDto requestDto) {
//        User sender = userRepository.findByIdOrElseThrow(requestDto.getSenderId());
        ChatRoom chatRoom = chatRoomRepository.findByIdOrElseThrow(roomId);

        User receiver;
        if (sender.getId().equals(chatRoom.getGuardian().getId())) {
            receiver = chatRoom.getDependent();
        } else {
            receiver = chatRoom.getGuardian();
        }

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .content(requestDto.getContent())
                .build();

        chatMessageRepository.save(chatMessage);

        messagingTemplate.convertAndSend("/sub/chats/rooms/" + roomId, MessageResponseDto.of(chatMessage));
        notificationService.sendChatNotifications(sender, receiver, requestDto.getContent());
    }
}
