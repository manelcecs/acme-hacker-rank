<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="educationData"
	action="educationData/hacker/save.do">
	<acme:hidden path="id"/>
	<acme:hidden path="version" />
	<acme:hidden path="curricula" />
	<acme:textbox code="educationData.edit.degree" path="degree" />
	<acme:textbox code="educationData.edit.institution" path="institution" />
	<acme:textbox code="educationData.edit.mark" path="mark" />
	<acme:inputDate code="educationData.edit.startDate" path="startDate"/>
	<acme:inputDate code="educationData.edit.endDate" path="endDate"/>
	<spring:message code="educationData.edit.submit" var="submit" />
	<input type="submit" name="submit" value="${ submit}" />
	<acme:cancel url="/curricula/hacker/display.do?curriculaId=${educationData.curricula.id}" code="educationData.edit.cancel" />
</form:form>