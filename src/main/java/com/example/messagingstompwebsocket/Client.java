package com.example.messagingstompwebsocket;

import com.example.messagingstompwebsocket.config.MyStompSessionHandler;
import com.example.messagingstompwebsocket.dto.RequestMessage;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

public class Client {

    private static final String URL = "ws://localhost:8080/gs-guide-websocket";

    private StompSession stompSession;
    private MyStompSessionHandler sessionHandler;

    public Client() throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        sessionHandler = new MyStompSessionHandler(URL);
        var future = stompClient.connectAsync(URL, sessionHandler);
        stompSession = future.get();
    }

    public void sendMessage(String requestMessage) {
        stompSession.send(sessionHandler.getSendTo(), new RequestMessage(requestMessage));
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Client c1 = new Client();
        c1.sendMessage("123");
        c1.sendMessage("456");
        c1.sendMessage("789");
    }

}
