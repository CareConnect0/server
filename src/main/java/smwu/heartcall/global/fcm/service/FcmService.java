package smwu.heartcall.global.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.exception.CustomException;
import smwu.heartcall.global.exception.errorCode.FcmErrorCode;
import smwu.heartcall.global.fcm.dto.FcmTokenRequestDto;
import smwu.heartcall.global.fcm.entity.FcmToken;
import smwu.heartcall.global.fcm.repository.FcmRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final FcmRepository fcmRepository;

    @Transactional
    public void saveOrUpdateToken(User user, FcmTokenRequestDto requestDto) {
        Optional<FcmToken> existingToken = fcmRepository.findByUser(user);

        if (existingToken.isPresent()) {
            FcmToken token = existingToken.get();
            token.setToken(requestDto.getToken());
            fcmRepository.save(token); // 업데이트된 토큰 저장
        } else {
            FcmToken fcmToken = FcmToken.builder()
                    .user(user)
                    .token(requestDto.getToken())
                    .build();
            fcmRepository.save(fcmToken);
        }
    }

    public void sendNotification(String token, String title, String message) {
        try {
            Message fcmMessage = Message.builder()
                    .setToken(token)
                    .putData("title", title)
                    .putData("message", message)
                    .build();

            String response = FirebaseMessaging.getInstance().send(fcmMessage);
        } catch (FirebaseMessagingException e) {
            throw new CustomException(FcmErrorCode.FCM_BAD_REQUEST);
        }
    }
}
