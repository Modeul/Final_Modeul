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
	private String categoryName;
	
	// insert용
	public Crawling(String title, String price,String contenturl, String imgurl,Long categoryId, String categoryName) {
		
		this.title = title;
		this.price = price;
		this.contenturl = contenturl;
		this.imgurl = imgurl;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
	
	// update용
	public Crawling(String title, String price,String contenturl, String imgurl,Long categoryId, String categoryName, Long id) {
	
	this.title = title;
	this.price = price;
	this.contenturl = contenturl;
	this.imgurl = imgurl;
	this.categoryId = categoryId;
	this.categoryName = categoryName;
	this.id = id;
}
}
