<#-- header -->
<#macro tl
title="mm2018"
>
<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"
        "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width; initial-scale=1.4;  minimum-scale=1.0; maximum-scale=2.0"/>
    <title>${title!}</title>
    <link href="${base}/mmall/static/mobile/color/css/color.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="wz">
    <div class="wz"></div>
</div>
<div id="top"></div>
  <#nested />
<div class="wz">
    <div class="wz"></div>
</div>
</body>
</html>
</#macro>