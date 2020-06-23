package com.boot.my.thumbsup.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	/* js, css 경로설정 */
	@Configuration
	public class WebConfig implements WebMvcConfigurer {
	    @Override
	    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/**/*.js").addResourceLocations("/ui/static/");
	        registry.addResourceHandler("/**/*.css").addResourceLocations("/ui/static/");
	    }
	}
	
	//권한설정
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter,CsrfFilter.class);
		http
			.authorizeRequests()
				//.antMatchers("/resources/**").permitAll().anyRequest().permitAll()
				//.antMatchers("/admin/**").permitAll()
				.antMatchers("/**").permitAll()
				.and()
				.logout()
				.logoutSuccessUrl("/index");
		/*
			.formLogin()
				.defaultSuccessUrl("/index")
				.permitAll()
				.and()
			.logout();
		*/
	}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/templates/**");
        web.ignoring().antMatchers("/resources/**").anyRequest();
    }
    
}
