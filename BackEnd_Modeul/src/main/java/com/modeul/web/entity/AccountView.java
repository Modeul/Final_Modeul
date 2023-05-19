package com.modeul.web.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountView {
    private Long id;
    private String bankName;
    private String number;
    private LocalDateTime regDate;
    private Long memberId;
    private Long stuffId;
    private String memberName;
    private String memberNickname;
}
