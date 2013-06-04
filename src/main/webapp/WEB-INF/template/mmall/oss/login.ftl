<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>Administrator's Control Panel</title>
<#include "head.ftl"/>
  <script type="text/javascript">
    if (top != this) {
      top.location = this.location;
    }
    $(function () {
      $("#username").focus();
      $("#jvForm").validate();
    });
    
    $(document).ready(function(){
    	$("#getVerifyCodeButton").click(function(){
    		//var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    		var emailReg = /^([a-zA-Z0-9_\-\.])+@ningmenghai\.com$/;
    		var email = $("#insideNetworkEmail").val();
    		if(!email || !emailReg.test(email)){
	    		alert('请输入有效的公司邮箱地址！');
	    		return;
    		}
		  	$("#getVerifyCodeButton").hide();
		  	$("#verifyCodeLoading").show();
		  	//debugger;
		  	$.ajax({
				url:"verifyCode2Email.jhtml",
				type:"GET",
				async:false,
				data:{"email":email},
				dataType:"json",
				success:function(data){
					if(data.success == true){
						$("#verifyCodeLoading").text(data.msg);
					}else{
//						alert(data.msg);
						$("#verifyCodeLoading").hide();
						$("#getVerifyCodeButton").show();
					}
				},
				error:function(xmlHttpRequest, textStatus, errorThrown){
					if(xmlHttpRequest){
						alert('status:'+xmlHttpRequest.status +','+xmlHttpRequest.statusText);
					}else{
						alert("error");
					}
					$("#verifyCodeLoading").hide();
					$("#getVerifyCodeButton").show();
				}
			});
    	});
	});
    
  </script>
  <style type="text/css">
    body {
      margin: 0;
      padding: 0;
      font-size: 12px;
      background: url(${base}/mmall/static/oss/images/login/bg.jpg) top repeat-x;
    }

    .input {
      width: 150px;
      height: 17px;
        border: 1px solid #404040;
        border-right-color: #D4D0C8;
        border-bottom-color: #D4D0C8;
    }
  </style>
</head>
<body>
<form id="jvForm" action="login.jhtml" method="post">
<#if returnUrl??><input type="hidden" name="returnUrl" value="${returnUrl}"/></#if>
  <table width="850" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="200">&nbsp;</td>
    </tr>
    <tr>
      <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="423" height="280" valign="top">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><img src="${base}/mmall/static/oss/images/login/ltop.jpg"/></td>
                </tr>
                <tr>
                  <td><img src="${base}/mmall/static/oss/images/login/llogo.jpg"/></td>
                </tr>
              </table>
            </td>
            <td width="40" align="center" valign="bottom">
              <img src="${base}/mmall/static/oss/images/login/line.jpg" width="23" height="450"/>
            </td>
            <td valign="top">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="90" align="center" valign="bottom">
                    <img src="${base}/mmall/static/oss/images/login/ltitle.jpg"/>
                  </td>
                </tr>
                <tr>
                  <td>
                    <div style="text-align:center;">
                    <#if errors??>
                      <ul>
                        <#list errors as error>
                          <li>${error}</li></#list>
                      </ul>
                    </#if>
                    </div>
                    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="5">
                      <tr>
                        <td width="91" height="40" align="right"><strong> 用户名：</strong></td>
                        <td width="211">
                          <input type="input" id="username" name="username" vld="{required:true}" maxlength="100"
                                 class="input"/>
                        </td>
                        <td></td>
                      </tr>
                      <tr>
                        <td height="40" align="right"><strong>密码：</strong></td>
                        <td>
                          <input name="password" type="password" id="password" maxlength="32" vld="{required:true}"
                                 class="input"/>
                        </td>
                      </tr>
                    <#if errorRemaining?? && errorRemaining <= 0>
                      <tr>
                        <td colspan="2" align="center">
                          <img src="${base}/mmall/oss/captcha.svl" onclick="this.src='${base}/mmall/oss/captcha.svl?d='+new Date()"
                               alt="看不清楚?请点击"/>
                        </td>
                      </tr>
                      <tr>
                        <td height="40" align="right"><strong>验证码：</strong></td>
                        <td><input name="captcha" type="text" id="captcha" vld="{required:true}" class="input"/></td>
                      </tr>
                  	</#if>
                    <#if isOutsideNetwork??>
                    	<#if isOutsideNetwork == "Y" >
	                    <tr>
	                    	<td height="40" align="right"><strong>校验码：</strong></td>
	                    	<td>
	                      		<input name="outsideNetworkVerifyCode" type="text" id="outsideNetworkVerifyCode" maxlength="6" class="input"/>
	                    	</td>
	                  	</tr>
	                  	<tr>
	                    	<td height="40" align="right"><strong>email：</strong></td>
	                    	<td>
	                      		<input name="insideNetworkEmail" value="xxx@ningmenghai.com" type="text" id="insideNetworkEmail" maxlength="64" class="input"/>
	                    	</td>
	                    	<td><span id="verifyCodeLoading" style="display:none;">emailing...</span><input type="button" value="获取校验码" id="getVerifyCodeButton"/></td>
	                  	</tr>
                  		</#if>
                    </#if>
                      <tr>
                        <td height="40" colspan="2" align="center">
                          <input type="image" src="${base}/mmall/static/oss/images/login/login.jpg" name="submit"/>
                          &nbsp; &nbsp; <img name="reg" style="cursor: pointer"
                                             src="${base}/mmall/static/oss/images/login/reset.jpg"
                                             onclick="document.forms[0].reset()"/>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</form>
<#include "common/alert_message.ftl"/>
</body>
</html>