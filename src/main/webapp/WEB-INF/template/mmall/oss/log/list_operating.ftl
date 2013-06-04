<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>log_operating-list</title>
<#include "../head.ftl"/>
  <script type="text/javascript">
    function getTableForm() {
      return document.getElementById('tableForm');
    }
    function optDelete() {
      if (Kxa.checkedCount('ids') <= 0) {
        alert("请选择您要操作的数据");
        return;
      }
      if (!confirm("您确定删除吗？")) {
        return;
      }
      var f = getTableForm();
      f.action = "o_remove_operating.jhtml";
      f.submit();
    }
  </script>
</head>
<body>
<div class="box-positon">
  <div class="rpos">当前位置: 后台操作日志 - 列表</div>
  <form class="ropt" action="o_remove_operating_batch.jhtml" method="post">
    <span>批量删除</span>
      <select name="days">
          <option value="365">一年前日志</option>
          <option value="90">一季前日志</option>
          <option value="30">一月前日志</option>
          <option value="7">一周前日志</option>
          <option value="0">所有日志</option>
      </select>
      <input class="submit" type="submit" value="提交"/>
  </form>
  <div class="clear"></div>
</div>
<div class="body-box">
  <form action="v_list_operating.jhtml" method="post" style="padding-top:5px;">
    用户 : <input type="text" name="queryUsername" value="${queryUsername!}" style="width:100px"/>
    标题 : <input type="text" name="queryTitle" value="${queryTitle!}" style="width:150px"/>
    <input class="query" type="submit" value="查询"/>
  </form>
  <form id="tableForm" method="post">
  <input type="hidden" name="pageNo" value="${pageNo!}"/>
  <input type="hidden" name="queryUsername" value="${queryUsername!}"/>
  <input type="hidden" name="queryTitle" value="${queryTitle!}"/>
  <@t.table listAction="v_list_operating.jhtml" value=pagination;ossLog,i,has_next><#rt/>
  <@t.column title="<input type='checkbox' onclick='Kxa.checkbox(\"ids\",this.checked)'/>" width="20">
    <input type='checkbox' name='ids' value='${ossLog.logId}'/><#t/>
  </@t.column><#t/>
  <@t.column title="ID">${ossLog.logId}</@t.column><#t/>
  <@t.column title="操作用户">${ossLog.user.username}</@t.column><#t/>
  <@t.column title="操作时间" align="center">${ossLog.createTime?string("yyyy-MM-dd HH:mm:ss")}</@t.column><#t/>
  <@t.column title="IP地址" align="center">${ossLog.ip!}</@t.column><#t/>
  <@t.column title="日志标题" align="center">${ossLog.title}</@t.column><#t/>
  <@t.column title="日志内容">${ossLog.content!?html}</@t.column><#t/>
  <@t.column title="操作选项" align="center">
    <a href="o_remove_operating.jhtml?ids=${ossLog.logId}&pageNo=${pageNo!}&queryUsername=${queryUsername!}&queryTitle=${queryTitle!}" onclick="if(!confirm('您确定删除吗？')) {return false;}"
       class="pn-opt">删除</a><#t/>
  </@t.column><#t/>
  </@t.table>
    <div style="margin-top:15px;">
      <input class="del-button" type="button" value="删除" onclick="optDelete();"/>
    </div>
  </form>
</div>
<#include "../common/alert_message.ftl"/>
</body>
</html>