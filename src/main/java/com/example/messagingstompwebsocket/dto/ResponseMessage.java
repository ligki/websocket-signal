package com.example.messagingstompwebsocket.dto;

public class ResponseMessage {

	private String message;

	public ResponseMessage() {
	}

	public ResponseMessage(String content) {
		this.message = content;
	}

	public String getMessage() {
		return message;
	}

}
