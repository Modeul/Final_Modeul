package com.modeul.web.entity;

import java.time.LocalDateTime;

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
public class ParticipationMemberView {
    private Long stuffId;
    private Long memberId;
    private LocalDateTime participationDate;
    private String memberNickname;
    private String memberEmail;
    private String memberImage;
}
