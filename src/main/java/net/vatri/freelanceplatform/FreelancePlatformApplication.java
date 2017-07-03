package net.vatri.freelanceplatform;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;

import org.thymeleaf.processor.AbstractProcessor;

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
