package com.mgmtp.blog.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mgmtp.blog.model.PostDTO;

import com.mgmtp.blog.service.PostService;
import com.mgmtp.blog.service.SessionService;

@Controller
public class BaseController {

	@Autowired
	PostService postService;
	
	@Autowired
	SessionService sessionService;

	@RequestMapping("/")
	public String showIndex(Model model, HttpServletRequest request, HttpServletResponse response) {
		List<PostDTO> posts = postService.findAll();
		model.addAttribute("posts", posts);

		// check session
		Cookie loginCookie = sessionService.checkLoginCookie(request);
		if (loginCookie != null)
			sessionService.checkSessionId(loginCookie.getValue());
		else
			loginCookie = new Cookie("SESSIONID", sessionService.getRandomSessionId());
		response.addCookie(loginCookie);
		return "index";
	}

}
