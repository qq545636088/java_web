<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/webpage/common/taglibs.jspf"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>用户信息</title>
<meta name="decorator" content="single" />
<html:css name="iCheck,Validform" />
<html:css name="bootstrap-fileinput" />
</head>

<body class="gray-bg">
	<form:form id="userinfoForm" modelAttribute="data" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<table
			class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<tbody>
				<tr>
					<td class="width-15  text-right"><label>姓名:</label></td>
					<td class="width-35">${data.realname}</td>
					<td class="width-15  text-right">用户名:</label></td>
					<td class="width-35">${data.username}</td>
				</tr>
				<tr>
					<td class="width-15  text-right"><label>邮箱:</label></td>
					<td class="width-35">${data.email}</td>
					<td class="width-15  text-right">联系电话:</label></td>
					<td class="width-35">${data.phone}</td>
				</tr>
				<tr>
					<td class="width-15  text-right"><label>用户角色:</label></td>
					<td class="width-35"><form:checkboxes path="roleIdList" nested="false" items="${roleIdList}" defaultvalue="${roleIdList}" itemLabel="name" itemValue="id" htmlEscape="false" cssClass="i-checks required" /></td>
				</tr>
				<tr>
					<td class="width-15  text-right"><label>组织结构:</label></td>
					<td class="width-35">${organizationNames}</td>
				</tr>
			</tbody>
		</table>
	</form:form>
</body>
</html>