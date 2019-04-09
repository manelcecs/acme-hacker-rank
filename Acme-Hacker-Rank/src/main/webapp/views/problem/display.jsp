<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<acme:button code="problem.display.back" type="button" url="/problem/company/list.do"/>

<acme:text label="problem.display.title" value="${problem.title}"/>
<acme:text label="problem.display.statement" value="${problem.statement}"/>
<acme:text label="problem.display.hint" value="${problem.hint}"/>
<acme:text label="problem.display.position" value="${problem.position.title}"/>

<p><strong><spring:message code="problem.display.attachments" />:</strong> </p> 
<ul>
	<jstl:forEach var="attachment" items="${problem.attachments}">
	<li><jstl:out value="${attachment}"/></li>
	</jstl:forEach>
</ul>

<p><strong><spring:message code="problem.display.status" />:</strong>  

<jstl:choose>
	<jstl:when test="${problem.draft}">
		<spring:message code="problem.display.draft"/>
	</jstl:when>
	<jstl:when test="${!problem.draft}">
		<spring:message code="problem.display.final"/>
	</jstl:when>
</jstl:choose>
</p>

