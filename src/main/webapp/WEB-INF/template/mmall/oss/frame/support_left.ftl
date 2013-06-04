<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>support-left</title>
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
    <@oss_perm url="/story/v_list_cover.jhtml">
        <li><a href="../story/v_list_cover.jhtml" target="rightFrame">历史封面故事管理</a></li>
    </@oss_perm>
    <@oss_perm url="/topic/v_list.jhtml">
        <li><a href="../topic/v_list.jhtml" target="rightFrame">专题管理</a></li>
    </@oss_perm>
    <@oss_perm url="/comment/v_list.jhtml">
        <li><a href="../comment/v_list.jhtml" target="rightFrame">评论管理</a></li>
    </@oss_perm>
    <@oss_perm url="/sensitivity/v_list.jhtml">
        <li><a href="../sensitivity/v_list.jhtml" target="rightFrame">敏感词管理</a></li>
    </@oss_perm>
    </ul>
</div>
</body>
</html>