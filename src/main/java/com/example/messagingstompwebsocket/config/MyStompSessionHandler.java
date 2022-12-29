package com.example.messagingstompwebsocket.config;

import com.example.messagingstompwebsocket.dto.ResponseMessage;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Getter
@Setter
public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private String topic;
    private String sendTo;
    public MyStompSessionHandler() {
        this.topic = "/topic/greetings";
        this.sendTo = "/app/hello";
    }

    private Logger logger = LogManager.getLogger(MyStompSessionHandler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("New session established : " + session.getSessionId());
        session.subscribe(topic, this);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return ResponseMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        ResponseMessage msg = (ResponseMessage) payload;
        System.out.printf("Session %s: %s%n", headers.getSession(), msg.getMessage());
    }

}
