package com.modeul.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ModeulPrjApplication extends SpringBootServletInitializer {

@Override
protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	return builder.sources(ModeulPrjApplication.class);
}

	public static void main(String[] args) {
		SpringApplication.run(ModeulPrjApplication.class, args);
	}

}
