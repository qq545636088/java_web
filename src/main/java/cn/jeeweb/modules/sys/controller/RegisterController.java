package cn.jeeweb.modules.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.jeeweb.core.common.controller.BaseController;

/**
 * 用户注册contorller
 * @author liuxiaolong
 * @date   2017年9月14日--下午3:17:42
 * @Description
 */
@Controller
@RequestMapping("${admin.url.prefix}/register")
public class RegisterController extends BaseController{

	@RequestMapping("/toRegister")
	public ModelAndView toRegisterUI(){
		System.out.println("进入register 页面");
		return new ModelAndView("modules/sys/register/register");
	}

	/***
	 * 保存用户注册信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/saveRegister")
	public void saveRegister(HttpServletRequest request , HttpServletResponse response){
		String username =request.getParameter("userName");
		String password = request.getParameter("passWord");
		String authCode = request.getParameter("authCode");
		if(!StringUtils.isEmpty(username)&& !StringUtils.isEmpty(password)&&!StringUtils.isEmpty(authCode)){
			
		}
	}
}
