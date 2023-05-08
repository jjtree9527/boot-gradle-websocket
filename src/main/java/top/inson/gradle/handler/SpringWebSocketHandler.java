package top.inson.gradle.handler;

import cn.hutool.core.map.MapUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import top.inson.gradle.constant.WebSocketConstant;

import java.io.IOException;
import java.util.Map;

/**
 * @author jingjitree
 * @description
 * @date 2023/4/19 14:27
 */
@Slf4j
@Component
public class SpringWebSocketHandler extends TextWebSocketHandler {

    /**
     * 存储用户id和对应的session
     */
    private static final Map<String, WebSocketSession> userMap = MapUtil.newHashMap();

    /**
     * 连接建立后触发
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("成功建立socket连接");
        String userId = (String) session.getAttributes().get(WebSocketConstant.WEB_SOCKET_USER_ID);
        userMap.put(userId, session);
        log.info("当前在线用户数量：{}", userMap.size());
    }

    /**
     * 关闭连接触发
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = (String) session.getAttributes().get(WebSocketConstant.WEB_SOCKET_USER_ID);
        log.info("关闭socket连接, userId: {}", userId);
        userMap.remove(userId);
        log.info("剩余在线用户数量：{}", userMap.size());
    }

    /**
     * 接收消息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        super.handleTextMessage(session, message);
        log.info("接收到消息内容message：{}", message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()){
            session.close();
        }
        log.info("数据传输异常，");
        String userId = (String) session.getAttributes().get(WebSocketConstant.WEB_SOCKET_USER_ID);
        userMap.remove(userId);
    }

    @Override
    public boolean supportsPartialMessages() {

        return false;
    }

    /**
     * 给指定用户发送消息
     * @param userId
     * @param message
     */
    @SneakyThrows
    public void sendMessageToUser(String userId, TextMessage message){
        if (!userMap.containsKey(userId)){
            log.info("用户不在线userId：{}", userId);
            return;
        }
        userMap.get(userId).sendMessage(message);
        log.info("用户userId：{}，消息内容message：{}", userId, message);
    }

    /**
     * 所有在线用户发送消息
     * @param message
     */
    public void sendMessageToUsers(TextMessage message){
        if (MapUtil.isEmpty(userMap)){
            log.info("暂无在线用户");
        }
        userMap.forEach((k, v) -> {
            try {
                v.sendMessage(message);
            } catch (IOException e) {
                log.info("消息发送异常", e);
            }
        });
    }

}
