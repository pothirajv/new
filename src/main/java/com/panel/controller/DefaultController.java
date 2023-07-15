package com.panel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/")
public class DefaultController {

	@RequestMapping(path = { "", "/home", "/index" })
	public ModelAndView home(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView = new ModelAndView("home");
		return modelAndView;
	}

}