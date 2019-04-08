<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table pagesize="5" name="problems" id="problem" requestURI="${requestURI}">
			<display:column titleKey="problem.list.title"><jstl:out value="${problem.title}"/></display:column>
			<display:column titleKey="problem.list.statement"><jstl:out value="${problem.statement}"/></display:column>
			<display:column titleKey="problem.list.position"><jstl:out value="${problem.position.title}"/></display:column>
		
			<jstl:if test="${company}">
				<display:column titleKey="problem.list.seeMore">
					<acme:button url="problem/company/display.do?idProblem=${problem.id}" type="button" code="problem.list.seeMore"/>
				</display:column>
								
				<display:column titleKey="problem.list.changeDraft">
					<jstl:if test="${problem.draft}">
						<acme:button url="problem/company/changeDraft.do?idProblem=${problem.id}" type="button" code="problem.list.changeDraft"/>
					</jstl:if>
				</display:column>

				<display:column titleKey="problem.list.edit">
					<jstl:if test="${problem.draft}">
						<acme:button url="problem/company/edit.do?idProblem=${problem.id}" type="button" code="problem.list.edit"/>
					</jstl:if>
				</display:column>

				<display:column titleKey="problem.list.delete">
					<jstl:if test="${problem.draft}">
						<acme:button url="problem/company/delete.do?idProblem=${problem.id}" type="button" code="problem.list.delete"/>
					</jstl:if>
				</display:column>
				
			</jstl:if>
			<jstl:if test="${viewAll}">
				<display:column titleKey="problem.list.seeMore">
					<acme:button url="problem/display.do?idProblem=${problem.id}" type="button" code="problem.list.seeMore"/>
				</display:column>
				
				<display:column titleKey="problem.list.company">
						<jstl:out value="${problem.position.company.companyName}"/>
				</display:column>
								
¡				<display:column titleKey="problem.list.viewCompany">
						<acme:button url="" type="button" code="problem.list.viewCompany"/>
				</display:column>
			</jstl:if>
</display:table>