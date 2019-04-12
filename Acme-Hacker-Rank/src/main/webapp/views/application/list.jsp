<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<security:authorize access="hasRole('COMPANY')">
	<acme:button code="application.list.status.accepted" type="button" url="application/company/listByStatus.do?status=ACCEPTED"/>
	<acme:button code="application.list.status.rejected" type="button" url="application/company/listByStatus.do?status=REJECTED"/>
	<acme:button code="application.list.status.submitted" type="button" url="application/company/listByStatus.do?status=SUBMITTED"/>
	<acme:button code="application.list.status.all" type="button" url="application/company/list.do"/>
	
</security:authorize>

<security:authorize access="hasRole('HACKER')">
	<acme:button code="application.list.create" type="button"  url="application/hacker/create.do"/><br/>
	<acme:button code="application.list.status.accepted" type="button" url="application/hacker/listByStatus.do?status=ACCEPTED"/>
	<acme:button code="application.list.status.rejected" type="button" url="application/hacker/listByStatus.do?status=REJECTED"/>
	<acme:button code="application.list.status.submitted" type="button" url="application/hacker/listByStatus.do?status=SUBMITTED"/>
	<acme:button code="application.list.status.pending" type="button" url="application/hacker/listByStatus.do?status=PENDING"/>
	<acme:button code="application.list.status.all" type="button" url="application/company/list.do"/>
</security:authorize>


<display:table pagesize="5" requestURI="${requestURI}" name="applications" id="oneApplication">
	
	<display:column titleKey="application.list.position"><jstl:out value="${oneApplication.problem.position.title}"/></display:column>
	<display:column titleKey="application.list.problem"><jstl:out value="${oneApplication.problem.title}"/></display:column>
	<security:authorize access="hasRole('COMPANY')">
		<display:column titleKey="application.list.seeMore">
			<acme:button code="application.list.seeMore" type="button" url="application/company/display.do?idApplication=${oneApplication.id}"/>
		</display:column>
	</security:authorize>
	
	
	<security:authorize access="hasRole('HACKER')">
	<display:column titleKey="application.list.seeMore">
		<acme:button code="application.list.seeMore" type="button" url="application/hacker/display.do?idApplication=${oneApplication.id}"/>
	</display:column>
	
	
	<display:column titleKey="application.list.answer">
		<jstl:choose>
			<jstl:when test="${applicationsAnswered.contains(oneApplication)}">
				<spring:message code="application.list.answered"/>
			</jstl:when>
			
			<jstl:otherwise>
				<acme:button code="application.list.answer" type="button" url="answer/hacker/create.do?idApplication=${oneApplication.id}"/>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>

	</security:authorize>


</display:table>