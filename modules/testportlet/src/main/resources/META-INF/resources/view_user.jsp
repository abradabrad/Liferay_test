


<%@ include file="/init.jsp" %>

<p>
	<%
		Object users = Objects.nonNull(renderRequest.getAttribute("USER")) ?
				renderRequest.getAttribute("USER") :
				"";
	%>

	<%= users %>

	<aui:button-row>
		<aui:button href="#" onClick="history.go(-1) " value='Back' type="Back" />
	</aui:button-row>

</p>