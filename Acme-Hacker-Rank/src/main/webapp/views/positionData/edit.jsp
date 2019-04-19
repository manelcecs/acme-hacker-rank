<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form modelAttribute="positionData" action="positionData/hacker/save.do">
	<acme:hidden path="id"/>
	<acme:hidden path="version" />
	<acme:hidden path="curricula" />
	
	<acme:textbox code="positionData.edit.title" path="title"/>
	<acme:textbox code="positionData.edit.description" path="description"/>
	<acme:inputDate code="positionData.edit.startDate" path="startDate"/>
	<acme:inputDate code="positionData.edit.endDate" path="endDate"/>
	
	<spring:message code="positionData.edit.submit" var="submit" />
	<input type="submit" name="submit" value="${ submit}" />
	<acme:cancel url="/curricula/hacker/display.do?curriculaId=${positionData.curricula.id}" code="positionData.edit.cancel" />
</form:form>