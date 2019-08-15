package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.BrandingService;

@RestController
@RequestMapping("/rest/v1/branding")
public class BrandingController {
	@Autowired
	BrandingService brandingService;

	@GetMapping("/config/{appName}/{client}/{environment}")
	String fetchConfig(@PathVariable String appName, @PathVariable String client, @PathVariable String environment)
			throws IOException {
		return brandingService.fetchConfigurationWithEnvironementContent(appName, client, environment);
	}

	@GetMapping("/config/{appName}/{client}")
	String fetchConfig(@PathVariable String appName, @PathVariable String client) throws IOException {
		return brandingService.fetchConfigurationContent(appName, client);
	}

	@GetMapping("/message/{appName}/{client}/{lang}")
	String fetchMessageContent(@PathVariable String appName, @PathVariable String client, @PathVariable String lang)
			throws IOException {
		return brandingService.fetchMessageContent(appName, client, lang);
	}
}
