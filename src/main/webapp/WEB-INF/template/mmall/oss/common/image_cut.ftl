<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script src="${base}/mmall/static/common/js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript">
      <#if error??>
      alert("${error}");
      <#else>
      var imgSrc = parent.document.getElementById("preImg${uploadNum}");
      var path = $(parent.document.getElementById("uploadImgPath${uploadNum}")).val();
      if (path.indexOf("?") == -1) {
          imgSrc.src = path + "?d=" + new Date();
      } else {
          imgSrc.src = path + "&d=" + new Date();
      }
      if (!$(imgSrc).attr("noResize")) {
          $(imgSrc).css("width", "auto");
          $(imgSrc).css("height", "auto");
      }
      </#if>
    </script>
</head>
<body>
</body>
</html>