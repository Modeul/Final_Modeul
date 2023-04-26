package com.modeul.web.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modeul.web.entity.Chat;
import com.modeul.web.entity.MessageView;
import com.modeul.web.repository.MessageRepository;


@RestController
@RequestMapping
public class MessageController {

	static Map<Long, Map<String, Object>> chatBuffers = new HashMap<>();

	// chatBuffers {"449" : {"participationSet" : Set<Long>, "buffer" : List<MessageView>}}

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@Autowired
	private MessageRepository repository;

	@GetMapping("/api/chatlog")
	public String getChatLogs(long stuffId, long memberId) throws JsonMappingException, JsonProcessingException {

		Map<String, Object> chatBuffer = chatBuffers.get(stuffId);

		String chatLog;
		if (chatBuffer == null) // 채팅이 활성화 상태가 아닌 경우
		{
			chatBuffer = new HashMap<String, Object>();

			Set<Long> participationSet = new HashSet<Long>();
			participationSet.add(memberId);
			System.out.printf("접속한 MemberId : %s\n", participationSet);
			chatBuffer.put("participationSet", participationSet);

			// ---------- DB에서 chatLog를 얻어오는 서비스 실행!!!!! ----------
			chatLog = repository.findChatLogBystuffId(stuffId);
			// repository.delete(stuffId);
			// ---------- DB에서 chatLog를 얻어오는 서비스 실행!!!!! ----------

			if (chatLog == null) { // DB에 chatLog가 비어있는 경우
				repository.insert(new Chat(stuffId, ""));
				chatBuffer.put("buffer", new ArrayList<MessageView>());
			} else if (chatLog.equals("")) {
				chatBuffer.put("buffer", new ArrayList<MessageView>());
			} else { // DB에서 존재하는 chatLog를 얻어온 경우
				chatLog = chatLog.replace("\\\"", "\"");
				ObjectMapper mapper = new ObjectMapper();
				List<MessageView> list = mapper.readValue(chatLog, new TypeReference<List<MessageView>>() {
				});
				chatBuffer.put("buffer", list);
			}
		} else { // 채팅이 활성화 상태인 경우

			Set<Long> participationSet = (Set<Long>) chatBuffer.get("participationSet");
			participationSet.add(memberId);
			System.out.printf("접속한 MemberId : %s\n", participationSet);
			chatBuffer.put("participationSet", participationSet);

			ObjectMapper objectMapper = new ObjectMapper();

			chatLog = objectMapper.writeValueAsString(chatBuffer.get("buffer"));
		}

		chatBuffers.put(stuffId, chatBuffer);

		return chatLog;
	}

	// sub를 메시지를 받을 endpoint로 설정합니다.
	@MessageMapping("/chat/enterUser")
	public void enterUser(@RequestBody MessageView messageView, SimpMessageHeaderAccessor headerAccessor) {

		messageView.setContent(messageView.getSender() + "님이 입장하셨습니다.");

		Map<String, Object> chatBuffer = chatBuffers.get(messageView.getStuffId());
		List<MessageView> chatList = (List<MessageView>) chatBuffer.get("buffer");
		chatList.add(messageView);

		messagingTemplate.convertAndSend("/sub/chat/room/" + messageView.getStuffId(), messageView);
	}

	@MessageMapping("/chat/exitUser")
	public void exitUser(@RequestBody MessageView messageView, SimpMessageHeaderAccessor headerAccessor)
			throws JsonProcessingException {

		Map<String, Object> chatBuffer = chatBuffers.get(messageView.getStuffId());
		Set<Long> participationSet = (Set<Long>) chatBuffer.get("participationSet");
		participationSet.remove(messageView.getMemberId());

		messageView.setContent(messageView.getSender() + "님이 퇴장하셨습니다.");
		List<MessageView> chatList = (List<MessageView>) chatBuffer.get("buffer");
		chatList.add(messageView);

		if (participationSet.size() == 0) {

			ObjectMapper objectMapper = new ObjectMapper();

			String json = objectMapper.writeValueAsString(chatList);
			json = json.replace("\"", "\\\"");
			System.out.println("!!!!!!!!!!!BUFFER DB에 저장!!!!!!!!!!!!!");
			// System.out.println(json);
			Chat chatLog = new Chat(messageView.getStuffId(), json);
			repository.update(chatLog);

			chatBuffers.remove(messageView.getStuffId());
		} else {
			System.out.println(participationSet);
			System.out.printf("남은 활성인원수 : %s\n", participationSet.size());
		}

		
		messagingTemplate.convertAndSend("/sub/chat/room/" + messageView.getStuffId(), messageView);

	}

	// pub으로 메시지를 반환합니다.
	@MessageMapping("/chat/message")
	public void message(@RequestBody MessageView messageView) {

		Map<String, Object> chatBuffer = chatBuffers.get(messageView.getStuffId());
		List buffer = (List<MessageView>) chatBuffers.get(messageView.getStuffId()).get("buffer");

		buffer.add(messageView);
		
		chatBuffer.put("buffer", buffer);
		chatBuffers.put(messageView.getStuffId(), chatBuffer);

		messagingTemplate.convertAndSend("/sub/chat/room/" + messageView.getStuffId(), messageView);
	}
}