package com.example.messagingstompwebsocket.config;
import com.example.messagingstompwebsocket.dto.ResponseMessage;
import com.example.messagingstompwebsocket.dto.RequestMessage;
import lombok.Data;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import java.lang.reflect.Type;

@Data
public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private String url;
    private String topic;
    private String sendTo;
    public MyStompSessionHandler(String url) {
        this.url = url;
        this.topic = "%s/topic/greetings".formatted(url);
        this.sendTo = "%s/app/hello".formatted(url);
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
        logger.info("Received : " + headers);
        ResponseMessage msg = (ResponseMessage) payload;
        logger.info("Received : " + msg.getMessage());
    }

}
