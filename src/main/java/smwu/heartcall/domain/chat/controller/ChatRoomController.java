package smwu.heartcall.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import smwu.heartcall.domain.chat.dto.*;
import smwu.heartcall.domain.chat.entity.ChatRoom;
import smwu.heartcall.domain.chat.service.ChatRoomService;
import smwu.heartcall.global.response.BasicResponse;
import smwu.heartcall.global.security.UserDetailsImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/available-users")
    public ResponseEntity<BasicResponse<List<TargetResponseDto>>> getChatTargets(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<TargetResponseDto> responseDtoList = chatRoomService.getChatTargets(userDetails.getUser());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of(HttpStatus.OK.value(), "채팅 가능한 사용자 목록입니다.", responseDtoList));
    }

    @PostMapping("/rooms")
    public ResponseEntity<BasicResponse<ChatRoomResponseDto>> getOrCreateChatRoom(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ChatRoomRequestDto requestDto
    ) {
        ChatRoomResponseDto responseDto = chatRoomService.getOrCreateChatRoom(userDetails.getUser(), requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of(HttpStatus.OK.value(), "채팅방 조회 완료", responseDto));
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<BasicResponse<ChatMessageScrollResponseDto>> getChatMessages(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long roomId,
            @RequestParam(required = false) Long lastMessageId,
            @RequestParam(defaultValue = "20") int size
    ) {
        ChatMessageScrollResponseDto responseDto = chatRoomService.getChatMessages(userDetails.getUser(), roomId, lastMessageId, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of(HttpStatus.OK.value(), "채팅방 메시지 조회 완료", responseDto));
    }
}
