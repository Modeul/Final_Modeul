package com.modeul.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modeul.web.entity.Chat;
import com.modeul.web.entity.MessageView;
import com.modeul.web.entity.MessageView.MessageType;
import com.modeul.web.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {

	static Map<Long, Map<String, Object>> chatBuffers = new HashMap<>();

	@Autowired
	private MessageRepository repository;

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

	public MessageView enterUser(MessageView messageView) {

		messageView.setContent(messageView.getSender() + "님이 입장하셨습니다.");

		Map<String, Object> chatBuffer = chatBuffers.get(messageView.getStuffId());
		List<MessageView> chatList = (List<MessageView>) chatBuffer.get("buffer");
		chatList.add(messageView);

		return messageView;

	}

	public MessageView exitUser(MessageView messageView)  throws JsonProcessingException {
		
		Map<String, Object> chatBuffer = chatBuffers.get(messageView.getStuffId());
		Set<Long> participationSet = (Set<Long>) chatBuffer.get("participationSet");
		participationSet.remove(messageView.getMemberId());

		if(messageView.getType().equals(MessageType.LEAVE))
			messageView.setContent(messageView.getSender() + "님이 퇴장하셨습니다.");
		else
			messageView.setContent(messageView.getSender() + "님이 방장에 의해 강제퇴장됐습니다.");

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

		return messageView;
	}

	public MessageView defaultMessage(MessageView messageView) {

		Map<String, Object> chatBuffer = chatBuffers.get(messageView.getStuffId());
		List buffer = (List<MessageView>) chatBuffers.get(messageView.getStuffId()).get("buffer");

		buffer.add(messageView);
		
		chatBuffer.put("buffer", buffer);
		chatBuffers.put(messageView.getStuffId(), chatBuffer);

		return messageView;
	}

	@Override
	public MessageView dutchComplete(MessageView messageView) {
		String content = "정산이 완료되었습니다." + System.lineSeparator() + "정산 결과를 확인해주세요.";
		messageView.setContent(content);

		Map<String, Object> chatBuffer = chatBuffers.get(messageView.getStuffId());
		List<MessageView> chatList = (List<MessageView>) chatBuffer.get("buffer");
		chatList.add(messageView);

		return messageView;
	}
}
