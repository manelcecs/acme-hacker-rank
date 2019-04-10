<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<security:authorize access="hasRole('COMPANY')"><acme:button code="position.seeMore.back" type="button" url="/position/company/list.do"/></security:authorize>
<security:authorize access="not hasRole('COMPANY')"><acme:button code="position.seeMore.back" type="button" url="/position/list.do"/></security:authorize>

<acme:text label="position.seeMore.title" value="${position.title}"/>
<acme:text label="position.seeMore.description" value="${position.description}"/>
<acme:text label="position.seeMore.deadline" value="${position.deadline}"/>
<acme:text label="position.seeMore.profileRequired" value="${position.profileRequired}"/>
<acme:text label="position.seeMore.salaryOffered" value="${position.salaryOffered}"/>
<acme:text label="position.seeMore.ticker" value="${position.ticker.identifier}"/>

<p><strong><spring:message code="position.seeMore.status" />:</strong>  

<jstl:choose>
	<jstl:when test="${position.cancelled}">
		<spring:message code="position.seeMore.cancelled"/>
	</jstl:when>
	<jstl:when test="${!position.cancelled && position.draft}">
		<spring:message code="position.seeMore.draft"/>
	</jstl:when>
	<jstl:when test="${!position.cancelled && !position.draft}">
		<spring:message code="position.seeMore.final"/>
	</jstl:when>
</jstl:choose>
</p>

<p><strong><spring:message code="position.seeMore.skillsRequired" />:</strong>  
<ul>
	<jstl:forEach var="skill" items="${position.skillsRequired}">
	<li><jstl:out value="${skill}"/></li>
	</jstl:forEach>
</ul>

<p><strong><spring:message code="position.seeMore.technologiesRequired" />:</strong>  
<ul>
	<jstl:forEach var="technology" items="${position.technologiesRequired}">
	<li><jstl:out value="${technology}"/></li>
	</jstl:forEach>
</ul>