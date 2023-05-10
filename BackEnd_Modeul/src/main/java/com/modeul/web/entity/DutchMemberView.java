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
public class DutchMemberView {
    private Long stuffId;
    private Long memberId;
    private String memberNickname;
    private String memberImage;
    private LocalDateTime participationDate;
    private String price;
}
