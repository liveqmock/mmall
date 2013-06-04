<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>profile</title>
<#include "../head.ftl"/>
  <script type="text/javascript">
    $(function() {
      $("#jvForm").validate();
    });
  </script>
</head>
<body>
<div class="box-positon">
  <div class="rpos">当前位置: 全局配置 - 登录设置 - 修改</div>
  <#include "inc_opt.html" />
  <div class="clear"></div>
</div>
<div class="body-box">
<@t.form id="jvForm" action="o_login_modify.jhtml" labelWidth="15">
<@t.text width="50" colspan="1" label="登录错误次数" name="errorTimes" value=configLogin.errorTimes required="required" class="required digits" maxlength="5" style="width:70px" help="达到错误次数后显示验证码"/>
<@t.text width="50" colspan="1" label="登录错误时间" name="errorInterval" value=configLogin.errorInterval required="required" class="required digits" maxlength="5" style="width:70px" help="(分钟).超过时间重计次数"/><@t.tr/>
<@t.td colspan="2"><@t.submit value="提交"/> &nbsp; <@t.reset value="重置"/></@t.td>
</@t.form>
</div>
<#include "../common/alert_message.ftl"/>
</body>
</html>