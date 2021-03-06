package com.zsx.config;

import com.zsx.handler.MyHandshakeHandler;
import com.zsx.web.interceptor.MyChannelInterceptor;
import com.zsx.web.interceptor.AuthHandshakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final AuthHandshakeInterceptor authHandshakeInterceptor;

    private final MyHandshakeHandler myHandshakeHandler;
    private final MyChannelInterceptor myChannelInterceptor;

    @Autowired
    public WebSocketConfig(AuthHandshakeInterceptor authHandshakeInterceptor, MyHandshakeHandler myHandshakeHandler,
                           MyChannelInterceptor myChannelInterceptor) {
        this.authHandshakeInterceptor = authHandshakeInterceptor;
        this.myHandshakeHandler = myHandshakeHandler;
        this.myChannelInterceptor = myChannelInterceptor;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws/endpointChat").withSockJS();
        registry.addEndpoint("/chat-websocket")
                .addInterceptors(authHandshakeInterceptor)
                .setHandshakeHandler(myHandshakeHandler)
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //客户端需要把消息发送到/api/xxx地址
        registry.setApplicationDestinationPrefixes("/api");
        //服务端广播消息的路径前缀，客户端需要相应订阅/topic/yyy这个地址的消息
        registry.enableSimpleBroker("/topic");
        //给指定用户发送消息的路径前缀，默认值是/user
//        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(myChannelInterceptor);
    }
}
