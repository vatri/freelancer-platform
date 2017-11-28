package net.vatri.freelanceplatform;

import net.vatri.freelanceplatform.validators.UserValidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Validator;

@SpringBootApplication
public class FreelancePlatformApplication {

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

}
