package com.modeul.web.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ReportStuff {
	 Integer id;
	 LocalDateTime regDate;
	 Integer stuffId;
	 Integer memberId;
	 String detail;
//	 Integer progress;
//	 String reply;
}
