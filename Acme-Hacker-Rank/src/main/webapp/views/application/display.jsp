
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('HACKER')">
	<acme:button url="application/hacker/list.do" type="button" code="application.seeMore.back"/>
</security:authorize>

<security:authorize access="hasRole('COMPANY')">
		<acme:button url="application/company/list.do" type="button" code="application.seeMore.back"/>
	<jstl:if test="${application.status == 'SUBMITTED'}">
		<acme:button code="application.seeMore.changeAccepted" type="button" url="application/company/changeAccepted.do?idApplication=${application.id}"/>
		<acme:button code="application.seeMore.changeRejected" type="button" url="application/company/changeRejected.do?idApplication=${application.id}"/>
	</jstl:if>


</security:authorize>


<acme:text label="application.seeMore.moment" value="${application.moment}"/>
<security:authorize access="hasRole('HACKER')">
	<acme:button code="application.seeMore.curricula" type="button" url="curricula/hacker/display.do?curriculaId=${application.curricula.id}"/>
</security:authorize>
<security:authorize access="hasRole('COMPANY')">
	<acme:button code="application.seeMore.curricula" type="button" url="curricula/company/display.do?curriculaId=${application.curricula.id}"/>
</security:authorize>

<acme:text label="application.seeMore.status" value="${application.status}"/>


<h3><spring:message code="application.seeMore.problem"/></h3>

<acme:text label="application.seeMore.problem.title" value="${application.problem.title}"/>
<acme:text label="application.seeMore.problem.statement" value="${application.problem.statement}"/>
<acme:text label="application.seeMore.problem.hint" value="${application.problem.hint}"/>
<acme:text label="application.seeMore.problem.position" value="${application.problem.position.title}"/>

<jstl:if test="${existsAnswer}">
	<h3><spring:message code="application.seeMore.answer"/></h3>
	<acme:text label="application.seeMore.momentSubmit" value="${application.momentSubmit}"/>
	<acme:text label="application.seeMore.answer.explanation" value="${answer.explanation}"/>
	<acme:text label="application.seeMore.answer.link" value="${answer.link}"/>
</jstl:if>

