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
public class StuffView extends Stuff {
	private Long memberId;
	private Long categoryId;
	private String categoryName;
	private String imageName;
	private int participantCount;
}
