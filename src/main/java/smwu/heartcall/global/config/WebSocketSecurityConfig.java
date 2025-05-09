//package smwu.heartcall.global.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.support.MessageHeaderAccessor;
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
//import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
//import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSocketSecurity
//public class WebSocketSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain springWebSocketSecurity(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/ws/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .csrf(csrf -> csrf.disable()) // WebSocket은 일반적으로 CSRF 비활성화
//                .build();
//    }
//
//    @Bean
//    public AuthorizationManager<Message<?>> messageAuthorizationManager() {
//        return (authentication, message) -> {
//            MessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message.getMessage(), SimpMessageHeaderAccessor.class);
//
//            if (accessor == null) return new AuthorizationDecision(false);
//
//            String destination = accessor.getDestination();
//
//            if (destination == null) return new AuthorizationDecision(false);
//
//            // 여기에 목적지 별 권한 설정
//            if (destination.startsWith("/app/") || destination.startsWith("/topic/")) {
//                return new AuthorizationDecision(authentication.isAuthenticated());
//            }
//
//            return new AuthorizationDecision(false);
//        };
//    }
//
//    @Bean
//    public MessageSecurityMetadataSource messageSecurityMetadataSource(
//            AuthorizationManager<Message<?>> messageAuthorizationManager) {
//        return new MessageSecurityMetadataSource(messageAuthorizationManager);
//    }
//}
//
