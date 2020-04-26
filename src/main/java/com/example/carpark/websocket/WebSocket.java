package com.example.carpark.websocket;


import com.example.carpark.javabean.IMData;
import com.example.carpark.javabean.TbAdmin;
import com.example.carpark.javabean.UserEntity;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Auther: shigc
 * @Date: 2018/12/15 11:53
 * @Description: websocket
 */
@ServerEndpoint("/websocket/{pageCode}")
@Component
public class WebSocket {

    private static final String loggerName = WebSocket.class.getName();
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    public static Map<String, List<Session>> electricSocketMap = new ConcurrentHashMap<String, List<Session>>();

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("pageCode") String pageCode, Session session) {
        session.setMaxIdleTimeout(3600000);
        List<Session> sessions = electricSocketMap.get(pageCode);
        System.out.println("pageCode====" + pageCode);
        if (null == sessions) {
            List<Session> sessionList = new ArrayList<>();
            sessionList.add(session);
            electricSocketMap.put(pageCode, sessionList);
        } else {
            sessions.add(session);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("pageCode") String pageCode, Session session) {
        if (electricSocketMap.containsKey(pageCode)) {
            electricSocketMap.get(pageCode).remove(session);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        IMData imData = new Gson().fromJson(message, IMData.class);
        UserEntity userEntity = new UserEntity();
        if (WebSocket.electricSocketMap.get(("" + imData.getTo().getId())) != null) {
            userEntity.setUsername(imData.getMine().getUsername());
            userEntity.setAvatar(imData.getMine().getAvatar());
            userEntity.setType(imData.getMine().getType());
            userEntity.setId(imData.getMine().getId());
            userEntity.setContent(imData.getMine().getContent());
            for (Session sessions : WebSocket.electricSocketMap.get(("" + imData.getTo().getId()))) {
                try {
                    sessions.getBasicRemote().sendText(new Gson().toJson(userEntity));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            userEntity.setUsername(imData.getTo().getUsername());
            userEntity.setAvatar(imData.getTo().getAvatar());
            userEntity.setType(imData.getTo().getType());
            userEntity.setId(imData.getTo().getId());
            userEntity.setContent("您好，我现在有事不在，一会再和您联系。");
            try {
                session.getBasicRemote().sendText(new Gson().toJson(userEntity));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
    }
}

