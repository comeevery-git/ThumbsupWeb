package com.boot.my.thumbsup.common;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

/*
	@Override
	public void configure(WebSecurity web) throws Exception {
		// 허용되어야 할 경로들
		// web.ignoring().antMatchers("/templates/**");
		// web.ignoring().antMatchers("/resources/assets/**").anyRequest();
	}
*/

	// 권한설정 - Jwt Token으로 인증
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
        .csrf().disable()
    	.authorizeRequests()
        	//접근가능 경로
        	.antMatchers("/index", "/login/**", "/error").permitAll()
        	//js, css
        	.antMatchers("/resources/**").permitAll()
	        //.antMatchers("/**").hasRole("MEMBER")
        	
        	//위 소스는 소스에 Role이 박혀있어야 해서 권한을 동적으로 생성하기가 어려움.
        	//아래는 계정에 해당된 URL 권한 리스트를 가져와서 제어
        	.anyRequest().access("@authorizationChecker.check(request, authentication)");

		 //권한 없을 시 login form 경로
	     http
	     	.formLogin()
	        	.loginPage("/index");
	    }

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

}
