package com.modeul.web.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stuff {

	private Long id;
	private String title;
	private String place;
	private LocalDateTime regDate;
	private String numPeople;
	private String url;
	private String imgurl;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime deadline;
	
	private String price;
	private String content;
	private Long memberId;
	private Long categoryId;
	private Double coordX;
	private Double coordY;
	private String dongCode;
	
	// 이미지 파일 업로드 용!
	private List<Image> imageList;
	
	// insert용
	public Stuff(String title, String place, String numPeople, 
			LocalDateTime deadline, String price, String url,
			String content, Long categoryId, List<Image> imageList, Double coordX, Double coordY, String dongCode) {
		
		this.title = title;
		this.place = place;
		this.numPeople = numPeople;
		this.deadline = deadline;
		this.price = price;
		this.url = url;
		this.content = content;
		this.categoryId = categoryId;
		this.imageList = imageList;
		this.coordX = coordX;
		this.coordY = coordY;
		this.dongCode = dongCode;
	}
	
	// update용
	public Stuff(String title, String place, String numPeople, 
	LocalDateTime deadline, String price, String url,
	String content, List<Image> imageList, Long categoryId, Long id, Double coordX, Double coordY, String dongCode) {
	
	this.title = title;
	this.place = place;
	this.numPeople = numPeople;
	this.deadline = deadline;
	this.price = price;
	this.url = url;
	this.content = content;
	this.imageList = imageList;
	this.categoryId = categoryId;
	this.id = id;
	this.coordX = coordX;
	this.coordY = coordY;
	this.dongCode = dongCode;
}
}
