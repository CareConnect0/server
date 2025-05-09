package smwu.heartcall.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import smwu.heartcall.domain.chat.dto.ChatRoomRequestDto;
import smwu.heartcall.domain.chat.dto.ChatRoomResponseDto;
import smwu.heartcall.domain.chat.dto.TargetResponseDto;
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
    // 대화 가능 상대 조회
    @GetMapping("/available-users")
    public ResponseEntity<BasicResponse<List<TargetResponseDto>>> getChatTargets(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<TargetResponseDto> responseDtoList = chatRoomService.getChatTargets(userDetails.getUser());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of(HttpStatus.OK.value(), "채팅 가능한 사용자 목록입니다.", responseDtoList));
    }

    // 채팅방 생성 혹은 조회 (채팅방 id 반환)
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


    // 채팅방 메시지 목록 조회

}
