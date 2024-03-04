package tw.com.remecomic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@Configuration
@PropertySource("google-oauth2.properties")
@Data
public class GoogleOAuthConfig {

	@Value("${client_id}")
	private String clientId;

	@Value("${client_secret}")
	private String clientSecret;

	@Value("${redirect_uris}")
	private String redirectUri;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	};

}
