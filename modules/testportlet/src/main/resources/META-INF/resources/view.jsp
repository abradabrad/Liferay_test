<%@ page import="java.util.List" %>
<%@ include file="/init.jsp" %>
<%
    List<User> users = (List<User>) renderRequest.getAttribute("LIST_USERS");
%>
<p>
<liferay-ui:search-container

        total='<%= userLocalService.getUsersCount() %>'
>
    <liferay-ui:search-container-results
            results="<%= users%>"
    />

    <liferay-ui:search-container-row
            className="com.liferay.portal.kernel.model.User"
            escapedModel="true"
            modelVar="user"
            keyProperty="userId"
    >
        <portlet:renderURL var="renderURL">
            <portlet:param name="backURL" value="<%= currentURL %>"/>
            <portlet:param name="mvcRenderCommandName" value="/render"/>
            <portlet:param name="userId" value="<%= String.valueOf(user.getUserId())%>"/>
        </portlet:renderURL>

        <liferay-ui:search-container-column-text
                name="Пользователь"
                valign="top"
                href="<%=renderURL%>"
        >

            <strong><%= user.getFullName() %>
            </strong>
            <br/>
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

    </liferay-ui:search-container-row>

    <liferay-ui:search-iterator/>
</liferay-ui:search-container>


</p>