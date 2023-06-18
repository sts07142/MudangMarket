package gachon.mudang.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Registers the "/ws" WebSocket endpoint that the clients will use to connect to our WebSocket server.
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Designates "/pub" as a prefix for messages that are bound for @MessageMapping methods.
        registry.setApplicationDestinationPrefixes("/pub");
        // Designates the "/sub" prefix for messages that should be routed back to the client-side.
        registry.enableSimpleBroker("/sub");
    }
}
