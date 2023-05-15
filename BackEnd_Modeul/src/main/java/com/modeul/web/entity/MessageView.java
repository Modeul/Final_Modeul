package com.modeul.web.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageView {

	// 중요!!
    // 메세지 타입을 enum 클래스로 상수 열거형으로 설정!
    // 메세지 타입에 따라서 동작하는 구조가 달라진다!!

    public enum MessageType{
        ENTER, TALK, LEAVE, DUTCH, BANISH;
    }

    private MessageType type; // 메세지 타입
    private Long memberId;  // 회원 아이디 FK
    private String sender;  // 채팅을 보낸 사람
    private String content; // 메세지
    private String sendDate;    // 채팅 발송 시간
    private Long participationId; // 참여 테이블 FK
    private Long stuffId;         // 채팅방과 FK
    private String memberImage; // 회원 프로필 사진
}
