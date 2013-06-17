<@c.tl title="IPAD MINI皮套 - mm2018.net">
<div class="bd"> <!--上下分界限-->
    <div class="wz">
        <div class="wz"></div>
    </div>
  <#if error?? && error?length gt 0>
  ${error!}<br/>
  </#if>
    订购商品:IPAD MINI皮套<br/>
    商品单价:128元<br/>
    运费:包邮<br/>
    购买数量:【1】<br/>
    属性:皮套<br/>
    合计费用=128元<br/>

    <form method="post" action="${base}/mmall/c/order/${id!}">
        =收获信息填写=<br/>
        联系人:<br/>
        <input name="contact" type="text" size="8" value=""/>*<br/>
        联系电话:<br/>
        <input name="phone" type="text" format="*N" size="15" value=""/>*<br/>
        送货地址:<br/>
        <input name="address" type="text" size="50" value=""/><br/>
        x省x市x区(x路)x镇x村.地址后请写上邮政编码<br/>
        订货备注:<br/>
        <input name="remark" type="text" size="24" value=""/><br/>
        请选择支付方式:<br/>
        <input type="submit" name="btnOK" value=" 立即购买 " class="btn"/>
    </form>
    提交订单后,我们将在24小时内联系您,请保持电话畅通<br/>

    <div class="wz">
        <div class="wz"></div>
    </div>
    <div class="footer">
        <div class="nav1_left">
            <a href="#top"><img src="${base}/mmall/static/mobile/images/totop.gif" alt="top"/></a><br/>
        </div>
        订购|咨询<a href="wtai://wp/mc;075583405480">0755-83405480</a><br/>
        客服QQ：852323338<br/>
        客服服务 09:00-21:00<br/>
        版本切换:彩色版|<a href="${base}/mmall/s/order/${id!}">简单版</a>
    </div>
</@c.tl>