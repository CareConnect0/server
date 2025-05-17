package smwu.heartcall.domain.emergency.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import smwu.heartcall.domain.emergency.dto.CallEmergencyRequestDto;
import smwu.heartcall.domain.emergency.dto.EmergencyAudioUrlResponseDto;
import smwu.heartcall.domain.emergency.dto.EmergencyDetailResponseDto;
import smwu.heartcall.domain.emergency.dto.GetEmergencyResponseDto;
import smwu.heartcall.domain.emergency.service.EmergencyService;
import smwu.heartcall.global.response.BasicResponse;
import smwu.heartcall.global.security.UserDetailsImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/emergency")
public class EmergencyController {
    private final EmergencyService emergencyService;

    @PreAuthorize("@userPermissionChecker.isDependent(authentication)")
    @PostMapping("/audio")
    public ResponseEntity<BasicResponse<EmergencyAudioUrlResponseDto>> uploadEmergencyAudio(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart MultipartFile audioFile
    ) {
        EmergencyAudioUrlResponseDto responseDto = emergencyService.uploadEmergencyAudio(userDetails.getUser(), audioFile);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of("비상 호출 음성 업로드 완료", responseDto));
    }
    @PostMapping("/audio-trigger")
    @PreAuthorize("@userPermissionChecker.isDependent(authentication)")
    public ResponseEntity<BasicResponse<Void>> callEmergencyByAudio(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid CallEmergencyRequestDto requestDto
    ) {
        emergencyService.callEmergencyByAudio(userDetails.getUser(), requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BasicResponse.of("비상 호출 데이터 생성 완료"));
    }

    @PostMapping
    @PreAuthorize("@userPermissionChecker.isDependent(authentication)")
    public ResponseEntity<BasicResponse<Void>> callEmergency(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        emergencyService.callEmergency(userDetails.getUser());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BasicResponse.of("비상 호출 데이터 생성 완료"));
    }

    // 특정 유저가 보낸 비상 호출 목록 조회
    @GetMapping
    @PreAuthorize("@userPermissionChecker.isGuardian(authentication)")
    public ResponseEntity<BasicResponse<List<GetEmergencyResponseDto>>> getEmergencies(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long dependentId
    ) {
        List<GetEmergencyResponseDto> responseDto = emergencyService.getEmergencies(userDetails.getUser(), dependentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of("비상 호출 데이터 조회 완료", responseDto));
    }

    @PatchMapping("/{emergencyId}")
    @PreAuthorize("@userPermissionChecker.isGuardian(authentication)")
    public ResponseEntity<BasicResponse<EmergencyDetailResponseDto>> handleEmergency(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long emergencyId
    ) {
        EmergencyDetailResponseDto responseDto = emergencyService.checkEmergency(userDetails.getUser(), emergencyId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of("비상 호출 데이터 확인 완료", responseDto));
    }
}
