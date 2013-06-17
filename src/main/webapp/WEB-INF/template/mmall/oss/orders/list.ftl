<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>orders-list</title>
<#include "../head.ftl"/>
    <script type="text/javascript">
        function getTableForm() {
            return document.getElementById("tableForm");
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
            f.action = "o_remove.jhtml";
            f.submit();
        }
        function chgStatus() {
            var queryStatus = $("input[name=queryStatus]:checked").val();
            location.href = "v_list.jhtml?queryStatus=" + queryStatus
                    + "&queryContact=${queryContact!}&queryPhone=${queryPhone!}&pageNo=${pageNo!}";
        }
        function optSaveContent() {
            var f = getTableForm();
            f.action = "o_modify.jhtml";
            f.submit();
        }
    </script>
</head>
<body>
<div id="sd" style="display:none;position:absolute;height:300px;width:300px"></div>
<div class="box-positon">
    <div class="rpos">当前位置: 订单管理 - 列表</div>
    <form class="ropt">
    </form>
    <div class="clear"></div>
</div>
<div class="body-box">
<#assign params>&pageNo=${pageNo!}&queryContact=${queryContact!?url}&queryPhone=${queryPhone!}&queryStatus=${queryStatus!}</#assign>
    <form action="v_list.jhtml" method="post" style="padding-top:5px;">
        <div>
            联系人 : <input type="text" name="queryContact" value="${queryContact!}" style="width:100px"/>
            &nbsp;电话 : <input type="text" name="queryPhone" value="${queryPhone!}" style="width:100px"/>
            <input class="query" type="submit" value="查询"/>
        </div>
        <div style="padding-top:5px">
            <label><input type="radio" name="queryStatus" value=""
                          onclick="chgStatus();"<#if !queryStatus??> checked="checked"</#if>/>所有内容</label>
            <label><input type="radio" name="queryStatus" value="0"
                          onclick="chgStatus();"<#if queryStatus?? && queryStatus==0> checked="checked"</#if>/>下单</label>
            <label><input type="radio" name="queryStatus" value="1"
                          onclick="chgStatus();"<#if queryStatus?? && queryStatus==1> checked="checked"</#if>/>已回访</label>
            <label><input type="radio" name="queryStatus" value="2"
                          onclick="chgStatus();"<#if queryStatus?? && queryStatus==2> checked="checked"</#if>/>已发货</label>
            <label><input type="radio" name="queryStatus" value="3"
                          onclick="chgStatus();"<#if queryStatus?? && queryStatus==3> checked="checked"</#if>/>已签收</label>
            <label><input type="radio" name="queryStatus" value="4"
                          onclick="chgStatus();"<#if queryStatus?? && queryStatus==4> checked="checked"</#if>/>已完成</label>
            <label><input type="radio" name="queryStatus" value="-1"
                                      onclick="chgStatus();"<#if queryStatus?? && queryStatus==-1> checked="checked"</#if>/>已删除</label>
        </div>
    </form>
    <form id="tableForm" method="post">
        <input type="hidden" name="pageNo" value="${pageNo!}"/>
        <input type="hidden" name="queryTitle" value="${queryContact!}"/>
        <input type="hidden" name="queryStatus" value="${queryStatus!}"/>
        <input type="hidden" name="queryOrderBy" value="${queryPhone!}"/>
    <@t.table value=pagination;orders,i,has_next><#rt/>
      <@t.column title="<input type='checkbox' onclick='Kxa.checkbox(\"ids\",this.checked)'/>" width="20">
          <input type="checkbox" name="ids" value="${(orders.orderId)!}"/><#t/>
      </@t.column><#t/>
      <@t.column title="ID">${(orders.orderId)!}</@t.column><#t/>
      <@t.column title="商品名">${orders.itemName}</@t.column><#t/>
      <@t.column title="价格">${(orders.price)!}</@t.column><#t/>
      <@t.column title="联系人" align="center">${(orders.contact)!}</@t.column><#t/>
      <@t.column title="电话" align="center">${(orders.phone)!}</@t.column><#t/>
      <@t.column title="地址" align="center">${(orders.address)!0}</@t.column><#t/>
      <@t.column title="备注">${(orders.remark)!}</@t.column><#t/>
      <@t.column title="状态" align="center">
        <#if orders.status == 0>
          下单
        <#elseif orders.status == 1>
          已回访
        <#elseif orders.status == 2>
          已发货
        <#elseif orders.status == 3>
          已签收
        <#elseif orders.status == 4>
          已完成
        <#elseif orders.status == -1>
        <span style="color:red">已删除</span>
        </#if>
      </@t.column><#t/>
      <@t.column title="下单时间" align="center">${(orders.createTime)!?string("yyyy-MM-dd HH:mm:ss")}</@t.column><#t/>
      <@t.column title="操作选项" align="center">
        <#if orders.status < 1 && orders.status != -1>
          <a href="o_modify_status.jhtml?orderId=${(orders.orderId)!}${params}&status=1" class="pn-opt">回访</a> | <#rt/>
        </#if>
        <#if orders.status < 2 && orders.status != -1>
          <a href="o_modify_status.jhtml?orderId=${(orders.orderId)!}${params}&status=2" onclick="if(!confirm('您确定发货吗？订单编号是否已经编辑输入')) {return false;}" class="pn-opt">发货</a> | <#rt/>
        </#if>
        <#if orders.status < 3 && orders.status != -1>
          <a href="o_modify_status.jhtml?orderId=${(orders.orderId)!}${params}&status=3" class="pn-opt">签收</a> | <#rt/>
        </#if>
        <#if orders.status < 4 && orders.status != -1>
          <a href="o_modify_status.jhtml?orderId=${(orders.orderId)!}${params}&status=4" class="pn-opt">完成</a> | <#rt/>
        </#if>
        <#if orders.status != -1>
          <a href="o_modify_status.jhtml?orderId=${(orders.orderId)!}${params}&status=-1" onclick="if(!confirm('您确定删除吗？')) {return false;}" class="pn-opt">删除</a><#rt/>
        </#if>
      </@t.column><#t/>
    </@t.table>
        <div style="margin-top:15px;">
        <#--<@oss_perm url="/orders/o_remove.jhtml">-->
            <#--<input type="button" value="删除" onclick="optDelete();" class="del-button"/>-->
        <#--</@oss_perm>-->
        <#--<@oss_perm url="/story/o_check.jhtml">-->
            <#--&nbsp; <input type="button" value="发布" onclick="optCheck();" class="check"/>-->
        <#--</@oss_perm>-->
        <#--<@oss_perm url="/story/o_recommend.jhtml">-->
            <#--&nbsp; <input type="button" value="推荐" onclick="optRecommend();" class="export"/>-->
        <#--</@oss_perm>-->
        <#--<@oss_perm url="/story/o_cancel_recommend.jhtml">-->
            <#--&nbsp; <input type="button" value="取消推荐" onclick="optCancelRecommend();" class="del-index-page"/>-->
        <#--</@oss_perm>-->
        </div>
    </form>
</div>
<#include "../common/alert_message.ftl"/>
</body>
</html>





