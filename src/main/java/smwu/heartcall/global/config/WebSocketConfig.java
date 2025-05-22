package smwu.heartcall.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import smwu.heartcall.global.websocket.StompHandShakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final StompHandShakeInterceptor interceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub"); // 메시지 구독 주소
        config.setApplicationDestinationPrefixes("/pub"); // 메시지 전송 주소

        // /pub/chats/rooms/{roomId}/messages 로 메시지를 보냄
        // /sub/chats/rooms/{roomId} 로 메시지가 전달됨
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // /ws로 도착하는 것은 stomp 통신으로 인식
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*"); // 프론트 도메인
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(interceptor);
    }
}
