<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>role-add</title>
<#include "../head.ftl"/>
  <script type="text/javascript">
    function disablePerms() {
      if ($("input[name=super]:checked").val() == "true") {
        $(".perm-container input[type=checkbox]").attr("disabled", "disabeld");
      } else {
        $(".perm-container input[type=checkbox]").removeAttr("disabled");
      }
    }
    $(function() {
      $("#jvForm").validate();
      $("input[name=super]").bind("click", function() {
        disablePerms();
      });
    });
  </script>
  <style type="text/css">
    .perm-container {
    }

    .perm-layout-1 {
      padding: 3px 0;
    }

    .perm-layout-2 {
      padding-left: 30px;
    }
  </style>
</head>
<body>
<div class="box-positon">
  <div class="rpos">当前位置: 角色管理 - 添加</div>
  <form class="ropt">
    <input type="submit" value="返回列表" onclick="this.form.action='v_list.jhtml';" class="return-button"/>
  </form>
  <div class="clear"></div>
</div>
<div class="body-box">
<@t.form id="jvForm" action="o_create.jhtml" labelWidth="15">
<@t.text colspan="1" width="50" label="角色名" name="roleName" required="true" class="required" maxlength="100"/>
<@t.text colspan="1" width="50" label="排列顺序" name="priority" value="10" required="true" class="required digits" maxlength="5" style="width:70px"/><@t.tr/>
<@t.radio colspan="2" label="拥有所有权限" name="mSuper" value="false" list={"true":"是","false":"否"}/><@t.tr/>
<@t.td colspan="2" label="功能权限"><#include "perms.ftl"/></@t.td><@t.tr/>
<@t.td colspan="2"><@t.submit value="提交"/> &nbsp; <@t.reset value="重置"/></@t.td>
</@t.form>
</div>
</body>
</html>