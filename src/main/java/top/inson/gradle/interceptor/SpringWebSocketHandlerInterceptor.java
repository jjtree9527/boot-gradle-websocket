package top.inson.gradle.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import top.inson.gradle.constant.WebSocketConstant;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author jingjitree
 * @description
 * @date 2023/4/19 14:04
 */
@Slf4j
@Component
public class SpringWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info("拦截器之前请求，");
        if (request instanceof ServletServerHttpRequest){
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            //获取session，如果没有返回null
            HttpSession session = serverRequest.getServletRequest().getSession(false);
            if (ObjectUtil.isNotNull(session)){
                //登录时保存的用户名
                String username = (String) session.getAttribute(WebSocketConstant.WEB_SOCKET_SESSION_USERNAME);
                if (StrUtil.isNotBlank(username)){
                    attributes.put(WebSocketConstant.WEB_SOCKET_USER_ID, username);
                }
            }
        }

        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        log.info("拦截后进来了,");
        super.afterHandshake(request, response, wsHandler, ex);
    }

}
