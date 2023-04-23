package com.modeul.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")   // 연결될 엔드포인트
                .setAllowedOriginPatterns("*")
                .withSockJS();  // SocketJS를 연결한다는 설정
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // 메세지를 구독하는 요청 url(= 즉 메세지 받을 때)
        registry.enableSimpleBroker("/sub");

        // 메세지를 발행하는 요청 url(= 메세지를 보낼 때)
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
