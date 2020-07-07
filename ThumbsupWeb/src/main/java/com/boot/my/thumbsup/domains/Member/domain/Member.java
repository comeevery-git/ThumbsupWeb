package com.boot.my.thumbsup.domains.Member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "tb_member")
public class Member {
 
	public Member() {}

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer mbIdx;

    // 아이디 
	@Column(length = 100)
    private String mbId;

    // 비밀번호 
	@Column(length = 100)
    private String mbPwd;

    // 이름 
	@Column(length = 100)
    private String mbNm;

    // 전화번호 
	@Column(length = 100)
    private String mbTel;

    // 생년월일 
	@Column(length = 100)
    private String mbRrno;

    // 성별 
	@Column(length = 100)
    private String mbGender;

    // 대표 이미지 
	@Column(length = 100)
    private Integer mbImg;

    // 탈퇴유무 
	@Column(length = 100)
    private String mbDelyn;

    // 등록일 
	@Column(length = 100)
    private String mbRegdate;

    // 수정일 
	@Column(length = 100)
    private String mbUpddate;
   
    // 마지막 접속일 
	@Column(length = 100)
    private String mbAccessdate;


    
}
