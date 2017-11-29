package net.vatri.freelanceplatform.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{

    	httpSecurity
	        .authorizeRequests()
	        .antMatchers("/", "/css/**", "/favicon.ico").permitAll()
	        .antMatchers("/login", "/register", "/job", "/job/view/*").permitAll()
	        .anyRequest().authenticated()
	        .and()
	        .formLogin()
	        	.loginPage("/login").usernameParameter("email").failureUrl("/login-error")
	            .and().logout()
	            	.logoutUrl("/logout")
	            	.logoutSuccessUrl("/")
	                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

    }

}
