package weixin.popular.api;

import org.springframework.web.client.RestTemplate;

import weixin.popular.client.RestTemplateClient;

public abstract class BaseAPI {
	protected static final String BASE_URI = "https://api.weixin.qq.com/cgi-bin";

	protected RestTemplate restTemplate = RestTemplateClient.restTemplate();
  	
}
