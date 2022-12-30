package com.example.messagingstompwebsocket.client;

import com.example.messagingstompwebsocket.config.MyStompSessionHandler;
import com.example.messagingstompwebsocket.dto.RequestMessage;
import lombok.Getter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.whispersystems.libsignal.SessionCipher;
import org.whispersystems.libsignal.state.PreKeyBundle;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@Getter
public class Client {

    private static final String URL = "ws://localhost:8080/gs-guide-websocket";

    private StompSession stompSession;
    private MyStompSessionHandler sessionHandler;
    private SignalService signalService;
    private SessionCipher sessionCipher;

    public Client(int id) throws Exception {
        initWebsocketClient();
        initSignal(id);
    }

    private void initSignal(int id) throws Exception {
        this.signalService = new SignalService(id);
    }

    private void initWebsocketClient() throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        sessionHandler = new MyStompSessionHandler();
        var future = stompClient.connectAsync(URL, sessionHandler);
        stompSession = future.get();
    }

    /**
     * Invoke this method to start chatting
     *
     * @param recipientId     id of recipient
     * @param deviceId        device id of recipient
     * @param retrievedPreKey PreKey from server
     * @throws Exception
     */
    private void initSessionCipher(String recipientId, int deviceId, PreKeyBundle retrievedPreKey) throws Exception {
        assert signalService != null;
        this.sessionCipher = signalService.buildSignalSession(this, recipientId, deviceId, retrievedPreKey);
    }

    public void sendMessage(String message) throws Exception {
        var encryptedMsg = signalService.encryptMessage(sessionCipher, message);
        stompSession.send(sessionHandler.getSendTo(), new RequestMessage(new String(encryptedMsg.serialize())));
    }

    public void disconnect() {
        stompSession.disconnect();
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object

        int id = 0;
        while (id == 0) {
            System.out.println("Enter id (number): ");
            String idInput = scanner.nextLine();
            try {
                id = Integer.parseInt(idInput);
                if (id > 0) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Enter a number");
            }
        }

        Client c1 = new Client(id);
        c1.initSessionCipher("2", 1, null);

        while (true) {
            System.out.println("Enter chat (Enter q! to end): ");
            String message = scanner.nextLine();  // Read user input
            if ("q!".equals(message)) {
                break;
            }
            c1.sendMessage(message);
        }
        c1.disconnect();
    }

}
