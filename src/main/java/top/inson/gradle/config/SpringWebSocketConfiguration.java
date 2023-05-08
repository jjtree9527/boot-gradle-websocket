package top.inson.gradle.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import top.inson.gradle.handler.SpringWebSocketHandler;
import top.inson.gradle.interceptor.SpringWebSocketHandlerInterceptor;

/**
 * @author jingjitree
 * @description
 * @date 2023/4/19 13:59
 */
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class SpringWebSocketConfiguration implements WebSocketConfigurer {
    private final SpringWebSocketHandler springWebSocketHandler;
    private final SpringWebSocketHandlerInterceptor springWebSocketHandlerInterceptor;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(springWebSocketHandler, "/websocket/server")
                .addInterceptors(springWebSocketHandlerInterceptor).setAllowedOrigins("*");

        registry.addHandler(springWebSocketHandler, "/sockjs/server").setAllowedOrigins("*")
                .addInterceptors(springWebSocketHandlerInterceptor).withSockJS();
    }


}
