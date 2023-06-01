package com.modeul.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.modeul.web.entity.MessageView;

public interface MessageService {

		String getChatLogs(long stuffId, long memberId) throws JsonMappingException, JsonProcessingException ;

		MessageView enterUser(MessageView messageView);
		MessageView exitUser(MessageView messageView) throws JsonProcessingException;
		MessageView defaultMessage(MessageView messageView);

        MessageView dutchComplete(MessageView messageView);
}
