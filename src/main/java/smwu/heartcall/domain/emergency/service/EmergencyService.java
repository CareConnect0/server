package smwu.heartcall.domain.emergency.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import smwu.heartcall.domain.emergency.dto.CallEmergencyRequestDto;
import smwu.heartcall.domain.emergency.dto.EmergencyAudioUrlResponseDto;
import smwu.heartcall.domain.emergency.dto.EmergencyDetailResponseDto;
import smwu.heartcall.domain.emergency.dto.GetEmergencyResponseDto;
import smwu.heartcall.domain.emergency.entity.Emergency;
import smwu.heartcall.domain.emergency.repository.EmergencyRepository;
import smwu.heartcall.domain.notification.service.NotificationService;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.domain.user.repository.RelationRepository;
import smwu.heartcall.domain.user.repository.UserRepository;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.EmergencyErrorCode;
import smwu.heartcall.global.exception.errorCode.RelationErrorCode;
import smwu.heartcall.global.util.S3Uploader;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmergencyService {
    private final EmergencyRepository emergencyRepository;
    private final UserRepository userRepository;
    private final RelationRepository relationRepository;
    private final S3Uploader s3Uploader;
    private final NotificationService notificationService;

    @Transactional
    public void callEmergency(User dependent) {
        Emergency emergency = Emergency.builder()
                .dependent(dependent)
                .build();

        emergencyRepository.save(emergency);
        notificationService.sendEmergencyNotifications(dependent, NotificationService.DIRECT_EMERGENCY_CONTENT);
    }

    @Transactional
    public EmergencyAudioUrlResponseDto uploadEmergencyAudio(User user, MultipartFile audioFile) {
        String url = s3Uploader.uploadEmergencyAudio(audioFile, user.getId());
        return EmergencyAudioUrlResponseDto.of(url);
    }

    @Transactional
    public void callEmergencyByAudio(User dependent, CallEmergencyRequestDto requestDto) {
        Emergency emergency = Emergency.builder()
                .dependent(dependent)
                .keyword(requestDto.getKeyword())
                .audioUrl(requestDto.getAudioUrl())
                .build();

        emergencyRepository.save(emergency);
        notificationService.sendEmergencyNotifications(dependent, NotificationService.AUDIO_EMERGENCY_CONTENT);
    }

    public List<GetEmergencyResponseDto> getEmergencies(User guardian, Long dependentId) {
        User dependent = userRepository.findByIdOrElseThrow(dependentId);
        checkGuardianRelation(dependent, guardian);
        List<Emergency> emergencyList = emergencyRepository.findActiveByDependent(dependent);
        return emergencyList.stream().map(GetEmergencyResponseDto::of).toList();
    }

    @Transactional
    public EmergencyDetailResponseDto checkEmergency(User guardian, Long emergencyId) {
        Emergency emergency = emergencyRepository.findByIdOrElseThrow(emergencyId);

        User dependent = emergency.getDependent();
        checkGuardianRelation(dependent, guardian);

        emergency.check();
        return EmergencyDetailResponseDto.of(emergency);
    }

    private void checkGuardianRelation(User dependent, User guardian) {
        if(!relationRepository.existsByDependentAndGuardian(dependent, guardian)) {
            throw new CustomException(RelationErrorCode.NOT_A_GUARDIAN_OF_USER);
        }
    }

    private void validateEmergencyOwnership(User dependent, Emergency emergency) {
        if(!emergency.getDependent().getId().equals(dependent.getId())) {
            throw new CustomException(EmergencyErrorCode.EMERGENCY_ACCESS_DENIED);
        }
    }
}
