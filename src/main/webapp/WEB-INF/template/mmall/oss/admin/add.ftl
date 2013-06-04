<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>admin-add</title>
<#include "../head.ftl"/>
  <script type="text/javascript">
    $(function () {
      $("#jvForm").validate();
    });
  </script>
</head>
<body>
<div class="box-positon">
  <div class="rpos">当前位置: 管理员管理 - 添加</div>
  <form class="ropt">
    <input type="submit" value="返回列表" onclick="this.form.action='v_list.jhtml';"
           class="return-button"/>
  </form>
  <div class="clear"></div>
</div>
<div class="body-box">
<#assign usernameExist>用户名已被使用</#assign>
<@t.form id="jvForm" action="o_create.jhtml" labelWidth="12">
  <@t.text width="50" colspan="1" label="用户名" name="username" required="true" maxlength="100" vld="{required:true,username:true,remote:'v_check_username.jhtml',messages:{remote:'${usernameExist}'}}"/>
  <@t.text width="50" colspan="1" label="电子邮箱" name="email" size="30" maxlength="100" class="email"/><@t.tr/>
  <@t.password width="50" colspan="1" label="密码" id="password" name="password" maxlength="100" class="required" required="true"/>
  <@t.password width="50" colspan="1" label="确认密码" equalTo="#password" required="true"/><@t.tr/>
  <@t.text width="50" colspan="2" label="等级" name="rank" value=currRank-1 style="width:70px" required="true" class="required digits" maxlength="5" max=currRank help="越大等级越高"/><@t.tr/>
  <@t.text width="50" colspan="1" label="真实姓名" name="realName" maxlength="100"/>
  <@t.radio width="50" colspan="1" label="性别" name="gender" list={"true":"男","false":"女","":"保密"}/><@t.tr/>
  <@t.radio width="50" colspan="1" label="受限管理员" name="selfAdmin" value="false" list={"true":"是","false":"否"} required="true" help="只能管理自己的数据"/>
  <@t.radio width="50" colspan="1" label="只读管理员" name="viewonlyAdmin" value="false" list={"true":"是","false":"否"} required="true" help="只能查看数据"/><@t.tr/>
  <@t.checkboxlist colspan="2" label="角色" name="roleIds" list=roleList listKey="roleId" listValue="roleName"/><@t.tr/>
  <@t.td colspan="2"><@t.submit value="提交"/> &nbsp; <@t.reset value="重置"/></@t.td>
</@t.form>
</div>
</body>
</html>