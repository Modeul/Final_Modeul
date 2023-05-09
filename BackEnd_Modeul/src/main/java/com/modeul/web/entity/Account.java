package com.modeul.web.entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    private Long id;
    private String bankName;
    private String number;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime regDate; 
    private Long memberId; 
}
