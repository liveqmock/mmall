<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>admin-edit</title>
<#include "../head.ftl"/>
  <script type="text/javascript">
    $(function() {
      $("#jvForm").validate();
    }
  </script>
</head>
<body>
<div class="box-positon">
  <div class="rpos">当前位置: 管理员管理 - 编辑</div>
  <form class="ropt">
    <input type="button" value="返回列表" onclick="history.back();" class="return-button"/>
  </form>
  <div class="clear"></div>
</div>
<div class="body-box">
<@t.form id="jvForm" action="o_modify.jhtml" labelWidth="12">
<@t.td width="50" colspan="1" label="用户名">${ossAdmin.username}</@t.td>
<@t.text width="50" colspan="1" label="电子邮箱" name="email" value=ossAdmin.email size="30" maxlength="100" class="email"/><@t.tr/>
<@t.password width="50" colspan="1" label="密码" id="password" name="password" maxlength="100" help="不修改请留空"/>
<@t.password width="50" colspan="1" label="确认密码" equalTo="#password" help="不修改请留空"/><@t.tr/>
<@t.text width="50" colspan="2" label="等级" name="rank" value=ossAdmin.rank style="width:70px" required="true" class="required digits" maxlength="5" max=currRank help="越大等级越高"/><@t.tr/>
<@t.radio width="50" colspan="1" label="禁用" name="disabled" value=ossAdmin.disabled list={"true":"是","false":"否"} required="true"/>
<@t.td width="50" colspan="1" label="登录次数">${ossAdmin.loginCount}</@t.td><@t.tr/>
<@t.td width="50" colspan="1" label="注册">${ossAdmin.registerTime?string('yyyy-MM-dd HH:mm:ss')}
  &nbsp; ${ossAdmin.registerIP}</@t.td>
<@t.td width="50" colspan="1" label="最后登录">${ossAdmin.lastLoginTime?string('yyyy-MM-dd HH:mm:ss')}
  &nbsp; ${ossAdmin.lastLoginIP}</@t.td><@t.tr/>
<@t.text width="50" colspan="1" label="真实姓名" name="realName" value=ossAdmin.ossUserExt.realName maxlength="100"/>
<@t.radio width="50" colspan="1" label="性别" name="gender" value=ossAdmin.ossUserExt.gender list={"true":"男","false":"女","":"保密"}/><@t.tr/>
<@t.radio width="50" colspan="1" label="受限管理员" name="selfAdmin" value=ossAdmin.selfAdmin list={"true":"是","false":"否"} required="true" help="只能管理自己的数据"/>
<@t.radio width="50" colspan="1" label="只读管理员" name="viewonlyAdmin" value=ossAdmin.viewonlyAdmin list={"true":"是","false":"否"} required="true" help="只能查看数据"/><@t.tr/>
<@t.checkboxlist colspan="2" label="角色" name="roleIds" valueList=roleIds list=roleList listKey="roleId" listValue="roleName"/><@t.tr/>
<@t.td colspan="2">
<@t.hidden name="userId" value=ossAdmin.userId/>
<@t.submit value="提交"/> &nbsp; <@t.reset value="重置"/>
</@t.td>
</@t.form>
</div>
</body>
</html>