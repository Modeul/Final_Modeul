package com.modeul.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.modeul.web.entity.MessageView;
import com.modeul.web.service.MessageService;


@RestController
@RequestMapping
public class MessageController {

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@Autowired
	private MessageService service;

	@GetMapping("/api/chatlog")
	public String chatlog(long stuffId, long memberId) throws JsonMappingException, JsonProcessingException {

		return service.getChatLogs(stuffId, memberId);
	}

	// sub를 메시지를 받을 endpoint로 설정합니다.
	@MessageMapping("/chat/enterUser")
	public void enterUser(@RequestBody MessageView messageView, SimpMessageHeaderAccessor headerAccessor) {

		MessageView message = service.enterUser(messageView);

		messagingTemplate.convertAndSend("/sub/chat/room/" + messageView.getStuffId(), message);
	}

	@MessageMapping("/chat/exitUser")
	public void exitUser(@RequestBody MessageView messageView, SimpMessageHeaderAccessor headerAccessor)
			throws JsonProcessingException {

				MessageView message = service.exitUser(messageView);

		messagingTemplate.convertAndSend("/sub/chat/room/" + messageView.getStuffId(), message);
	}

	// pub으로 메시지를 반환합니다.
	@MessageMapping("/chat/message")
	public void message(@RequestBody MessageView messageView) {

		MessageView message = service.defaultMessage(messageView);

		messagingTemplate.convertAndSend("/sub/chat/room/" + messageView.getStuffId(), message);
	}

	@MessageMapping("/chat/dutchComplete")
	public void dutchComplete(@RequestBody MessageView messageView, SimpMessageHeaderAccessor headerAccessor)
			throws JsonProcessingException {

				MessageView message = service.dutchComplete(messageView);

		messagingTemplate.convertAndSend("/sub/chat/room/" + messageView.getStuffId(), message);
	}
}