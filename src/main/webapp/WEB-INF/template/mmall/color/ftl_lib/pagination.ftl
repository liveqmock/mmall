<#--
  url params to form input hidden
-->
<#macro handlerParam params>
  <#if params?? && params?size gt 0>
    <#list params as param>
      <#assign posi = param?index_of("=") />
      <#assign pKey = param?substring(0, posi) />
      <#assign posi = posi + 1 />
      <#assign pValue = param?substring(posi) />
    <input type="hidden" name="${pKey!}" value="${pValue!}"/>
    </#list>
  </#if>
</#macro>

<#--
  pagination: style1 上页.下页.页码.【】跳页
-->
<#macro pg1 pageList pageLink pageKey="page">
  <#-- 链接参数初始化 -->
  <#assign posi = pageLink?index_of("?") />
  <#if posi gt 0>
    <#assign action = pageLink?substring(0, posi) />
    <#assign posi = posi + 1 />
    <#assign params = pageLink?substring(posi) />
  <#else>
    <#assign action = pageLink />
    <#assign params = "" />
  </#if>
  <#-- 分页参数初始化 -->
  <#assign pageCount = pageList.totalPage />
  <#assign currentPage = pageList.page />
  <#assign totalRowCount = pageList.totalResult />
  <#-- 开始分页 -->
  <#if pageCount gt 1>
    <#if !pageList.lastPage><@c.a href="${action}?${params}&${pageKey}=${pageList.nextPage}">下一页</@c.a><#else>下一页</#if><#rt/>
    <#if !pageList.firstPage><@c.a href="${action}?${params}&${pageKey}=${pageList.prevPage}">上一页</@c.a><#else>上一页</#if><br/><#rt/>
    第<#rt/>
    <#if currentPage - 3 gt 1>
      <#if pageCount gt currentPage + 3>
        <#list currentPage - 3..currentPage + 3 as i><#if i = currentPage>[${i}]<#else><@c.a href="${action}?${params}&${pageKey}=${i}">${i}</@c.a></#if><#--<#if i_has_next>.</#if>--></#list>
      <#else>
        <#list pageCount-6..pageCount as i><#if i = currentPage>[${i}]<#else><@c.a href="${action}?${params}&${pageKey}=${i}">${i}</@c.a></#if><#--<#if i_has_next>.</#if>--></#list>
      </#if>
    <#else>
      <#if pageCount gt 7>
        <#list 1..7 as i><#if i = currentPage>[${i}]<#else><@c.a href="${action}?${params}&${pageKey}=${i}">${i}</@c.a></#if><#--<#if i_has_next>.</#if>--></#list>
      <#else>
        <#list 1..pageCount as i><#if i == currentPage>[${i}]<#else><@c.a href="${action}?${params}&${pageKey}=${i}">${i}</@c.a></#if><#--<#if i_has_next>.</#if>--></#list>
      </#if>
    </#if>
  </#if>
</#macro>