<#--
<a href=""></a>
-->
<#macro a href class="">
<a<#rt/>
<#if class?length gt 0> class="${class!}"</#if><#rt/>
 href="<#if href?? && href?length gt 0>${href?replace('&','&amp;')}<#if href?contains('?')>&amp;<#else>?</#if>msid=${msid!}</#if>"><#rt/>
<#nested />
</a><#rt/>
</#macro>