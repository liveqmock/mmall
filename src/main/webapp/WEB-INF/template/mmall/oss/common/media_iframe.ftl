<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title></title>
    <script src="${base}/mmall/static/common/js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript">
      <#if error??>
      alert('${error}');
      <#else>
      parent.document.getElementById("mediaPath${uploadNum}").value = "${uploadPath}";
      </#if>
    </script>
</head>
<body>
</body>
</html>