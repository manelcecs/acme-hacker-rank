<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<acme:button code="application.create.cancel" type="button" url="application/hacker/list.do"/>

<form:form modelAttribute="applicationForm" action="application/hacker/save.do" method="post">
	
	<acme:select items="${positions}" itemLabel="title" code="application.create.position" path="position"/>
	
	<acme:select items="${curriculas}" itemLabel="title" code="application.create.curricula" path="curricula"/>

	<acme:submit name="save" code="application.create.save"/>
</form:form>

