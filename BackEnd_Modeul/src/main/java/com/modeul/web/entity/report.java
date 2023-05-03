package com.modeul.web.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class report {
	 Integer Id;
	 LocalDateTime regDate;
	 Integer stuffId;
	 Integer memberId;
	 String detail;
	 char progress;
	 String reply;
}
