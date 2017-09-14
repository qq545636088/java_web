package cn.jeeweb.modules.sys.controller;

import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.jeeweb.core.common.controller.BaseController;
import cn.jeeweb.core.model.AjaxJson;
import cn.jeeweb.core.utils.sms.data.SmsResult;
import cn.jeeweb.core.utils.sms.sender.SmsSender;
import cn.jeeweb.core.utils.sms.sender.TelnetSender;
import cn.jeeweb.modules.sys.utils.UserUtils;

/**
 * 用户注册contorller
 * 
 * @author liuxiaolong
 * @date 2017年9月14日--下午3:17:42
 * @Description
 */
@Controller
@RequestMapping("${admin.url.prefix}/register")
public class RegisterController extends BaseController {

	@RequestMapping("/toRegister")
	public ModelAndView toRegisterUI() {
		System.out.println("进入register 页面");

		return new ModelAndView("modules/sys/register/register");
	}

	/***
	 * 保存用户注册信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/saveRegister")
	public void saveRegister(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxJson = new AjaxJson();
		String username = request.getParameter("userName");
		String password = request.getParameter("passWord");
		String authCode = request.getParameter("authCode");
		String userKey = request.getParameter("userKey");
		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password) && !StringUtils.isEmpty(authCode)) {
			String obj = (String) UserUtils.getCache(userKey);
			if (!StringUtils.isEmpty(obj)) {
				if (authCode.equals(obj)) {
					
				} else {
					ajaxJson.fail("验证码不正确！");
				}
			} else {
				ajaxJson.fail("未获取到验证码！");
			}
		} else {
			ajaxJson.fail("用户名或密码不能为空！");
		}
	}

	/**
	 * 获取验证码
	 * 
	 * @return
	 */
	@RequestMapping("/getSms")
	public AjaxJson getSms(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxJson = new AjaxJson();
		String phone = request.getParameter("phone");
		if (!StringUtils.isEmpty(phone)) {
			ajaxJson.fail("手机号不能为空！");
		}
		TelnetSender t = new TelnetSender();
		String randomNum= randomNumber(6);
		cn.jeeweb.core.utils.sms.data.SmsTemplate template = cn.jeeweb.core.utils.sms.data.SmsTemplate.newTemplateByContent(randomNum);
		try {
			SmsResult sms = t.send(phone, template, null);
			String phoneKey ="user"+UUID.randomUUID();
			UserUtils.putCache(phoneKey,randomNum );
			JSONObject json = new JSONObject();
			json.put("authCode", randomNum);
			json.put("phoneKey", phoneKey);
			ajaxJson.setData(json);
			ajaxJson.setMsg("获取验证码成功！");
		} catch (Exception e) {
			ajaxJson.fail("获取验证码失败！");
		}
		return ajaxJson;
	}

	public String randomNumber(int num) {
		StringBuffer sb = new StringBuffer();
		Integer number[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
		Random random = new Random();
		for (int i = 0; i < num; i++) {
			sb.append(number[random.nextInt(10)]);
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
}
