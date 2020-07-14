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

	// 권한설정
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
	        	//접근가능 경로
	        	.antMatchers("/index","/login/**", "/logout", "/error").permitAll()
	        	//js, css
	        	.antMatchers("/resources/**").permitAll()
	        	//그 외 Member 권한필수 - ROLE_MEMBER
		        .antMatchers("/**").hasRole("MEMBER")
	        	//위 소스는 소스에 Role이 박혀있어야 해서 권한을 동적으로 생성하기가 어려움.
	        	//아래는 계정에 해당된 URL 권한 리스트를 가져와서 제어
		        //.anyRequest().access("@authorizationChecker.check(request, authentication)")
        		//제일 마지막에 지정된 이외의 경우 모두 인증받아야한다고 표기 ==> 미인증 시 지정한 loginForm으로 이동
		        .anyRequest()
        		.authenticated();
	     http
	     	.formLogin()
	        	.loginPage("/index")
	        	.loginProcessingUrl("/login/userLogin")
	        	.usernameParameter("mbId")
	        	.passwordParameter("mbPwd");
    }

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

}
