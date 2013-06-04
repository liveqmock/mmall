<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>admin-list</title>
<#include "../head.ftl"/>
  <script type="text/javascript">
    function getTableForm() {
      return document.getElementById('tableForm');
    }
    function optDelete() {
      if (Kxa.checkedCount('ids') <= 0) {
        alert("请选择您要操作的数据");
        return;
      }
      if (!confirm("您确定删除吗？")) {
        return;
      }
      var f = getTableForm();
      f.action = "o_remove.jhtml";
      f.submit();
    }
  </script>
</head>
<body>
<div class="box-positon">
  <div class="rpos">当前位置: 管理员管理 - 列表</div>
  <form class="ropt">
    <input class="add" type="submit" value="添加" onclick="this.form.action='v_add.jhtml';"/>
  </form>
  <div class="clear"></div>
</div>
<div class="body-box">
  <form id="tableForm" method="post">
    <input type="hidden" name="pageNo" value="${pageNo!}"/>
  <@t.table value=pagination;ossUser,i,has_next><#rt/>
  <@t.column title="<input type='checkbox' onclick='Kxa.checkbox(\"ids\",this.checked)'/>" width="20">
    <input type='checkbox' name='ids' value='${ossUser.userId}'/><#t/>
  </@t.column><#t/>
  <@t.column title="ID">${ossUser.userId}</@t.column><#t/>
  <@t.column title="用户名">${ossUser.username}<#if ossUser.realName??>(${ossUser.realName})</#if></@t.column><#t/>
  <@t.column title="角色"><#list ossUser.roles as r>
    <div style="float:left;padding-right:3px">${r.roleName}</div></#list>
    <div style="clear:both"></div></@t.column><#t/>
  <@t.column title="等级" align="center">${ossUser.rank}</@t.column><#t/>
  <@t.column title="最后登录" align="center">${ossUser.lastLoginTime?string('yyyy-MM-dd HH:mm:ss')}
    <br/>${ossUser.lastLoginIP}</@t.column><#t/>
  <@t.column title="登录" align="right">${ossUser.loginCount}</@t.column><#t/>
  <@t.column title="禁用" align="center"><#if ossUser.disabled><span style="color:red">是</span><#else>否</#if></@t.column><#t/>
  <@t.column title="操作选项" align="center">
    <a href="v_edit.jhtml?id=${ossUser.userId}&pageNo=${pageNo!}" class="pn-opt">编辑</a> | <#rt/>
    <a href="o_remove.jhtml?ids=${ossUser.userId}&pageNo=${pageNo!}" onclick="if(!confirm('您确定删除吗？')) {return false;}" class="pn-opt">删除</a><#t/>
  </@t.column><#t/>
  </@t.table>
    <div style="margin-top:15px;">
      <input class="del-button" type="button" value="删除" onclick="optDelete();"/>
    </div>
  </form>
</div>
<#include "../common/alert_message.ftl"/>
</body>
</html>