package dev.infomatik.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


/**
 * Application/API that creates weather pages, json data comes from:<BR> 
 * https://www.weather.gov/documentation/services-web-api
 */
@SpringBootApplication
public class WeatherApiApplication {

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
	 public static void main(String[] args) {
		SpringApplication.run(WeatherApiApplication.class, args);
	}

}
