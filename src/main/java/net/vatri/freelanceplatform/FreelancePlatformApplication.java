package net.vatri.freelanceplatform;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.vatri.freelanceplatform.cache.Cache;
import net.vatri.freelanceplatform.cache.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;

@SpringBootApplication
public class FreelancePlatformApplication {

    public static void main(String[] args) {
		SpringApplication.run(FreelancePlatformApplication.class, args);
	}


    @Value("${redis.host}")
    private String redisHost;
    @Value("${redis.port}")
    private int redisPort;
    @Value("${redis.password}")
    private String redisPassword;
    //    @Bean
    private Jedis redisCliFactory(){
        Jedis jedis = new Jedis(redisHost, redisPort);
        jedis.auth(redisPassword);
        return jedis;
    }

    @Bean
    @Autowired
    public Cache cacheObject(ObjectMapper objectMapper){
        return new RedisCache(objectMapper, redisCliFactory());
    }


/*    @Bean
    public IDialect springSecurityDialect(){
//        SpringTemplateEngine e = new SpringTemplateEngine();
//        IDialect dialect = new LayoutDialect();
        IDialect dialect = new SpringSecurityDialect();
//        e.setDialect(dialect);
//        e.addDialect(dialect);
//        return e;
        return dialect;
    }*/

}
