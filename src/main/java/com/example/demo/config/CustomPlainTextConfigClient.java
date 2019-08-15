package com.example.demo.config;

import static org.springframework.cloud.config.client.ConfigClientProperties.TOKEN_HEADER;

import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.pivotal.spring.cloud.service.config.PlainTextConfigClient;

@Component
public class CustomPlainTextConfigClient implements PlainTextConfigClient {
	private final ConfigClientProperties configClientProperties;

	private RestTemplate restTemplate;

	protected CustomPlainTextConfigClient(final OAuth2ProtectedResourceDetails resource,
			final ConfigClientProperties configClientProperties) {
		this.restTemplate = new OAuth2RestTemplate(resource);
		this.configClientProperties = configClientProperties;
	}

	@Override
	public Resource getConfigFile(String application, String label, String profile) {

		if (label == null) {
			label = configClientProperties.getLabel();
		}
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(configClientProperties.getUri()[0])
				.pathSegment(label).pathSegment(profile + ".yml");

		RequestEntity.HeadersBuilder<?> requestBuilder = RequestEntity.get(urlBuilder.build().toUri());
		if (StringUtils.hasText(configClientProperties.getToken())) {
			requestBuilder.header(TOKEN_HEADER, configClientProperties.getToken());
		}
		ResponseEntity<Resource> forEntity = restTemplate.exchange(requestBuilder.build(), Resource.class);
		return forEntity.getBody();
	}

	@Override
	public Resource getConfigFile(String path) {
		return getConfigFile(null, null, path);
	}
}
