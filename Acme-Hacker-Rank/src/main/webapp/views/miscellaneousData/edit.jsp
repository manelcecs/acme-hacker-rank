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

<form:form modelAttribute="miscellaneousData"
	action="miscellaneousData/hacker/save.do">
	<acme:hidden path="id" />
	<acme:hidden path="version" />
	<acme:hidden path="curricula" />

	<acme:textbox code="miscellaneousData.edit.text" path="text" />

	<form:label class="textboxLabel" path="attachments">
		<spring:message code="miscellaneousData.edit.attachments" />
	</form:label>
	<div id="attachments">
		<jstl:forEach items="${miscellaneousData.attachments }" var="attachment">
			<form:input class="textbox" path="attachments" type="text"
				value="${ attachment}" />
		</jstl:forEach>
	</div>
	<form:errors path="attachments" cssClass="error" />

	<spring:message code="miscellaneousData.edit.submit" var="submit" />
	<input type="submit" name="submit" value="${ submit}" />
	<acme:cancel url="/" code="miscellaneousData.edit.cancel" />
</form:form>
<button class="addTag"
	onclick="addComment('attachments','attachments', 'textbox')">
	<spring:message code="miscellaneousData.edit.attachments.add" />
</button>
