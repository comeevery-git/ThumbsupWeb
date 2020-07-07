package com.boot.my.thumbsup.domains.Member.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

//메인 컨트롤러, RestController 어노테이션 ?
@Controller
public class MemberController {
	/*
	 * @Autowired private MemberService memberService;
	 */
	
	@RequestMapping("/index_login")
    public String indexLogin(Model model) {
    	return "index_login";
    }
	
	@GetMapping("/index")
    public String index(Model model, @RequestHeader HttpHeaders header) {
		System.out.println(header);
		//token 확인
		if(header.get("token") != null) {
			System.out.println("ㅇ"+header.get("token"));
			
		}
		
    	return "index";
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
	


/*
	@GetMapping(path="/add")
	public @ResponseBody String addNewUser (@RequestParam String name,
			@RequestParam String email) {
		int id = 1004;
		User n = new User(id, name, email);
		userService.userInsert(n);
		
		return "Saved";
		
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUser(){
		return userService.userSelect();
	}

*/
}
