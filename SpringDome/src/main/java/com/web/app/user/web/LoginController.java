package com.web.app.user.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String loginPageSubmit(Map<String, Object> map) {
		//map.put("exception", "exception");
		return "test";
	}
}
