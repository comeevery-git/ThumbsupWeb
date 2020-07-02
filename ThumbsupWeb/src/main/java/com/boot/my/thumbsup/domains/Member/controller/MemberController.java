package com.boot.my.thumbsup.domains.Member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//메인 컨트롤러, RestController 어노테이션 ?

@Controller
public class MemberController {
	/*
	 * @Autowired private MemberService memberService;
	 */
	@Controller
	@RequestMapping("/index_login")
	public class IndexLoginController {
		@RequestMapping(method=RequestMethod.GET) public String index() {
			return "index_login";
		}
	}
	
	@Controller
	@RequestMapping("/index")
	public class Index {
		@RequestMapping(method=RequestMethod.GET) public String index() {
			return "index";
		}
	}
	@Controller
	@RequestMapping("/first")
	public class first {
		@RequestMapping(method=RequestMethod.GET) public String index() {
			return "first";
		}
	}
	@Controller
	@RequestMapping("/elements")
	public class elements {
		@RequestMapping(method=RequestMethod.GET) public String index() {
			return "elements";
		}
	}
	@Controller
	@RequestMapping("/generic")
	public class generic {
		@RequestMapping(method=RequestMethod.GET) public String index() {
			return "generic";
		}
	}
	
	
	@Controller
	@RequestMapping("/detail1")
	public class detail1 {
		@RequestMapping(method=RequestMethod.GET) public String index() {
			return "detail1";
		}
	}
	
	@Controller
	@RequestMapping("/detail2")
	public class detail2 {
		@RequestMapping(method=RequestMethod.GET) public String index() {
			return "detail2";
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
