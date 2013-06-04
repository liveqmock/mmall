<#--
<a href=""></a>
-->
<#macro a href class="">
<a<#rt/>
<#if class?length gt 0> class="${class!}"</#if><#rt/>
 href="<#if href?? && href?length gt 0>${href}<#if href?contains('?')>&<#else>?</#if>msid=${msid!}</#if>"><#rt/>
<#nested />
</a><#rt/>
</#macro>