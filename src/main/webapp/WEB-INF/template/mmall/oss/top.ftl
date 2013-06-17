<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>Administrator's Control Panel - powered by kxai</title>
<#include "head.ftl"/>
  <style type="text/css">
    * {
      margin: 0;
      padding: 0
    }

    a:focus {
      outline: none;
    }

    html {
      height: 100%;
      overflow: hidden;
    }

    body {
      height: 100%;
    }

    #top {
      background-color: #1d63c6;
      height: 69px;
      width: 100%;
    }

    .logo {
      width: 215px;
      height: 69px;
    }

    .topbg {
      background: url(${base}/mmall/static/oss/images/admin/top-tbg.png) no-repeat;
      height: 38px;
    }

    .login-welcome {
      padding-left: 20px;
      color: #fff;
      font-size: 12px;
      background: url(${base}/mmall/static/oss/images/admin/topbg.gif) no-repeat;
    }

    .login-welcome a:link, .login-welcome a:visited {
      color: #fff;
      text-decoration: none;
    }

    #welcome {
      color: #FFFFFF;
      padding: 0 30px 0 5px;
    }

    #logout {
      color: #FFFFFF;
      padding-left: 5px;
    }

    .nav {
      height: 31px;
      overflow: hidden;
    }

    .nav-menu {
      background: url(${base}/mmall/static/oss/images/admin/bg.png) repeat-x;
      height: 31px;
      list-style: none;
      padding-left: 20px;
      font-size: 14px;
    }

    .nav .current {
      background: url(${base}/mmall/static/oss/images/admin/navcurrbg.gif) no-repeat 0 2px;
      color: #fff;
      width: 72px;
      text-align: center;
    }

    .nav .current a {
      color: #fff;
    }

    .nav-menu li {
      height: 31px;
      text-align: center;
      line-height: 31px;
      float: left;
    }

    .nav-menu li a {
      color: #2b2b2b;
      font-weight: bold;
    }

    .nav-menu li.sep {
      background: url(${base}/mmall/static/oss/images/admin/step.png) no-repeat;
      width: 2px;
      height: 31px;
      margin: 0 5px;
    }

    .nav .normal {
      width: 72px;
      text-align: center;
    }

    .top-bottom {
      width: 100%;
      background: url(${base}/mmall/static/oss/images/admin/bg.png) repeat-x 0 -34px;
      height: 3px;
    }

  </style>

  <script type="text/javascript">
    function g(o) {
      return document.getElementById(o);
    }
    $(function(){
    	$("ul").find("li[id *='tb_']").each(function(index){
    		$(this).bind("click",function(){
    			$(this).addClass("current");
    			var id = this.id;
    			$("li[id!='"+id+"']").removeClass("current").addClass("normal");
    		});
    	});
    });
  </script>
</head>

<body>
<div id="top">
  <div class="top">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="215">
          <div class="logo"><img src="${base}/mmall/static/oss/images/admin/logo.png" width="215" height="69"/></div>
        </td>
        <td valign="top">
          <div class="topbg">
            <div class="login-welcome">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="320" height="38">
                    <img src="${base}/mmall/static/oss/images/admin/welconlogin-icon.png"/>
                    <span id="welcome">您好, ${user.username}</span>
                    <img src="${base}/mmall/static/oss/images/admin/loginout-icon.png"/>
                    <a href="logout.jhtml?returnUrl=index.jhtml" target="_top" id="logout" onclick="return confirm('您确定退出吗？');">退出</a>
                  </td>
                </tr>
              </table>
            </div>
            <div class="nav">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td style="background-image:url('${base}/mmall/static/oss/images/admin/nav-left.png')"
                      width="14" height="31"></td>
                  <td>
                    <ul class="nav-menu">
                      <li class="current" id="tb_11">
                        <a href="main.jhtml" target="mainFrame">首页</a>
                      </li>
                    <@oss_perm url="/frame/system_main.jhtml">
                      <li class="sep"></li>
                      <li class="normal" id="tb_12">
                        <a href="frame/system_main.jhtml" target="mainFrame">系统</a>
                      </li>
                    </@oss_perm>
                    <@oss_perm url="/frame/orders_main.jhtml">
                       <li class="sep"></li>
                       <li class="normal" id="tb_12">
                         <a href="frame/orders_main.jhtml" target="mainFrame">订单</a>
                       </li>
                    </@oss_perm>
                    </ul>
                  </td>
                </tr>
              </table>
            </div>
          </div>
      </tr>
    </table>
  </div>
</div>
<div class="top-bottom"></div>
</body>
</html>