package com.modeul.web.service;

import com.modeul.web.entity.Chat;

public interface MessageService {

    int addChatLog(Chat chat);

    Chat getChatLogBystuffId(Long stuffId);
    
}
