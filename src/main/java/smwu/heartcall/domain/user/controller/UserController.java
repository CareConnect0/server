package smwu.heartcall.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import smwu.heartcall.domain.user.dto.LinkRequestDto;
import smwu.heartcall.domain.user.dto.SignUpRequestDto;
import smwu.heartcall.domain.user.dto.WithdrawRequestDto;
import smwu.heartcall.domain.user.service.UserService;
import smwu.heartcall.global.response.BasicResponse;
import smwu.heartcall.global.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<BasicResponse<Void>> signup(
            @RequestBody @Valid SignUpRequestDto requestDto
    ) {
        userService.signup(requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BasicResponse.of(HttpStatus.CREATED.value(), "회원가입 성공"));
    }

    @GetMapping("/check-username")
    public ResponseEntity<BasicResponse<Void>> verifyUsername(
            @RequestParam String username
    ) {
        userService.verifyUsername(username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of(HttpStatus.OK.value(), "사용할 수 있는 아이디입니다."));
    }

    @PatchMapping("/withdrawal")
    public ResponseEntity<BasicResponse<Void>> withdraw(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid WithdrawRequestDto requestDto
    ) {
        userService.withdraw(userDetails.getUser(), requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of("회원 탈퇴 성공"));
    }

    @PreAuthorize("@userPermissionChecker.isDependent(authentication)")
    @PostMapping("/link")
    public ResponseEntity<BasicResponse<Void>> linkGuardian(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid LinkRequestDto requestDto
    ) {
        userService.linkGuardian(userDetails.getUser(), requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BasicResponse.of("보호자 연결 성공"));
    }

    // TODO : 비밀번호 변경 API
    // TODO : 피보호자 목록 조회 API
}