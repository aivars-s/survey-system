package aivars.survey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.beans.BeanProperty;

@SpringBootApplication
public class SurveySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurveySystemApplication.class, args);
    }

    @Bean
	public WebMvcConfigurer webMvcConfigurer() {
    	return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedMethods("DELETE", "GET", "POST", "PUT")
					.allowedOrigins("*");
			}
		};
	}

}
