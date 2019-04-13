<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<section>

		<form:form id="search" action="search/display.do" modelAttribute="searchForm" method="post">	
			<acme:textbox code="search.keyword" path="keyword" />
			<acme:submit name="save" code="search.save" />
		</form:form>

	</section>
	
	<section>
	
		<display:table pagesize="10" name="positions" id="position" requestURI="${requestURI}">
			<display:column titleKey="search.position.ticker"> <jstl:out value="${position.ticker.identifier}"/>
			</display:column>
			<display:column titleKey="search.position.company.companyName"><jstl:out value="${position.company.companyName}"/></display:column>
			<display:column titleKey="search.position.title"><jstl:out value="${position.title}"/>
			</display:column>
			<display:column titleKey="search.position.description"><jstl:out value="${position.description}"/></display:column>
			<display:column titleKey="search.position.deadline"><jstl:out value="${position.deadline}"/></display:column>
			<display:column titleKey="search.position.profileRequired"><jstl:out value="${position.profileRequired}"/></display:column>
			<display:column titleKey="search.position.skillsRequired">
				<jstl:forEach var="skill" items="${position.skillsRequired}" >
					<jstl:out value="- ${skill}"/>
					<br/>
				</jstl:forEach>
			</display:column>
			<display:column titleKey="search.position.technologiesRequired">
				<jstl:forEach var="technology" items="${position.technologiesRequired}" >
					<jstl:out value="- ${technology}"/>
					<br/>
				</jstl:forEach>
			</display:column>
			<display:column titleKey="search.minimumSalary"><jstl:out value="${position.salaryOffered}"/></display:column>
		
		</display:table>
	
	</section>

