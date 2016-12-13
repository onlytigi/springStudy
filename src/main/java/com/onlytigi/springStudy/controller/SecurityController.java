package com.onlytigi.springStudy.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/security")
public class SecurityController {
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);
	
	/**
	 * login page
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String loginPage(Locale locale, Model model) {
		logger.info("Login Page! The client locale is {}.", locale);
		return "security/loginPage";
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String securityError(Locale locale, Model model) {
		logger.info("Security Error Page! The client locale is {}.", locale);
		return "security/error";
	}
	
}
