


<%@ include file="/init.jsp" %>

<p>
	<%
		//Object user = Objects.nonNull(renderRequest.getAttribute("USER")) ?
		//		renderRequest.getAttribute("USER") :
		//		"";
	%>

	<%= user %>

	<aui:button-row>
		<aui:button href="#" onClick="history.go(-1) " value='Back' type="Back" />
	</aui:button-row>

</p>