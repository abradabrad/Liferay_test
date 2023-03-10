<%@ include file="/init.jsp" %>

<%
    String redirect = ParamUtil.getString(request, "redirect");
%>
<aui:form action="<%= (javax.portlet.ActionURL)renderResponse.createActionURL() %>" method="post">
    <aui:input name="redirect" type="hidden" value="<%= currentURL %>"/>

    <liferay-ui:search-container total='1'>
        <liferay-ui:search-container-results
                results='<%= Collections.singletonList(renderRequest.getAttribute("USER"))%>'/>

        <liferay-ui:search-container-row
                className="com.liferay.portal.kernel.model.User"
                escapedModel="true"
                modelVar="user"
        >

            <liferay-ui:search-container-column-text
                    value="${user.userId}"
                    name="user-id"
                    valign="top"
            />

            <liferay-ui:search-container-column-text
                    name="user"
                    valign="top">
                <strong><%= user.getFullName() %>
                </strong>
            </liferay-ui:search-container-column-text>

            <liferay-ui:search-container-column-text
                    name="last-name"
                    property="lastName"
                    valign="top"
            />

            <liferay-ui:search-container-column-text
                    name="first-name"
                    property="firstName"
                    valign="top"
            />

            <liferay-ui:search-container-column-text
                    name="emails"
                    property="displayEmailAddress"
                    valign="top"
            />

            <liferay-ui:search-container-column-text
                    name="job-title"
                    property="jobTitle"
                    valign="top"
            />

            <liferay-ui:search-container-column-text
                    name="birthday"
                    valign="top"
                    value="<%= DateTimeUtils.dateToString(user.getBirthday(), "dd-MM-yyyy г.") %>"
            />

            <liferay-ui:search-container-column-text
                    name="organizations"
                    valign="top"
                    value='<%=testPortletUserService.getOrganizations(user)%>'
            />

            <liferay-ui:search-container-column-text
                    name="phones"
                    valign="top"
                    value="<%=testPortletUserService.getPhones(user)%>"
            />

        </liferay-ui:search-container-row>

        <liferay-ui:search-iterator/>
    </liferay-ui:search-container>

    <aui:button-row>
        <aui:button href="<%= redirect %>" type="back" value="Back"/>
    </aui:button-row>

</aui:form>