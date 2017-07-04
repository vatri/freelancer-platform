package net.vatri.freelanceplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FreelancePlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreelancePlatformApplication.class, args);
	}


    /*@Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        IDialect dialect = (IDialect) new LayoutDialect();
        templateEngine.setDialect(dialect);
        return templateEngine;
    }*/
}
