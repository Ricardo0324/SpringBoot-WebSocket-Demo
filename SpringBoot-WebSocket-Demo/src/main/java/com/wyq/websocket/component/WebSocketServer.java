package com.wyq.websocket.component;

import cn.hutool.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName WebSocketServer
 * @Description: //TODO
 * @Author wyq
 * @Date 2022/5/5 17:47
 */
@Component
@ServerEndpoint("/webSocket/{username}")
public class WebSocketServer {
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) throws IOException {
        if (username == null) {
            return;
        }
        clients.put(username, session);
        System.out.println("用户：" + username + "已连接到websocke服务器");
    }

    @OnClose
    public void onClose(@PathParam("username") String username) throws IOException {
        clients.remove(username);
        System.out.println("用户：" + username + "已离开websocket服务器");
    }

    @OnMessage
    public void onMessage(String json) throws IOException {
        System.out.println("前端发送的信息为：" + json);
        JSONObject jsonObject = new JSONObject(json);
        String user = jsonObject.getStr("user");
        String msg = jsonObject.getStr("msg");
        Session session = clients.get(user);
        //如果这个好友在线就直接发给他
        if (session != null) {
            sendMessageTo(msg,session);
        } else {
            System.out.println("对方不在线，对方名字为：" + user);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    //单发给某人
    public void sendMessageTo(String message, Session session) throws IOException {
        session.getBasicRemote().sendText(message);
    }
}
