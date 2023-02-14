<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.time.format.FormatStyle" %>

<%@ page import="testportlet.util.DateTimeUtils" %>
<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.kernel.model.*" %>


<%@ include file="/init.jsp" %>

<%
	PortletURL userInfoURL = renderResponse.createRenderURL();
	userInfoURL.setParameter("mvcPath", "/view_user.jsp");

	Boolean isGuest = realUser.getRoles().stream().map(Role::getName).collect(Collectors.toList()).contains("Guest");
	System.out.println(isGuest);
	User finalCurrentUser = realUser;
	List<User> users = userLocalService.getUsers(0, userLocalService.getUsersCount())
			.stream()
			.filter(it -> it.getRoles()
					.stream()
					.allMatch(role1 -> finalCurrentUser.getRoles()
							.stream()
							.anyMatch(role1::equals)))
			.collect(Collectors.toList());
%>

<p>
<liferay-ui:search-container

		total='<%= isGuest ? 0 : users.size() %>'
		delta="10">
	<%


	%>
	<liferay-ui:search-container-results

			results="<%= users
			 %>"
	/>

	<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			escapedModel="true"
			modelVar="user"
	>

		<liferay-ui:search-container-column-text
				value="${user.userId}"
				name="Пользователь"
				valign="top"

		/>

		<liferay-ui:search-container-column-text
				name="Пользователь"
				valign="top"
				href="<%= userInfoURL %>"
		>
			<portlet:renderURL var="userInfoURL">
				<portlet:param name="mvcRenderCommandName" value="/render"/>
			</portlet:renderURL>
			<%
				userInfoURL.setParameter("userId", String.valueOf(user.getUserId()));
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
				name="Фамилия"
				property="lastName"
				valign="top"
		/>

		<liferay-ui:search-container-column-text
				name="Имя"
				property="firstName"
				valign="top"
		/>

		<liferay-ui:search-container-column-text
				name="Электронная почта"
				property="displayEmailAddress"
				valign="top"

		/>

		<liferay-ui:search-container-column-text
				name="Должность"
				property="jobTitle"
				valign="top"
		/>

		<liferay-ui:search-container-column-text
				name="Дата рождения"
				valign="top"
				value="<%= (user.getBirthday() == null ? " " : DateTimeUtils.dateToString(user.getBirthday(), FormatStyle.LONG)) %>"
		/>

		<liferay-ui:search-container-column-text
				name="Организации"
				valign="top"
				value='<%=user.getOrganizations().stream().map(Organization::getName).collect(Collectors.joining("<br>"))%>'
		/>

		<liferay-ui:search-container-column-text
				name="Телефоны"
				valign="top"
				value="<%=user.getPhones().stream().map(Phone::getNumber).collect(Collectors.joining("<br>"))%>"
		/>
		<liferay-ui:search-container-column-text
				name="Roles"
				valign="top"
				value="<%=user.getRoles().stream().map(Role::getName).collect(Collectors.joining("<br>"))%>"
		/>


	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>



</p>