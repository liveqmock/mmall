<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>oss-right</title>
<#include "head.ftl"/>
</head>
<body>
<div class="box-positon">
  <h1>当前位置: 首页 - 欢迎页</h1>
</div>
<div class="body-box">
  <div class="welcom-con">
    <div class="we-txt">
      <p>
        欢迎登录MMall OSS系统！<br/>
        OSS版本： oss v1.0 beta<br/>
        您上次登录的时间是：${user.lastLoginTime?string('yyyy-MM-dd')}<br/>
        已用内存：<span style="color:#0078ff;">${(usedMemory/1024/1024)?string("0.##")}MB</span>&nbsp;&nbsp;&nbsp;&nbsp;
        剩余内存：<span style="color:#ff8400;">${(usableMemory/1024/1024)?string("0.##")}MB</span>&nbsp;&nbsp;&nbsp;&nbsp;
        最大内存：<span style="color:#00ac41;">${(maxMemory/1024/1024)?string("0.##")}MB</span>
      </p>
    </div>
    <ul class="ms">
      <li class="wxx">系统属性</li>
    </ul>
    <div class="ms-xx">
      <div class="xx-xx">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="30%" height="30" align="right">操作系统版本：</td>
            <td height="30"><span class="black">${props['os.name']!} ${props['os.version']!}</span></td>
          </tr>
          <tr>
            <td width="30%" height="30" align="right">操作系统类型：</td>
            <td height="30"><span class="black">${props['os.arch']!} ${props['sun.arch.data.model']!}位</span></td>
          </tr>
          <tr>
            <td width="30%" height="30" align="right">用户、目录、临时目录：</td>
            <td height="30"><span class="black">${props['user.name']!}, ${props['user.dir']!}
              , ${props['java.io.tmpdir']!}</span></td>
          </tr>
          <tr>
            <td width="30%" height="30" align="right">JAVA运行环境：</td>
            <td height="30"><span>${props['java.runtime.name']!} ${props['java.runtime.version']!}</span></td>
          </tr>
          <tr>
            <td width="30%" height="30" align="right">JAVA虚拟机：</td>
            <td height="30"><span>${props['java.vm.name']!} ${props['java.vm.version']!}</span></td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</body>
</html>