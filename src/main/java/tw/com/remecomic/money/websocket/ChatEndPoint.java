package tw.com.remecomic.money.websocket;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;
import tw.com.remecomic.config.GetHttpSessionConfig;
import tw.com.remecomic.money.util.MessageUtils;
import tw.com.remecomic.money.websocket.pojo.Message;
import tw.com.remecomic.userA.model.dto.LoginUserDto;


import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/money/chat", configurator = GetHttpSessionConfig.class)
@Component
public class ChatEndPoint {

    private static final Map<String, Session> onlineUsers = new ConcurrentHashMap<>();

    private HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        LoginUserDto loginUser = (LoginUserDto) this.httpSession.getAttribute("loginUser");
        String user = loginUser.getName();

        onlineUsers.put(user, session);

        String message = MessageUtils.getMessage(true, null, getFriends());

        broadcastAllUsers(message);
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        Message msg = JSON.parseObject(message, Message.class);

        String toName = msg.getToName();
        String msgMessage = msg.getMessage();

        Session session = onlineUsers.get(toName);

        if (session != null && session.isOpen()) {
            LoginUserDto loginUser = (LoginUserDto) this.httpSession.getAttribute("loginUser");
            String user = loginUser.getName();

            String message1 = MessageUtils.getMessage(false, user, msgMessage);

            session.getBasicRemote().sendText(message1);
        }
    }

    @OnClose
    public void onClose(Session session) {
        LoginUserDto loginUser = (LoginUserDto) this.httpSession.getAttribute("loginUser");
        String user = loginUser.getName();

        onlineUsers.remove(user);

        String message = MessageUtils.getMessage(true, null, getFriends());

        broadcastAllUsers(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("WebSocket Error: " + throwable.getMessage());

        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastAllUsers(String message) {
        Set<Map.Entry<String, Session>> entries = onlineUsers.entrySet();

        for (Map.Entry<String, Session> entry :
                entries) {
            Session session = entry.getValue();

            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception ignored) {

            }
        }
    }

    public Set<String> getFriends() {
        return onlineUsers.keySet();
    }
}
