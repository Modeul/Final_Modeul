package com.modeul.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class StuffController {
	
	@GetMapping("index")
	public String index() {
		return "forward:/index.html";
	}
	
	@GetMapping("login")
	public String login() {
		return "forward:/index.html";
	}
	
	@GetMapping("member/stuff/list")
	public String msl() {
		return "forward:/index.html";
	}
	
	@GetMapping("admin/stuff/list")
	public String asl() {
		return "forward:/index.html";
	}
	
	@GetMapping("admin/member/list")
	public String aml() {
		return "forward:/index.html";
	}
	
}
