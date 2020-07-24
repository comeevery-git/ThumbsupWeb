package com.boot.my.thumbsup.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.boot.my.thumbsup.domains.Login.domain.RSPAUTH002;
import com.boot.my.thumbsup.domains.Login.domain.RSPAUTH003;
import com.boot.my.thumbsup.domains.Member.domain.RSPHM001;

/*
 * 
 * ThumbsupAPI 요청
 * 2020-07-17 강수희
 * 
 * 요청 보낼 주소 : String url
 * 요청 값 세팅 : MultiValueMap<String, String>
 * 요청이용방법 : RestTemplate 이용
 * 요청구분 : String sendCategory
 * 
 */

@Component
public class RqsAPI {

	public Map<String, Object> sendAPI(String url, MultiValueMap<String, String> map, RestTemplate restTemplate, String sendCategory) {
		
		// API 데이터 요청 및 응답
		HttpHeaders headers = new HttpHeaders();
		//Charset utf8 = Charset.forName("UTF-8");
		//MediaType mediaType = new MediaType("application","json",utf8);
		//headers.setContentType(mediaType);
		System.out.println("sendAPI #headers# "+headers);
	
		//요청 url EX)
		//url = "http://localhost:8007/auth/signin_m";
		
		//요청값 EX)
		//MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		//map.add("adminId", adminId);
		//map.add("adminPwd", adminPwd);
	
		//요청 Entity
		HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		System.out.println("sendAPI #entity# "+entity);
		
		//응답 Entity
		Map<String, Object> result_map = new HashMap<String, Object>();
		
		if (sendCategory == "login") {
			System.out.println("로그인으로 요청합니다.");
			ResponseEntity<RSPAUTH003> response = restTemplate.postForEntity(url, entity, RSPAUTH003.class);
			result_map.put("response", response);
			System.out.println("로그인이 처리되었습니다.");
		}
		//가입
		if (sendCategory == "register") {
			System.out.println("가입으로 요청합니다.");
			ResponseEntity<RSPAUTH002> response = restTemplate.postForEntity(url, entity, RSPAUTH002.class);
			result_map.put("response", response);
			System.out.println("가입이 처리되었습니다.");
		}
		//홈페이지 공지사항
		if (sendCategory == "notice") {
			System.out.println("홈페이지 공지사항으로 요청합니다.");
			System.out.println("왜 안되는 걸까 ? 11111111" );
			ResponseEntity<RSPHM001> response = restTemplate.exchange(url, HttpMethod.GET, entity, RSPHM001.class);

			System.out.println("왜 안되는 걸까 ? 2222222" );
			
			result_map.put("response", response);
			System.out.println("홈페이지 공지사항 요청이 처리되었습니다.");
		}
		
		return result_map;
		
	}
		

}
