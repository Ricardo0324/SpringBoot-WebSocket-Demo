package com.wyq.websocket.controller;

import cn.hutool.json.JSONObject;
import com.wyq.websocket.component.WebSocketServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName ServerController
 * @Description: //TODO
 * @Author wyq
 * @Date 2022/5/5 17:59
 */
@RestController
public class ServerController {
    @RequestMapping(value = "/server", method = {RequestMethod.POST, RequestMethod.GET})
    public void server(HttpServletRequest request) throws IOException {
        try {
            String msg = request.getParameter("msg");
            String user = request.getParameter("user");
            //获取用户的webSocket对象
            WebSocketServer ws = new WebSocketServer();
            //封装JSON
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user", user);
            jsonObject.put("msg", msg);
            String message = jsonObject.toString();
            //发送消息
            ws.onMessage(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
