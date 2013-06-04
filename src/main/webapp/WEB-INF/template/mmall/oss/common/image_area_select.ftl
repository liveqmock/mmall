<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>图片裁剪</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<#assign imgSrc=imgSrcPath/>
<#assign minWidth=zoomWidth/>
<#assign minHeight=zoomHeight/>
</head>
<body style="margin:3px auto;padding:0px;font-size:14px;width:1000px;">
<div style="">
    <div style="width:500px;height:500px;border:1px solid #333;margin-right:10px;float:left;">
        <img id="ferret" src="${imgSrc}" title="image select" alt="loading IMG ......"/>
    </div>
    <div style="float:left;">
        <div id="imgDiv" style="overflow:hidden;width:${minWidth}px;height:${minHeight}px;border:1px solid #333;">
            <img id="minImg" src="${imgSrc}"/>
        </div>
        <form id="submitForm" action="o_image_cut.jhtml" target="hiddenIframe" method="post" id="cutImgForm"
              style="margin:7px 0px 0px 0px;padding:0px">
            <input type="hidden" id="imgSrcPath" name="imgSrcPath" value="${imgSrcPath}"/>
            <input type="hidden" id="cutImageW" name="imgWidth"/>
            <input type="hidden" id="cutImageH" name="imgHeight"/>
            <input type="hidden" id="cutImageX" name="imgTop"/>
            <input type="hidden" id="cutImageY" name="imgLeft"/>
            <input type="hidden" id="imgScale" name="imgScale" value="1"/>
            <input type="hidden" name="uploadNum" value="${uploadNum}"/>

            <div style="margin-bottom:3px;">
                原图 &nbsp; &nbsp;宽: <span id="oimgW"></span> &nbsp; &nbsp; 高: <span id="oimgH"></span>
            </div>
            缩略图 宽: <input type="text" name="reMinWidth" id="reMinWidth" value="${minWidth}" size="5"
                          onkeypress="if(event.keyCode==13){$('#resetButton').click();return false;}"
                          onfocus="this.select();"/>
            高: <input type="text" name="reMinHeight" id="reMinHeight" value="${minHeight}" size="5"
                      onkeypress="if(event.keyCode==13){$('#resetButton').click();return;}" onfocus="this.select();"/>
            <input type="button" id="resetButton" value="重置"
                   onclick="resetOption($('#reMinWidth').val(),$('#reMinHeight').val());"/>

            <div style="text-align:center;margin-top:7px;">
                <input type="button" value="确认裁剪" onclick="$('#submitForm').submit();window.close();"/>
                <input type="button" value="关闭窗口" onclick="window.close();"/>
            </div>
        </form>
    </div>
    <div style="clear:both;"></div>
</div>
<script src="${base}/mmall/static/common/js/jquery.js" type="text/javascript"></script>
<script src="${base}/mmall/static/common/js/jquery.imgareaselect.js" type="text/javascript"></script>
<script type="text/javascript">
    var container = 500;
    var imageW = 100;
    var imageH = 100;
    var minWidth = ${minWidth};
    var minHeight = ${minHeight};
    var scale = 1;
    function preview(img, selection) {
        showCut(selection.width, selection.height, selection.x1, selection.y1);
    }
    function showCut(w, h, x, y) {
        var scaleX = minWidth / w;
        var scaleY = minHeight / h;
        $('#minImg').css({ width: Math.round(scaleX * imageW * scale) + 'px', height: Math.round(scaleY * imageH
                * scale) + 'px', marginLeft: '-' + Math.round(scaleX * x) + 'px', marginTop: '-' + Math.round(scaleY
                * y) + 'px' });
        $('input#cutImageW').val(w);
        $('input#cutImageH').val(h);
        $('input#cutImageX').val(x);
        $('input#cutImageY').val(y);
    }
    $(window).load(function () {
        imageW = $('#ferret').width();
        imageH = $('#ferret').height();
        $('#oimgW').html(imageW);
        $('#oimgH').html(imageH);
        var imgMax = imageW > imageH ? imageW : imageH;
        if (imgMax > container) {
            scale = container / imgMax;
            $('#imgScale').val(scale);
            $('#ferret').css({width: Math.round(scale * imageW) + 'px', height: Math.round(scale * imageH) + 'px'});
        }
    <#if !imgSrc?has_content>
        alert("没有指定要剪裁的图片");
        return;
    </#if>
        if (imageW <${minWidth}|| imageH <${minHeight}) {
            alert("源图尺寸小于缩略图,请重设缩略图大小.");
            return;
        }
        if (imageW == minWidth && imageH == minHeight) {
            alert("源图和缩略图尺寸一致,请重设缩略图大小.");
            return;
        }
        var minSelW = Math.round(${minWidth}* scale
        )
        var minSelH = Math.round(${minHeight}* scale
        )
        $('img#ferret').imgAreaSelect({selectionOpacity: 0, outerOpacity: '0.5', selectionColor: 'blue', onSelectChange: preview, minWidth: minSelW, minHeight: minSelH, aspectRatio: '${minWidth}:${minHeight}', x1: 0, y1: 0, x2:${minWidth}, y2:${minHeight}});
        showCut(${minWidth},${minHeight}, 0, 0);
    });
    function resetOption(width, height) {
        minWidth = parseInt(width);
        minHeight = parseInt(height);
        if (imageW < minWidth || imageH < minHeight) {
            alert("源图尺寸小于缩略图,请重设缩略图大小.");
            return;
        }
        if (imageW == minWidth && imageH == minHeight) {
            alert("源图和缩略图尺寸一致,请重设缩略图大小.");
            return;
        }
        $('#imgDiv').css({'width': minWidth + 'px', 'height': minHeight + 'px'});
        var minSelW = Math.round(minWidth * scale);
        var minSelH = Math.round(minHeight * scale);
        $('img#ferret').imgAreaSelect({selectionOpacity: 0, outerOpacity: '0.5', selectionColor: 'blue', onSelectChange: preview, minWidth: minSelW, minHeight: minSelH, aspectRatio: minWidth
                + ':' + minHeight, x1: 0, y1: 0, x2: minWidth, y2: minHeight});
        //showCut(minWidth,minHeight,0,0);
    }
</script>
</body>
</html>