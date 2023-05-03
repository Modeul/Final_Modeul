package com.modeul.web.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.modeul.web.entity.report;
import com.modeul.web.service.ReportService;

@RestController
@RequestMapping("api")
public class ReportController {
	
	@Autowired
	ReportService service;
	
	@GetMapping("/report/lists")
	public List<report> getList(@RequestParam(name = "progress") String progress){
		
		return null;
	}
}
