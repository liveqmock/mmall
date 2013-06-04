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
  <div class="rpos">当前位置: 个人资料</div>
  <form class="ropt">
  </form>
  <div class="clear"></div>
</div>
<div class="body-box">
<#assign invalidPassword>密码错误</#assign>
<@t.form id="jvForm" action="o_profile.jhtml">
<@t.td label="用户名">${user.username!?html}</@t.td><@t.tr/>
<@t.password label="原密码" name="origPwd" required="true" maxlength="32" vld="{required:true,remote:'v_checkPwd.jhtml',messages:{remote:'${invalidPassword}'}}"/>
<@t.password label="新密码" id="newPwd" name="newPwd" maxlength="32" help="不修改请留空"/>
<@t.password label="确认密码" maxlength="32" equalTo="#newPwd" help="不修改请留空"/>
<@t.text label="真实姓名" name="realName" value=user.ossUserExt.realName maxlength="100"/>
<@t.text label="电子邮箱" name="email" value=user.email class="email" maxlength="100"/>
<@t.td><@t.submit value="提交"/> &nbsp; <@t.reset value="重置"/></@t.td>
</@t.form>
</div>
<#include "../common/alert_message.ftl"/>
</body>
</html>