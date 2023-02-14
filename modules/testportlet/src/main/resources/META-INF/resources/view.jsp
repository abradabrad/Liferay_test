<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.time.format.FormatStyle" %>

<%@ page import="testportlet.util.DateTimeUtils" %>
<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.kernel.model.*" %>


<%@ include file="/init.jsp" %>

<%
	//PortletURL userUrl = renderResponse.createRenderURL();
	//userUrl.setParameter("mvcPath", "/view_user.jsp");

%>

<p>
<portlet:defineObjects/>

<liferay-ui:search-container

		total='<%= userLocalService.getUsersCount() %>'
		delta="10">
	<liferay-ui:search-container-results

			results="<%= userLocalService.getUsers(searchContainer.getStart(), searchContainer.getEnd())
			 %>"
	/>


	<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			escapedModel="true"
			modelVar="user"
			keyProperty="userId"
	>
		<portlet:renderURL var="clickRenderURL">
			<portlet:param name="backURL" value="<%= currentURL %>"/>
			<portlet:param name="mvcRenderCommandName" value="/render"/>
			<portlet:param name="id" value="<%= String.valueOf(user.getUserId())%>"/>
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
				value="${user.userId}"
				name="Пользователь"
				valign="top"

		/>

		<liferay-ui:search-container-column-text
				name="Пользователь"
				valign="top"
				href="<%=clickRenderURL%>"
		>

			<%
				//userUrl.setParameter("userId", String.valueOf(user.getUserId()));
			%>
			<strong><%= user.getFullName() %></strong>

			<br />

			<div class="lfr-asset-categories">
				<liferay-ui:asset-categories-summary
						className="<%= User.class.getName() %>"
						classPK="<%= user.getUserId() %>"
				/>
			</div>

			<div class="lfr-asset-tags">
				<liferay-ui:asset-tags-summary
						className="<%= User.class.getName() %>"
						classPK="<%= user.getUserId() %>"
						message="tags"
				/>
			</div>
		</liferay-ui:search-container-column-text>


		<liferay-ui:search-container-column-text
				name="Roles"
				valign="top"
				value="<%=user.getRoles().stream().map(Role::getName).collect(Collectors.joining("<br>"))%>"
		/>

	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>



</p>