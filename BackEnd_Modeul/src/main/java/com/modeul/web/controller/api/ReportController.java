package com.modeul.web.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modeul.web.entity.ReportStuff;
import com.modeul.web.service.ReportService;

@RestController
@RequestMapping("api/reports")
public class ReportController {

	@Autowired
	ReportService service;

	@GetMapping("/stuff")
	public List<ReportStuff> getReportedStuffList() {
		List<ReportStuff> list = service.getReportedStuffList(); 
		return list;
	}

	@PostMapping("/stuff")
	public String regStuffReport(@RequestBody ReportStuff reportStuff) {
		
		return service.regReportedStuff(reportStuff);

	}

	@DeleteMapping("/stuff")
	public String delStuffReport(Integer id, String c) {
		System.out.println("c=="+c);
		return service.deleteReportedStuff(id, c);
	}
}
