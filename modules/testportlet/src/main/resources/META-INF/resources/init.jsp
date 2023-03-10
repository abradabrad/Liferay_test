<%@ page import="com.liferay.portal.kernel.model.User" %>
<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="testportlet.util.DateTimeUtils" %>
<%@ page import="java.util.Collections" %>
<%@ page import="testportlet.service.TestPortletUserService" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    PortletURL portletURL = renderResponse.createRenderURL();
    String currentURL = portletURL.toString();
%>