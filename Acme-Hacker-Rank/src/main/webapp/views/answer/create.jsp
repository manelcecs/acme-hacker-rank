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

<acme:button code="answer.create.cancel" type="button" url="application/hacker/list.do"/>

<form:form modelAttribute="answer" action="answer/hacker/save.do" method="post">
	
	<acme:hidden  path="application"/>
	
	<acme:textarea code="answer.create.explanation" path="explanation"/>
	<acme:textbox code="answer.create.link" path="link"/>
	
	<acme:submit name="save" code="answer.create.save"/>
</form:form>