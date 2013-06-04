<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>system-left</title>
<#include "../head.ftl"/>
    <script type="text/javascript">
        $(function () {
            OSS.lmenu('lmenu');
        });
    </script>
</head>
<body class="lbody">
<div class="left">
<#include "../date.html"/>
    <ul class="w-lful">
    <@oss_perm url="/admin/v_list.jhtml">
        <li><a href="../admin/v_list.jhtml" target="rightFrame">后台管理员管理</a></li>
    </@oss_perm>
    <@oss_perm url="/role/v_list.jhtml">
        <li><a href="../role/v_list.jhtml" target="rightFrame">后台角色管理</a></li>
    </@oss_perm>
    <@oss_perm url="/config/v_login_edit.jhtml">
        <li><a href="../config/v_login_edit.jhtml" target="rightFrame">全局配置</a></li>
    </@oss_perm>
    <@oss_perm url="/log/v_list_operating.jhtml">
        <li><a href="../log/v_list_operating.jhtml" target="rightFrame">后台操作日志</a></li>
    </@oss_perm>
    <@oss_perm url="/log/v_list_login_success.jhtml">
        <li><a href="../log/v_list_login_success.jhtml" target="rightFrame">登录成功日志</a></li>
    </@oss_perm>
    <@oss_perm url="/log/v_list_login_failure.jhtml">
        <li><a href="../log/v_list_login_failure.jhtml" target="rightFrame">登录失败日志</a></li>
    </@oss_perm>
    </ul>
</div>
</body>
</html>