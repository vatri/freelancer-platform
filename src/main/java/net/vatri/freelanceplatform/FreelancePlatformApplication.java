package net.vatri.freelanceplatform;

import net.vatri.freelanceplatform.validators.UserValidator;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class FreelancePlatformApplication{

	public static void main(String[] args) {
		SpringApplication.run(FreelancePlatformApplication.class, args);
	}

	// @Value("${redis.host}")
	// private String redisHost;
	// @Value("${redis.port}")
	// private int redisPort;
	// @Value("${redis.password}")
	// private String redisPassword;
	// // @Bean
	// private Jedis redisCliFactory(){
	// Jedis jedis = new Jedis(redisHost, redisPort);
	// jedis.auth(redisPassword);
	// return jedis;
	// }

	// @Bean
	// @Autowired
	// public Cache cacheObject(ObjectMapper objectMapper){
	// return new RedisCache(objectMapper, redisCliFactory());
	// }
	
	@Value("${freelancer.locale.default}")
	private String defaultLocale;

	@Bean
	public Validator userValidator() {
		return new UserValidator();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * @Bean public IDialect springSecurityDialect(){ // SpringTemplateEngine e =
	 * new SpringTemplateEngine(); // IDialect dialect = new LayoutDialect();
	 * IDialect dialect = new SpringSecurityDialect(); // e.setDialect(dialect); //
	 * e.addDialect(dialect); // return e; return dialect; }
	 */
	
	
	@Bean
	public LocaleResolver localeResolver() {

		SessionLocaleResolver slr = new SessionLocaleResolver();
	    
	    Locale locale = new Locale(defaultLocale);
	    slr.setDefaultLocale(locale);

	    return slr;
	}
	
	@Bean
    public WebMvcConfigurer configurer () {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addInterceptors (InterceptorRegistry registry) {
                LocaleChangeInterceptor l = new LocaleChangeInterceptor();
                l.setParamName("locale");
                registry.addInterceptor(l);
            }
        };
    }
 

}
