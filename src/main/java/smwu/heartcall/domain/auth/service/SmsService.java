package smwu.heartcall.domain.auth.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import smwu.heartcall.domain.auth.dto.SendCodeRequestDto;
import smwu.heartcall.domain.auth.dto.VerifyCodeRequestDto;
import smwu.heartcall.domain.auth.repository.SmsRepository;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.SmsErrorCode;
import smwu.heartcall.global.util.CodeGenerator;

@Service
@RequiredArgsConstructor
public class SmsService {
    @Value("${coolsms.api-key}")
    private String key;

    @Value("${coolsms.secret-key}")
    private String secret;

    @Value("${coolsms.provider}")
    private String provider;

    @Value("${coolsms.sender}")
    private String sender;

    private DefaultMessageService messageService;
    private final SmsRepository smsRepository;

    @PostConstruct
    public void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(
                key, secret, provider
        );
    }

    @Transactional
    public void sendVerificationCode(SendCodeRequestDto responseDto) {
        String phoneNumber = responseDto.getPhoneNumber(); // TODO : 휴대폰 번호 중복 허용?
        String code = CodeGenerator.generateNumberCode();
        smsRepository.saveVerificationCode(phoneNumber, code);

        Message message = new Message();
        message.setFrom(sender);
        message.setTo(phoneNumber);
        message.setText("[인증번호] " + code + " 를 입력해주세요.");

        messageService.sendOne(new SingleMessageSendingRequest(message));
    }

    @Transactional
    public void verifyCode(VerifyCodeRequestDto requestDto) {
        String code = requestDto.getCode();
        String phoneNumber = requestDto.getPhoneNumber();

        if(!code.equals(smsRepository.getVerificationCode(phoneNumber))) {
            throw new CustomException(SmsErrorCode.INVALID_VERIFICATION_CODE);
        }

        smsRepository.removeVerificationCode(phoneNumber);
    }
}
