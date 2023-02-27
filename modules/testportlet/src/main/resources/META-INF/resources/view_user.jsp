<%@ page import="testportlet.util.DateTimeUtils" %>
<%@ page import="com.liferay.portal.kernel.model.Organization" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="com.liferay.portal.kernel.model.Phone" %>
<%@ page import="java.util.List" %>


<%@ include file="/init.jsp" %>

<p>

<liferay-ui:search-container total='1'>
	<liferay-ui:search-container-results
			results="<%= (List<User>) renderRequest.getAttribute("USER")%>"/>

	<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			escapedModel="true"
			modelVar="user"
	>

		<liferay-ui:search-container-column-text
				value="${user.userId}"
				name="Id"
				valign="top"
		/>

		<liferay-ui:search-container-column-text
				name="Пользователь"
				valign="top">
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
				value="<%= DateTimeUtils.dateToString(user.getBirthday(), "dd-MM-yyyy г.") %>"
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

	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

	<aui:button-row>
		<aui:button href="#" onClick="history.go(-1) " value='Back' type="Back" />
	</aui:button-row>

</p>