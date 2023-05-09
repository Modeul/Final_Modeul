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
public class DutchView {
    private Long memberId;
    private Long stuffId;
    private String stuffTitle;
    private LocalDateTime stuffDeadline;
    private String stuffPrice;
    private Long stuffManagerId;
    private String stuffManagerNickname;
    private String stuffManagerImage;
    private String price;
    private LocalDateTime date;
}
