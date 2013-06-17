<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>orders-left</title>
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
    <@oss_perm url="/orders/v_list.jhtml">
        <li><a href="../orders/v_list.jhtml" target="rightFrame">订单管理</a></li>
    </@oss_perm>
    </ul>
</div>
</body>
</html>