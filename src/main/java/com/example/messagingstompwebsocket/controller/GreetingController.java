package com.example.messagingstompwebsocket.controller;

import com.example.messagingstompwebsocket.dto.ResponseMessage;
import com.example.messagingstompwebsocket.dto.RequestMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public ResponseMessage greeting(RequestMessage req) throws Exception {
		Thread.sleep(1000); // simulated delay
		System.out.printf("req: %s%n", req.getMessage());
		return new ResponseMessage("Hello, " + HtmlUtils.htmlEscape(req.getMessage()) + "!");
	}

}
