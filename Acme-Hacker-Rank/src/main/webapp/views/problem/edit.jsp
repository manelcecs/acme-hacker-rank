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

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form modelAttribute="problem" action="problem/company/edit.do">
		<acme:hidden path="id"/>
		<acme:hidden path="version"/>
		
		<p>
			<acme:textbox code="problem.edit.title" path="title"/>
		</p>
		<p>
			<acme:textarea code="problem.edit.statement" path="statement"/>
		</p>
		<p>
			<acme:textarea code="problem.edit.hint" path="hint"/>
		</p>
		
		<acme:select items="${positions}" itemLabel="title" code="problem.edit.positions" path="position"/>

		
		<form:label class="textboxLabel" path="attachments"><spring:message code="problem.edit.attachments" /></form:label>
    	<div id="attachments">
    		<form:input class="textbox" path="attachments" type="text"/>    
   		 </div>
    	<form:errors path="attachments" cssClass="error" />   
    	    	
		<acme:checkbox code="problem.edit.draft" path="draft"/>	
		
		<acme:submit name="save" code="problem.edit.save"/>
		<acme:cancel url="problem/company/list.do" code="problem.edit.back"/>
	</form:form>
	
	<button class="addTag" onclick="addComment('attachments','attachments', 'textbox')"><spring:message code="problem.edit.addAttachments" /></button>
	
