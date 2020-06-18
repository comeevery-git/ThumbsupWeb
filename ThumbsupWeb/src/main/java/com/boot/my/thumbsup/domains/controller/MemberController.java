package com.boot.my.thumbsup.domains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.boot.my.thumbsup.domains.service.MemberService;

//메인 컨트롤러, RestController 어노테이션 ?

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Controller
	@RequestMapping("/index")
	public class HelloController {
		@RequestMapping(method=RequestMethod.GET) public String index() {
			return "index";
		}
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
