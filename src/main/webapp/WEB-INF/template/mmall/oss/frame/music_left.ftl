<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>music-left</title>
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
    <@oss_perm url="/artist/v_list.jhtml">
        <li><a href="../artist/v_list.jhtml" target="rightFrame">艺人管理</a></li>
    </@oss_perm>
    <@oss_perm url="/album/v_list.jhtml">
        <li><a href="../album/v_list.jhtml" target="rightFrame">专辑管理</a></li>
    </@oss_perm>
    <@oss_perm url="/music/v_list.jhtml">
        <li><a href="../music/v_list.jhtml" target="rightFrame">音乐管理</a></li>
    </@oss_perm>
    </ul>
</div>
</body>
</html>