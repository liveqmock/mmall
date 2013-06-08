<@c.tl title="${(orders.itemName)!}">
<div class="bd"> <!--上下分界限-->
    <div class="wz">
        <div class="wz"></div>
    </div>
    恭喜您!您的订单提交成功!<br/>
    您的订单号:<b>${(orders.orderId)!}</b>(请牢记,以便查询.)<br/>
    订购商品:${(orders.itemName)!}<br/>
    商品单价:${(orders.price)!}元<br/>
    优惠券:0元<br/>
    运费:包邮<br/>
    购买数量:【1】<br/>
    合计费用=${(orders.price)!}元<br/>
    提交订单后,我们将在24小时内联系您,请保持电话畅通<br/>

    <div class="wz">
        <div class="wz"></div>
    </div>
    <div class="footer">
        <div class="nav1_left">
            <a href="#top"><img src="${base}/mmall/static/mobile/images/totop.gif" alt="top"/></a><br/>
        </div>
        订购|咨询<a href="wtai://wp/mc;4000939991">400-093-9991</a><br/>客服服务 09:00-21:00<br/>
    </div>
</@c.tl>