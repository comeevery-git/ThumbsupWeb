package com.boot.my.thumbsup.domains.Login.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boot.my.thumbsup.common.RqsAPI;
import com.boot.my.thumbsup.domains.Login.domain.RSPAUTH002;
import com.boot.my.thumbsup.domains.Login.domain.RSPAUTH003;

@Controller
@RequestMapping("/login")
public class LoginController {
 
	//@Autowired
	//UserDetailsService userService;
	@Autowired
	RqsAPI rqsApi;
	@Autowired
	PasswordEncoder passwordEncoder;

    @PostMapping(value="/userLogin")
    @ResponseBody
    public ModelAndView loginAPI(
    			ModelAndView mv,
    			String mbId,
    			String mbPwd,
    			RestTemplate restTemplate,
    			RedirectAttributes redirectAttributes,
    			HttpServletRequest request
			) {
    	System.out.println("member --- Login, API로 요청");
    	
    	//요청 url
		String url = "http://localhost:8007/auth/signin";
		//요청 카테고리
		String sendCategory = "login";
		//요청값
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("mbId", mbId);
		map.add("mbPwd", mbPwd);

		//API요청
		Map<String, Object> result_map = rqsApi.sendAPI(url, map, restTemplate, sendCategory);
		//API응답값
		ResponseEntity<RSPAUTH003> response = (ResponseEntity<RSPAUTH003>) result_map.get("response");
		//System.out.println("#응답# "+response);
		
		//token, 권한 확인
		String token = "";
		String role = "";
		if(response.getBody().getMemberToken() != null) {
			token = response.getBody().getMemberToken();
			role = response.getBody().getMemberRole();
		}
		
		//token, 권한 세팅
		HttpSession session = request.getSession();
		session.setAttribute("token", token);
		session.setAttribute("role", role);
		System.out.println("TOKEN : " + session.getAttribute("token"));
		System.out.println("ROLE : " + session.getAttribute("role"));
		
		// 응답 상태에 따른 처리
		String result = response.getBody().getSuccess();

		// 로그인 성공
		if(result.equals("success")) {
			redirectAttributes.addFlashAttribute( "msg", "환영합니다!" );
			redirectAttributes.addFlashAttribute( "loginRslt", "success_login" );
			return new ModelAndView("redirect:/index");
			
		// 로그인 실패
		} else {
			redirectAttributes.addFlashAttribute( "msg", "아이디 또는 비밀번호를 확인해주세요." );
			redirectAttributes.addFlashAttribute( "loginRslt", "fail" );
			return new ModelAndView("redirect:/login/admin_login");
			
		}

    }
    
    /*
     * 회원가입
     */
    @RequestMapping(value="/userRegister")
    @ResponseBody
    public ModelAndView registerAPI(
    			ModelAndView mv,
    			String mbId,
    			String mbPwd,
    			String mbNm,
    			String mbRrno,
    			String mbGender,
    			RestTemplate restTemplate,
    			RedirectAttributes redirectAttributes
    		) {

    	System.out.println("member --- Register, API로 요청");
    	
    	// API 데이터 요청 및 응답
		try {
			//요청 url
			String url = "http://localhost:8007/auth/signup";
			String sendCategory = "register";
			//요청값
			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("mbId", mbId);
			map.add("mbPwd", mbPwd);
			map.add("mbNm", mbNm);
			map.add("mbRrno", mbRrno);
			map.add("mbGender", mbGender);
	
			//API요청
			Map<String, Object> result_map = rqsApi.sendAPI(url, map, restTemplate, sendCategory);
			//API응답값
			ResponseEntity<RSPAUTH002> response = (ResponseEntity<RSPAUTH002>) result_map.get("response");
	
			// 응답 상태에 따른 처리
			String result = response.getBody().getSuccess();
	
			if(result.equals("success")) {
				redirectAttributes.addFlashAttribute( "msg", "가입이 완료되었습니다." );
				redirectAttributes.addFlashAttribute( "loginRslt", "success_reg" );
				return new ModelAndView("redirect:/index");
			} else {
				redirectAttributes.addFlashAttribute( "msg", "다시 시도해 주세요." );
				redirectAttributes.addFlashAttribute( "loginRslt", "fail" );
				return new ModelAndView("redirect:/index");
			}

			/* restTemplate exchange 를 이용한 방법
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			//응답데이터 가져오기
			responseDtl = response.getBody();
			System.out.println("----- 응답 DATA ----- "+responseDtl);
			// .getStatusCode = HTTP 상태코드 반환, 200:오류없이 전송성공
			// .hasBody = body유무 반환 true or false
			System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
			
			if(responseDtl.get.equals("no_data")) {
				redirectAttributes.addFlashAttribute( "loginRslt", "fail" );
				return new ModelAndView("redirect:/index");
			} else {
				redirectAttributes.addFlashAttribute( "msg", "가입이 완료되었습니다." );
				redirectAttributes.addFlashAttribute( "loginRslt", "success" );
				return new ModelAndView("redirect:/index");
			}
			 */
			
		} catch (Exception e) {
			System.out.println("API 데이터 요청 및 응답 실패");
			System.out.println("e :  "+e); 
			
		}
    	return mv;
    }

    
    /*
     * 로그아웃
     */
    @GetMapping(value="/logout")
    public String logout(HttpServletRequest request) {
    	System.out.println("Member Logout.....");
    	HttpSession session = request.getSession();
    	session.invalidate();
    	
    	return "redirect:/index";
    }

    /*
     * 비밀번호 찾기
     */
    @GetMapping("/forget_pwd")
    public String forget_pwd(Model model) {
    	return "forget_pwd";
    }
    
}
