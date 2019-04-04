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


<section>

	<form:form modelAttribute="adminConfigForm" action="adminConfig/administrator/save.do" method="Post">
	
		<acme:inputNumber code="finder.edit.cacheFinder" path="cacheFinder"/>
		<acme:inputNumber code="finder.edit.resultsFinder" path="resultsFinder"/>
		<acme:textbox code="finder.edit.systemName" path="systemName"/>
		<acme:textbox code="finder.edit.welcomeMessageEN" path="welcomeMessageEN"/>
		<acme:textbox code="finder.edit.welcomeMessageES" path="welcomeMessageES"/>
		<acme:textbox code="finder.edit.countryCode" path="countryCode"/>
		<acme:textbox code="finder.edit.bannerURL" path="bannerURL"/>
		<acme:textbox code="finder.edit.spamWord" path="spamWord"/>
		
		<acme:submit name="save" code="finder.edit.save"/>
		<acme:button url="adminConfig/administrator/display.do" type="button" code="finder.edit.back"/>

	</form:form>

	<display:table name="spamWords" id="spamWord" requestURI="${requestURI}" pagesize="5" >
		<display:column titleKey="finder.display.spamWords" ><acme:text value="${spamWord}"/></display:column>
		<display:column>
			<acme:deleteWithForm  url="adminConfig/administrator/deleteSpamWord.do" name="spamWord" id="${spamWord}" code="finder.edit.delete"/>
		</display:column>
	</display:table>


</section>



