package com.modeul.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Crawling {

	private Long id;
	private String title;
	private String price;
	private String contenturl;
	private String imgurl;
	private Long categoryId;
	
	
	// insert용
	public Crawling(String title, String price,String contenturl, String imgurl,Long categoryId) {
		
		this.title = title;
		this.price = price;
		this.contenturl = contenturl;
		this.imgurl = imgurl;
		this.categoryId = categoryId;
	}
	
	// update용
	public Crawling(String title, String price,String contenturl, String imgurl,Long categoryId, Long id) {
	
	this.title = title;
	this.price = price;
	this.contenturl = contenturl;
	this.imgurl = imgurl;
	this.categoryId = categoryId;
	this.id = id;
}
}
