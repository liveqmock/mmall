<@s.tl title="IPHONE5 真皮手机套 - mm2018.net">
  <#if error?? && error?length gt 0>
  ${error!}<br/>
  </#if>
订购商品:IPHONE5 真皮手机套<br/>
商品单价:328元<br/>
运费:包邮<br/>
购买数量:【1】<br/>
属性:真皮<br/>
合计费用=328元<br/>
=收获信息填写=<br/>
联系人:<br/>
<input name="contact" title="contact" type="text" emptyok="false" maxlength="50"/>*<br/>
联系电话:<br/>
<input name="phone" type="text" format="*N" emptyok="false" value=""/>*<br/>
送货地址:<br/>
<input name="address" type="text" value=""/><br/>
x省x市x区(x路)x镇x村.地址后请写上邮政编码<br/>
订货备注:<br/>
<input name="remark" type="text" value=""/><br/>
请选择支付方式:<br/>
<anchor title=" 立即购买 "> 立即购买
    <go href="${base}/mmall/s/order/${id!}" method="post">
        <postfield name="contact" value="$(contact)"/>
        <postfield name="phone" value="$(phone)"/>
        <postfield name="address" value="$(address)"/>
        <postfield name="remark" value="$(remark)"/>
    </go>
</anchor>
提交订单后,我们将在24小时内联系您,请保持电话畅通<br/>
&gt;&gt;切换到<a href="${base}/mmall/c/order/${id!}">彩色版</a><br/>
订购|咨询<a href="wtai://wp/mc;4000939991">400-093-9991</a><br/>
客服服务 09:00-21:00<br/>
</@s.tl>