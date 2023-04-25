package com.modeul.web.repository;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.Chat;

@Mapper
public interface MessageRepository {
    
	int insert(Chat chat);

    Chat findChatLogBystuffId(Long stuffId);
}
