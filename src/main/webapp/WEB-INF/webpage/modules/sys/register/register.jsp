<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/webpage/common/taglibs.jspf"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">

<head>
<title><spring:message code="sys.site.title"
		arguments="${platformName}" /></title>
<meta name="keywords"
	content="<spring:message code="sys.site.keywords" arguments="${platformName}"/>">
<meta name="description"
	content="<spring:message code="sys.site.description" arguments="${platformName}"/>">

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="Thu, 19 Nov 1900 08:52:00 GMT">
<!--Loading bootstrap css-->
<link type="text/css" rel="stylesheet"
	href="${staticPath}/vendors/jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.min.css">
<html:css
	name="favicon,bootstrap,font-awesome,animate,pace,iCheck,toastr,bootstrapvalidator" />
<!--Loading style-->
<link type="text/css" rel="stylesheet"
	href="${staticPath}/uadmin/css/themes/style1/orange-blue.css"
	class="default-style">
<link type="text/css" rel="stylesheet"
	href="${staticPath}/uadmin/css/themes/style1/orange-blue.css"
	id="theme-change" class="style-change color-change">
<link type="text/css" rel="stylesheet"
	href="${staticPath}/uadmin/css/style-responsive.css">
</head>

<body id="signin-page">
	<div class="page-form">
		<form id="registerForm" method="post" class="form">
			<div class="header-content">
				<h1>
					<spring:message code="sys.register.submit.label" />
				</h1>
			</div>
			<div class="body-content">
				<p>JeeWeb欢迎您！</p>
				<div class="form-group">
					<div class="input-icon right">
						<i class="fa fa-user"></i> <input name="username" id="username"
							class="form-control" placeholder="手机号" required="">
					</div>
				</div>
				<div class="form-group">
					<div class="input-icon right">
						<i class="fa fa-key"></i> <input name="password" type="password"
							id="password" class="form-control"
							placeholder="<spring:message code="sys.login.password.placeholder"/>"
							required="">
					</div>
				</div>
				<div class="form-group">
					<div class="input-icon right">
						<input name="authCode" type="text" style="border: 1px solid #ccc;"
							placeholder="请输入验证码" id="authCode" required="">
						<button type="button" class="btn btn-success"
							style="margin-left: 30px; width: 35%;" onclick="sendCode(this)">获取验证码</button>
					</div>
				</div>
				<div class="form-group" style="margin-top: 10px;">
					<div class="pull-right">
						<button type="button" class="btn btn-success"
							onclick="saveRegister()">
							<spring:message code="sys.register.submit.label" />
							&nbsp; <i class="fa fa-chevron-circle-right"></i>
						</button>
						<button type="reset" class="btn btn-success">
							<spring:message code="sys.register.reset.label" />
							&nbsp; <i class="fa fa-chevron-circle-right"></i>
						</button>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
			<input type="hidden" id="userKey" />
		</form>
	</div>

	<html:js
		name="jquery,bootstrap,iCheck,bootstrap-hover-dropdown,toastr,bootstrapvalidator" />
	<script type="text/javascript">
		var clock = '';
		var nums = 60;
		var btn;
		function sendCode(thisBtn) {
			btn = thisBtn;
			btn.disabled = true; //将按钮置为不可点击
			$.ajax({
				type : "POST",
				url : "${adminPath}/register/getSms",
				data : "phone=" + $("#username").val() + "",
				success : function(data) {
					$("#userKey").val(data.data.phoneKey);
				},
				error : function(error) {
					showToast(error, "error");
				}
			});
			btn.innerText = nums + '秒后重新获取';
			clock = setInterval(doLoop, 1000); //一秒执行一次
		}
		function doLoop() {
			nums--;
			if (nums > 0) {
				btn.innerText = nums + '秒后重新获取';
			} else {
				clearInterval(clock); //清除js定时器
				btn.disabled = false;
				btn.innerText = '获取验证码';
				nums = 60; //重置时间
			}
		}
		//注册用户
		function saveRegister() {
			var username = $("#username").val();
			var password = $("#password").val();
			var authCode = $("#authCode").val();
			var userKey = $("#userKey").val();
			var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
			if (!myreg.test(username)) {
				showToast("请输入有效的手机号码", "error")
				return;
			}
			$.ajax({
				type : "POST",
				url : "${adminPath}/register/saveRegister",
				data : "username=" + username + "&password=" + password
						+ "&authCode=" + authCode + "&userKey="+userKey+"",
				success : function(data) {
					showToast(data.msg, "success");
				},
				error : function(error) {
					showToast(error, "error");
				}
			});
		}
		function showToast(msg, shortCutFunction) {
			toastr.options = {
				"closeButton" : true,
				"debug" : false,
				"progressBar" : true,
				"positionClass" : "toast-bottom-right",
				"onclick" : null,
				"showDuration" : "400",
				"hideDuration" : "1000",
				"timeOut" : "7000",
				"extendedTimeOut" : "1000",
				"showEasing" : "swing",
				"hideEasing" : "linear",
				"showMethod" : "fadeIn",
				"hideMethod" : "fadeOut"
			}
			toastr[shortCutFunction](msg, "提示");
		}
	</script>
</body>

</html>