package com.boot.my.thumbsup;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    
    @GetMapping("/index")
    public String index(Model model) {

    	return "index";
    }
    
}
