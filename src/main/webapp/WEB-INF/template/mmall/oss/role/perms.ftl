<div class="perm-container">
    <#--图片处理权限-->
    <#assign imgPerms="/common/v_image_area_select.jhtml,/common/o_image_cut.jhtml,/common/o_upload_image.jhtml"/>
    <#--默认有的权限，比如后台首页-->
    <input type="hidden" name="perms" value="/index.jhtml,/top.jhtml,/main.jhtml,/left.jhtml,/right.jhtml"/>

    <div class="perm-layout-1">
        <label><input value="/personal/" type="checkbox" name="perms"/>个人资料</label>
    </div>

    <div class="perm-layout-1">
        <label><input value="/frame/system" type="checkbox" name="perms"/>系统管理</label>

        <div>
            <div class="perm-layout-2"><label><input value="/admin/" type="checkbox" name="perms"/>管理员管理</label></div>
            <div class="perm-layout-2"><label><input value="/role/" type="checkbox" name="perms"/>角色管理</label></div>
            <div class="perm-layout-2"><label><input value="/config/" type="checkbox" name="perms"/>全局配置</label></div>
            <div class="perm-layout-2"><label><input value="/log/" type="checkbox" name="perms"/>后台日志</label></div>
        </div>
    </div>

    <div class="perm-layout-1">
        <label><input value="/frame/orders" type="checkbox" name="perms"/>订单管理</label>

        <div>
            <#--<div class="perm-layout-2"><label><input value="/artist/,${imgPerms}" type="checkbox" name="perms"/>艺人管理</label></div>-->
            <#--<div class="perm-layout-2"><label><input value="/album/,${imgPerms}" type="checkbox" name="perms"/>专辑管理</label></div>-->
            <div class="perm-layout-2"><label><input value="/music/" type="checkbox" name="perms"/>订单管理</label></div>
        </div>
    </div>

    <#--<div class="perm-layout-1">-->
    <#--<label><input value="/frame/category,/category/v_left.jhtml,/category/v_tree.jhtml,/category/v_list.jhtml" type="checkbox" name="perms"/>分类管理</label>-->
    	<#--<div class="perm-layout-2">-->
    	<#--<label><input value="/category/v_add.jhtml,/category/o_create.jhtml" type="checkbox" name="perms"/>添加</label>-->
    	<#--<label><input value="/category/v_edit.jhtml,/category/o_modify.jhtml,/category/o_priority.jhtml" type="checkbox" name="perms"/>修改</label>-->
    	<#--<label><input value="/category/o_delete.jhtml" type="checkbox" name="perms"/>删除</label>-->
    	<#--</div>-->
    <#--</div>-->

    <#--<div class="perm-layout-1">-->
    <#--<label><input value="/frame/story,/story/v_left.jhtml,/story/v_tree.jhtml,/story/v_list.jhtml,/story/v_view.jhtml,/story/v_check_title.jhtml" type="checkbox" name="perms"/>故事管理</label>-->
        <#--<div class="perm-layout-2">-->
        <#--<label><input value="/story/v_add.jhtml,/story/o_create.jhtml,/story/v_tree_categories.jhtml,/fck/upload.jhtml,${imgPerms}" type="checkbox" name="perms"/>添加</label>-->
        <#--<label><input value="/story/v_edit.jhtml,/story/o_modify.jhtml,/story/v_tree_categories.jhtml,/fck/upload.jhtml,${imgPerms}" type="checkbox" name="perms"/>修改</label>-->
        <#--<label><input value="/story/o_remove.jhtml" type="checkbox" name="perms"/>删除</label>-->
        <#--<label><input value="/story/o_check.jhtml" type="checkbox" name="perms"/>发布</label>-->
        <#--<label><input value="/story/o_recommend.jhtml,/story/o_cancel_recommend.jhtml" type="checkbox" name="perms"/>推荐</label>-->
        <#--</div>-->
    <#--</div>-->

    <#--<div class="perm-layout-1">-->
    <#--<label><input value="/frame/user,/user/v_left.jhtml,/user/v_right.jhtml,/user/v_list.jhtml" type="checkbox" name="perms"/>用户管理</label>-->
      <#--<div class="perm-layout-2">-->
         <#--<label><input value="/user/v_add.jhtml,/user/o_create.jhtml,${imgPerms}" type="checkbox" name="perms"/>添加</label>-->
         <#--<label><input value="/user/v_edit.jhtml,/user/o_modify.jhtml,${imgPerms}" type="checkbox" name="perms"/>修改</label>-->
      <#--</div>-->
    <#--</div>-->


    <#--<div class="perm-layout-1">-->
    <#--<label><input value="/frame/support" type="checkbox" name="perms"/>辅助管理</label>-->
      <#--<div>-->
        <#--<div class="perm-layout-2"><label><input value="/story/v_list_cover.jhtml,/story/o_remove_cover.jhtml" type="checkbox" name="perms"/>历史封面故事管理</label></div>-->
        <#--<div class="perm-layout-2"><label><input value="/topic/" type="checkbox" name="perms"/>专题管理</label></div>-->
        <#--<div class="perm-layout-2"><label><input value="/comment/" type="checkbox" name="perms"/>评论管理</label></div>-->
        <#--<div class="perm-layout-2"><label><input value="/sensitivity/" type="checkbox" name="perms"/>敏感词管理</label></div>-->
      <#--</div>-->
    <#--</div>-->

    <#--<div class="perm-layout-1">-->
    <#--<label><input name="perms" value="/frame/resource,/resource/v_left.jhtml,/resource/v_tree.jhtml,/resource/v_list.jhtml" type="checkbox"/>资源管理</label>-->
    	<#--<div class="perm-layout-2">-->
    	<#--<label><input value="/resource/v_add.jhtml,/resource/o_create.jhtml,/resource/o_create_dir.jhtml,/resource/v_upload.jhtml,/resource/o_upload.jhtml,/resource/o_swfupload.jhtml" type="checkbox" name="perms"/>添加</label>-->
    	<#--<label><input value="/resource/v_edit.jhtml,/resource/o_update.jhtml,/resource/v_rename.jhtml,/resource/o_rename.jhtml" type="checkbox" name="perms"/>修改</label>-->
    	<#--<label><input value="/resource/o_delete.jhtml,/resource/o_delete_single.jhtml" type="checkbox" name="perms"/>删除</label>-->
    	<#--</div>-->
    <#--</div>-->

</div>
<script type="text/javascript">
    $(function () {
        $(".perm-container input[type=checkbox]").bind("click", function () {
            parentCheck(this);
            childCheck(this);
        });
    });
    function parentCheck(chk) {
        var obj = $(chk).parent().parent().parent();
        while (obj && obj.attr("class").indexOf("perm-container") == -1) {
            if (chk.checked) {
                $(obj.children()[0]).children().attr("checked", true);
            }
            obj = obj.parent();
        }
    }
    function childCheck(chk) {
        $(chk).parent().next().children().find("input[type=checkbox]").each(function () {
            this.checked = chk.checked
        });
    }
</script>