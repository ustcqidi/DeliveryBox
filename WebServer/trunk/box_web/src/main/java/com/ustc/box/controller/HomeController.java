package com.ustc.box.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.code.kaptcha.Constants;
import com.ustc.box.form.LoginForm;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Log logger = LogFactory.getLog(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		return "home";
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String login() {

		return "login";
	}

	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return "redirect:/login.do";
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String processSubmit(HttpServletRequest request,
			HttpServletResponse response, @Valid LoginForm loginForm,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {
		// 1. 登录检查
		if (result.hasErrors()) {
			model.addAttribute("message", result.getFieldError().getDefaultMessage());
			return "login";
		}

		String captchaId = (String) request.getSession().getAttribute(
				Constants.KAPTCHA_SESSION_KEY);
		if (StringUtils.isEmpty(captchaId)
				|| !captchaId.equalsIgnoreCase(loginForm.getCheckcode())) {
			model.addAttribute("message", "验证码有误，请重试");
			return "login";
		}

		String encodePwd =loginForm.getPassword();
		UsernamePasswordToken token = new UsernamePasswordToken(
				(loginForm.getUsername().toLowerCase()), encodePwd, true);
		try {
			SecurityUtils.getSubject().login(token);
		} catch (AuthenticationException e) {
			logger.info(e.getMessage());
			model.addAttribute("message", "用户名或密码错误");
			return "login";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/menu.do", method = RequestMethod.GET)
	public String menu() {

		return "menu";
	}

	@RequestMapping(value = "/dashboard.do", method = RequestMethod.GET)
	public String dashboard() {

		return "dashboard";
	}

	@RequestMapping(value = "/top.do", method = RequestMethod.GET)
	public String top() {

		return "top";
	}

	@RequestMapping(value = "/switchImg.do", method = RequestMethod.GET)
	public String switchImg() {

		return "centerBar";
	}

	@RequestMapping(value = "/topBar.do", method = RequestMethod.GET)
	public String topBar() {

		return "topBar";
	}

	@RequestMapping(value = "/bottom.do", method = RequestMethod.GET)
	public String bottom() {

		return "bottom";
	}
	

}
