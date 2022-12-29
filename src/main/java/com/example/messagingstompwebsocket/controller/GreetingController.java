package com.example.messagingstompwebsocket.controller;

import com.example.messagingstompwebsocket.dto.RequestMessage;
import com.example.messagingstompwebsocket.dto.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public ResponseMessage greeting(RequestMessage req) {
		System.out.printf("req: %s%n", req.getMessage());
		return new ResponseMessage(req.getMessage());
	}

}
