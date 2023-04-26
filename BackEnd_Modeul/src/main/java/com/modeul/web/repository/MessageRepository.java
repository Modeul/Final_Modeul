package com.modeul.web.repository;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.Chat;

@Mapper
public interface MessageRepository {

	int insert(Chat chat);

	int update(Chat chat);
	int delete(Long stuffId);
	// Chat findChatLogBystuffId(Long stuffId);
	String findChatLogBystuffId(Long stuffId);
}
