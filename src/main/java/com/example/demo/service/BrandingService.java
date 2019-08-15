package com.example.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.example.demo.config.CustomPlainTextConfigClient;

@Service
public class BrandingService {

	enum BRANDING_TYPE {
		CONFIG, MESSAGE, STYLE
	}

	@Autowired
	private CustomPlainTextConfigClient configClient;

	/**
	 * Url formation to fetch content from bitbucket -
	 * /{label}/{application}-{profile}.yml where the {label} placeholder refers to
	 * a Git branch like develop,feature(_)common, release(_)release or master and
	 * profile is combination of application name , message and lang id
	 */
	public String fetchMessageContent(String appName, String client, String lang) throws IOException {

		String platformMessage = appName + "-" + BRANDING_TYPE.MESSAGE.toString().toLowerCase() + "-" + lang;
		String clientSpecificMessage = appName + "-" + BRANDING_TYPE.MESSAGE.toString().toLowerCase() + "-" + lang + "-"
				+ client;
		String[] brandingFileNameArray = new String[] { appName, platformMessage, clientSpecificMessage };

		String path = String.join(",", brandingFileNameArray);
		System.out.print(path);
		String label = "master"; // replace with environment based value if you want to keep different value

		InputStream input = configClient.getConfigFile(appName, label, path).getInputStream();
		return StreamUtils.copyToString(input, Charset.defaultCharset());
	}

	public String fetchConfigurationWithEnvironementContent(String appName, String client, String environment)
			throws IOException {

		String platformConfig = appName + "-" + BRANDING_TYPE.CONFIG.toString().toLowerCase();
		String environmentSpecificPlatformConfig = appName + "-" + BRANDING_TYPE.CONFIG.toString().toLowerCase() + "-"
				+ environment;
		String clientAndEnvirmentSpecificConfig = appName + "-" + BRANDING_TYPE.CONFIG.toString().toLowerCase() + "-"
				+ client + "-" + environment;

		String[] brandingFileNameArray = new String[] { appName, platformConfig, environmentSpecificPlatformConfig,
				clientAndEnvirmentSpecificConfig };

		String path = String.join(",", brandingFileNameArray);
		System.out.println(path);
		String label = "master"; // replace with environment based value if you want to keep different value

		InputStream input = configClient.getConfigFile(appName, label, path).getInputStream();
		return StreamUtils.copyToString(input, Charset.defaultCharset());
	}

	public String fetchConfigurationContent(String appName, String client) throws IOException {

		String platformConfig = appName + "-" + BRANDING_TYPE.CONFIG;
		String clientSpecificConfig = appName + "-" + BRANDING_TYPE.CONFIG + "-" + client;

		String[] brandingFileNameArray = new String[] { appName, platformConfig, clientSpecificConfig };

		String path = String.join(",", brandingFileNameArray);
		String label = "master"; // replace with environment based value if you want to keep different value
		System.out.print(path);
		InputStream input = configClient.getConfigFile(appName, label, path).getInputStream();
		return StreamUtils.copyToString(input, Charset.defaultCharset());
	}

	public String fetchStyleContent(String appName, String client) throws IOException {

		String styleFile = appName + "-" + BRANDING_TYPE.STYLE.toString().toLowerCase();
		String clientSpecificStyle = appName + "-" + BRANDING_TYPE.STYLE.toString().toLowerCase() + "-" + client;

		String[] brandingFileNameArray = new String[] { styleFile, clientSpecificStyle };

		String path = String.join(",", brandingFileNameArray);
		System.out.println(path);
		String label = "master"; // replace with environment based value if you want to keep different value

		InputStream input = configClient.getConfigFile(appName, label, path).getInputStream();
		return StreamUtils.copyToString(input, Charset.defaultCharset());
	}
}
