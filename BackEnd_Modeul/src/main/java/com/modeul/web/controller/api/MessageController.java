package com.modeul.web.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.modeul.web.entity.MessageView;
import com.modeul.web.entity.MessageView.MessageType;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MessageController {
	
	static Map<Long, Map<String, Object>> chatBuffer = new HashMap<>();
	
	private final SimpMessageSendingOperations messagingTemplate;

	@GetMapping("/api/chatlog/{id}")
	public Map<String, Object> getChatLogs(@PathVariable("id") long id) {

		// 로그파일 내놔(id);

		Map<String, Object> chatData1 = new HashMap<>();
		chatData1.put("ParticipationCount", "1");
		List<MessageView> buffer = new ArrayList<>();
		MessageView mv1 = new MessageView(MessageType.TALK, 2L, "aaa", "안녕하세요제발", "1234:11:11", null, 449L, null);
		MessageView mv2 = new MessageView(MessageType.TALK, 13L, "감자탕", "안녕하세요제발222", "1234:11:12", null, 449L, null);
		buffer.add(mv1);
		buffer.add(mv2);
		chatData1.put("buffer", buffer);

		chatBuffer.put(449L, chatData1);

		return chatBuffer.get(id);
	}

	// sub를 메시지를 받을 endpoint로 설정합니다.
	@MessageMapping("/chat/enterUser")
	public void enterUser(@RequestBody MessageView messageView, SimpMessageHeaderAccessor headerAccessor)
			throws JsonProcessingException {

		// String publishMessage = (String)
		// messagingTemplate.getStringSerializer().deserialize(messageView.getContent());
		System.out.println(messageView);

		// chatBuffer.containsKey(messageView.getStuffId())

		// 방별로 map으로 buffer를 만들어 두었다가 들어올때 나갈때 io 발생시키기
		// 기존에 채팅 버퍼에 있던 채팅 내용을 input

		headerAccessor.getSessionAttributes().put("stuffId", messageView.getStuffId());

		messageView.setContent(messageView.getSender() + "님이 입장하셨습니다.");

		// String text = mapper.writeValueAsString(messageView);

		messagingTemplate.convertAndSend("/sub/chat/room/" + messageView.getStuffId(), messageView);

	}

	// pub으로 메시지를 반환합니다.
	@MessageMapping("/chat/message")
	public void message(@RequestBody MessageView messageView) {

		// System.out.println(messageView);

		// messageView.setContent(messageView.getContent());

		messagingTemplate.convertAndSend("/sub/chat/room/" + messageView.getStuffId(), messageView);
	}

}
