package cn.jeeweb.core.utils.sms.sender;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import cn.jeeweb.core.utils.PropertiesUtil;
import cn.jeeweb.core.utils.sms.data.SmsResult;
import cn.jeeweb.core.utils.sms.data.SmsTemplate;
import cn.jeeweb.core.utils.sms.sender.SmsSender;

public class TelnetSender extends SmsSender {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	// 产品名称:云通信短信API产品,开发者无需替换
	static final String product = "Dysmsapi";
	// 产品域名,开发者无需替换
	static final String domain = "dysmsapi.aliyuncs.com";

	public SendSmsResponse sendSms(String phone, String content) throws ClientException {
		PropertiesUtil p = new PropertiesUtil(getConfigname());
		// TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
		final String accessKeyId = p.getString("sms.telnet.accessKeyId");
		final String accessKeySecret = p.getString("sms.telnet.accessKeySecret");
		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		// 必填:待发送手机号
		request.setPhoneNumbers(phone);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName("枭龙科技");
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(p.getString("sms.telnet.templetId"));
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam("{\"messagecode\":\"" + content + "\"}");

		// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");

		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		// request.setOutId("yourOutId");

		// hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

		return sendSmsResponse;
	}

	// public static QuerySendDetailsResponse querySendDetails(String bizId)
	// throws ClientException {
	//
	// // 可自助调整超时时间
	// System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
	// System.setProperty("sun.net.client.defaultReadTimeout", "10000");
	//
	// // 初始化acsClient,暂不支持region化
	// IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
	// accessKeyId, accessKeySecret);
	// DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product,
	// domain);
	// IAcsClient acsClient = new DefaultAcsClient(profile);
	//
	// // 组装请求对象
	// QuerySendDetailsRequest request = new QuerySendDetailsRequest();
	// // 必填-号码
	// request.setPhoneNumber("18306043073");
	// // 可选-流水号
	// request.setBizId(bizId);
	// // 必填-发送日期 支持30天内记录查询，格式yyyyMMdd
	// SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
	// request.setSendDate(ft.format(new Date()));
	// // 必填-页大小
	// request.setPageSize(10L);
	// // 必填-当前页码从1开始计数
	// request.setCurrentPage(1L);
	//
	// // hint 此处可能会抛出异常，注意catch
	// QuerySendDetailsResponse querySendDetailsResponse =
	// acsClient.getAcsResponse(request);
	//
	// return querySendDetailsResponse;
	// }

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String name() {
		// TODO Auto-generated method stub
		return "超级管理员";
	}

	@Override
	public SmsResult send(String phone, SmsTemplate smsTemplate, String... datas) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String[] phones = phone.split(",");
		for (String singlePhone : phones) {
//			String data = null;
//			if (datas.length > 0) {
//				for (int i = 0; i < datas.length; i++) {
//					data = datas[0];
//				}
//			}
			try {
				SendSmsResponse sendSms = sendSms(singlePhone, smsTemplate.getTemplateContent());
				logger.info("telnetSms result=" + sendSms);
				if ("OK".equals(sendSms.getCode())) {
					resultMap.put("code", "2");
					resultMap.put("msg", "发送成功");
					resultMap.put("smsid", sendSms.getBizId());
					logger.info("短信发送成功");
				} else {
					logger.error("短信发送失败");
				}
			} catch (ClientException e) {
				e.printStackTrace();
				logger.error("短信发送失败");
			}
		}
		return mapToResult(resultMap);
	}

	private SmsResult mapToResult(Map<String, Object> result) {
		SmsResult requestResult = new SmsResult();
		requestResult.setSuccess(Boolean.FALSE);
		requestResult.setSenderName(name());
		if (result != null) {
			requestResult.setCode(result.get("code") + "");
			requestResult.setMsg(result.get("msg") + "");
			requestResult.setSmsid(result.get("smsid") + "");
			if ("2".equals(result.get("code"))) {
				requestResult.setSuccess(Boolean.TRUE);
			}
		}
		return requestResult;
	}
}
