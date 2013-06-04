<#--
<input type="button"/>
-->
<#macro button
	value=""
	id="" name="" class="" style="" size="" title="" disabled="" tabindex="" accesskey=""
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange=""
	>
<input type="button"<#rt/>
 value="${value}"<#rt/>
<#if id!=""> id="${id}"</#if><#rt/>
<#include "common-attributes.ftl"/><#rt/>
<#include "scripting-events.ftl"/><#rt/>
/><#rt/>
</#macro>

<#--
<input type="submit"/>
-->
<#macro submit
	value=""
	id="" name="" class="submit" style="" size="" title="" disabled="" tabindex="" accesskey=""
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange=""
	>
<input type="submit"<#rt/>
 value="${value}"<#rt/>
<#if id!=""> id="${id}"</#if><#rt/>
 class="${class}"<#rt/>
<#include "common-attributes.ftl"/><#rt/>
<#include "scripting-events.ftl"/><#rt/>
/><#rt/>
</#macro>

<#--
<input type="reset"/>
-->
<#macro reset
	value=""
	id="" name="" class="reset" style="" size="" title="" disabled="" tabindex="" accesskey=""
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange=""
	>
<input type="reset"<#rt/>
 value="${value}"<#rt/>
<#if id!=""> id="${id}"</#if><#rt/>
 class="${class}"<#rt/>
<#include "common-attributes.ftl"/><#rt/>
<#include "scripting-events.ftl"/><#rt/>
/><#rt/>
</#macro>