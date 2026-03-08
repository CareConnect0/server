package smwu.heartcall.global.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.heartcall.domain.notification.entity.Notification;
import smwu.heartcall.domain.user.entity.User;
import smwu.heartcall.global.fcm.dto.FcmTokenRequestDto;
import smwu.heartcall.global.fcm.entity.FcmToken;
import smwu.heartcall.global.fcm.repository.FcmRepository;

import java.util.List;
import java.util.Optional;

@Slf4j(topic = "FCM")
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

    public void sendNotification(User receiver, Notification notification) {
        List<FcmToken> tokenList = fcmRepository.findAllByUser(receiver);
        if(!tokenList.isEmpty()) {
            for(FcmToken fcmToken : tokenList) {
                sendFcmMessage(fcmToken.getToken(), notification);
            }
        }
    }

    public void sendFcmMessage(String token, Notification notification) {
        try {
            Message fcmMessage = Message.builder()
                    .setToken(token)
                    .putData("title", notification.getTitle())
                    .putData("message", notification.getContent())
                    .build();

            String response = FirebaseMessaging.getInstance().send(fcmMessage);
            log.info("FCM 알림 전송 성공");
        } catch (FirebaseMessagingException e) {
            log.error("FCM 알림 전송 실패");
        }
    }
}
