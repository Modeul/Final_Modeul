package com.modeul.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    public enum MessageType{
        ENTER, TALK, LEAVE, DUTCH;
    }

    private MessageType type; // 메세지 타입
    private Long id;      // 메세지 id
    private String sender;  // 채팅을 보낸 사람
    private String content; // 메세지
    private String sendDate;    // 채팅 발송 시간
    private Long participationId; // 참여 테이블 FK
}
