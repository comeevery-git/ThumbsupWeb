package com.boot.my.thumbsup.domains.Login.domain;

import lombok.Getter;
import lombok.Setter;

/*
 * 회원가입
 */

@Getter
@Setter
public class RSPAUTH002 implements RqsInterData{
	// 결과값
	private String success;

	// 메세지
	private String msg;
    
    
    
    
}
