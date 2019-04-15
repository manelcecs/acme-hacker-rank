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

<display:table name="${curriculas }" id="curricula">
	<display:column>
		<!--<jstl:out value="curricula"></jstl:out>-->
	</display:column>
	<display:column>
		<button
			onClick="window.location.href='curricula/hacker/display.do?curriculaId=${curricula.id }'">
			<spring:message code="curricula.seeMore" />
		</button>
	</display:column>
	<jstl:if test="${show }">
		<display:column>
			<button
				onClick="window.location.href='curricula/hacker/delete.do?curriculaId=${curricula.id }'">
				<spring:message code="curricula.delete" />
			</button>
		</display:column>
	</jstl:if>
</display:table>
<jstl:if test="${show }">
	<button onClick="window.location.href='curricula/hacker/create.do'">
		<spring:message code="curricula.create" />
	</button>
</jstl:if>