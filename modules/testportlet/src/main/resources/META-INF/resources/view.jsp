<%@ page import="testportlet.service.TestPortletUserService" %>
<%@ page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %>
<%@ include file="/init.jsp" %>
<%
    //List<User> users = (List<User>) renderRequest.getAttribute("LIST_USERS");

%>

<liferay-ui:search-container
        emptyResultsMessage="No users"
        delta="3"
        deltaConfigurable="false"
        total="<%=TestPortletUserService.getUsersCount(renderRequest)%>"
>

    <liferay-ui:search-container-results
            results="<%=TestPortletUserService.getUsers(renderRequest, searchContainer.getStart(), searchContainer.getEnd())%>"
    />

    <liferay-ui:search-container-row
            className="com.liferay.portal.kernel.model.User"
            escapedModel="true"
            modelVar="user"
            keyProperty="userId"
    >
        <portlet:renderURL var="renderURL">
            <portlet:param name="mvcRenderCommandName" value="/render"/>
            <portlet:param name="redirect" value="<%= currentURL %>"/>
            <portlet:param name="userId" value="<%= String.valueOf(user.getUserId())%>"/>
        </portlet:renderURL>

        <liferay-ui:search-container-column-text
                valign="top"
                href="<%=renderURL%>"
        >

            <strong><%= user.getFullName() %>
            </strong>

        </liferay-ui:search-container-column-text>

    </liferay-ui:search-container-row>

    <liferay-ui:search-iterator/>
</liferay-ui:search-container>


