package smwu.heartcall.domain.aiAssistant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import smwu.heartcall.domain.aiAssistant.dto.*;
import smwu.heartcall.domain.aiAssistant.service.AiAssistantService;
import smwu.heartcall.domain.chat.dto.ChatMessageScrollResponseDto;
import smwu.heartcall.global.response.BasicResponse;
import smwu.heartcall.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assistant")
public class AiAssistantController {
    private final AiAssistantService assistantService;

    @GetMapping("/rooms")
    public ResponseEntity<BasicResponse<AssistantRoomResponseDto>> getOrCreateRoom(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        AssistantRoomResponseDto responseDto = assistantService.getOrCreateRoom(userDetails.getUser());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of("AI 비서 채팅방 조회 완료", responseDto));
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<BasicResponse<AssistantMessageScrollResponseDto>> getMessages(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long roomId,
            @RequestParam(required = false) Long lastMessageId,
            @RequestParam(defaultValue = "20") int size
    ) {
        AssistantMessageScrollResponseDto responseDto = assistantService.getMessages(userDetails.getUser(), roomId, lastMessageId, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of(HttpStatus.OK.value(), "메시지 조회 완료", responseDto));
    }


    @PostMapping("/request")
    public ResponseEntity<BasicResponse<UserMessageResponseDto>> createUserRequestMessage(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid UserMessageRequestDto requestDto
    ) {
        UserMessageResponseDto responseDto = assistantService.createUserRequestMessage(userDetails.getUser(), requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BasicResponse.of("사용자 요청 메시지 저장 완료", responseDto));
    }

    @PostMapping("/response")
    public ResponseEntity<BasicResponse<AiMessageResponseDto>> createAiResponseMessage(
            @RequestBody @Valid AiMessageRequestDto requestDto
    ) {
        AiMessageResponseDto responseDto = assistantService.createAiResponseMessage(requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BasicResponse.of("AI 비서 메시지 저장 완료", responseDto));
    }
}
