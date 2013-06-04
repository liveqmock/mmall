<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>oss-left</title>
<#include "head.ftl"/>
  <script type="text/javascript">
    $(function() {
      OSS.lmenu('lmenu');
    });
  </script>
</head>
<body class="lbody">
<div class="left">
<#include "date.html"/>
  <ul class="w-lful">
    <li><a href="right.jhtml" target="rightFrame">欢迎页</a></li>
  <@oss_perm url="/personal/v_profile.jhtml">
    <li><a href="personal/v_profile.jhtml" target="rightFrame">个人资料</a></li>
  </@oss_perm>
  </ul>
</div>
</body>
</html>