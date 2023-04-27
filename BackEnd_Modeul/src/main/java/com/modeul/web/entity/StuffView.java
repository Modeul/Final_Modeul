package com.modeul.web.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


// 상속해서 Constructor를 만들기 위해서는 부모 Entity에서도 기본 생성자가 필요하다.

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
