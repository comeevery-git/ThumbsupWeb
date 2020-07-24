package com.boot.my.thumbsup.domains.Member.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boot.my.thumbsup.common.RqsAPI;
import com.boot.my.thumbsup.domains.Member.domain.RSPHM001;

//메인 컨트롤러, RestController 어노테이션 ?
@Controller
public class MemberController {
	/*
	 * @Autowired private MemberService memberService;
	 */
	@Autowired
	RqsAPI rqsApi;
	
	@RequestMapping("/index_login")
    public String indexLogin(Model model) {
    	return "index_login";
    }
	
	/*
	 * 회원 index(로그인/가입) 이동
	 */
   	@GetMapping("/index")
    public String index(Model model, HttpServletRequest request) {
		//token 확인
		HttpSession session = request.getSession();
		if(session.getAttribute("token") != null) {
			//System.out.println("###"+session.getAttribute("token"));
			
		} else {
			System.out.println("NOT TOKEN");
		}
    	return "index";
    }
   	
   	/*
   	 * 홈페이지 공지사항
   	 */
   	@GetMapping("/notice")
    public String notice(
    		Model model,
    		HttpServletRequest request,
    		RestTemplate restTemplate,
    		RedirectAttributes redirectAttributes) {
	    System.out.println("BackOffice --- notice, API로 요청");
	    
	    try {
			//요청 url
			String url = "http://localhost:8007/service/webNotice";
			//요청 카테고리
			String sendCategory = "notice";
			//요청값
			HttpSession session = request.getSession();
			String token = session.getAttribute("token").toString();
			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("token", token);
			
			//API요청
			Map<String, Object> result_map = rqsApi.sendAPI(url, map, restTemplate, sendCategory);
			//API응답값
			ResponseEntity<RSPHM001> response = (ResponseEntity<RSPHM001>) result_map.get("response");
			System.out.println("#응답# "+response);
	
			// 응답 상태에 따른 처리
			//공지사항 확인
			String result = response.getBody().getSuccess();
			System.out.println("NOTICE : " + result);
	
			/*
			if(result.equals("success")) {
				redirectAttributes.addFlashAttribute( "msg", "가입이 완료되었습니다." );
				redirectAttributes.addFlashAttribute( "loginRslt", "success_reg" );
				return new Model("redirect:thumbsup/notice");
			} else {
				redirectAttributes.addFlashAttribute( "msg", "다시 시도해 주세요." );
				redirectAttributes.addFlashAttribute( "loginRslt", "fail" );
				return new ModelAndView("redirect:thumbsup/notice");
			}
			*/
			
		} catch (Exception e) {
			System.out.println("API 데이터 요청 및 응답 실패");
			System.out.println("e :  "+e); 
			
		}
    	//return mv;
    	return "thumbsup/notice";
    	
    }
   	
	
	
	
	
	@RequestMapping("/elements")
    public String elements(Model model) {
    	return "/elements";
    }
	@RequestMapping("/generic")
    public String generic(Model model) {
    	return "/generic";
    }

	@RequestMapping("/detail1")
    public String detail1(Model model) {
    	return "/detail1";
    }
	@RequestMapping("/detail2")
    public String detail2(Model model) {
    	return "/detail2";
    }
	


}
