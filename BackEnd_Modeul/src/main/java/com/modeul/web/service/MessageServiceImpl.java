package com.modeul.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeul.web.entity.Chat;
import com.modeul.web.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService{
    
    @Autowired
    private MessageRepository repository;

    @Override
    public int addChatLog(Chat chat) {
        return repository.insert(chat);
    }

    @Override
    public Chat getChatLogBystuffId(Long stuffId) {
        return repository.findChatLogBystuffId(stuffId);
    }

    

    

}
