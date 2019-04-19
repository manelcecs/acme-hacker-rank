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

<script type='text/javascript'>
	function checkPhone(str) {
		if (str != "") {
			var patt = new RegExp("^(\[+][1-9][0-9]{0,2}[ ]{1}\[(][1-9][0-9]{0,2}\[)][ ]{1}[0-9]{4,}|\[+][1-9][0-9]{0,2}[ ]{1}[0-9]{4,}|[0-9]{4,}|[ ]{1,})$");
			if (patt.test(str) == false) { return confirm("<spring:message code="personalData.edit.phoneNumber.error"/>"); }
		}
	}
</script>


<form:form modelAttribute="personalData" action="personalData/hacker/save.do">
	<acme:hidden path="id"/>
	<acme:hidden path="version" />
	<acme:hidden path="curricula" />
	
	<acme:textbox code="personalData.edit.fullName" path="fullName"/>
	<acme:textbox code="personalData.edit.statement" path="statement"/>
	<acme:textbox code="personalData.edit.phoneNumber" path="phoneNumber"/>
	<acme:textbox code="personalData.edit.gitHubProfile" path="gitHubProfile"/>
	<acme:textbox code="personalData.edit.linkedinProfile" path="linkedinProfile"/>
	
	<spring:message code="personalData.edit.submit" var="submit" />
	<input type="submit" name="submit" value="${ submit}" onclick="return checkPhone(this.form.phoneNumber.value)"/>
	<acme:cancel url="/curricula/hacker/display.do?curriculaId=${personalData.curricula.id}" code="personalData.edit.cancel" />
</form:form>