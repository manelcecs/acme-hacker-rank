<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<jstl:if test="${owner}">
	<acme:button url="position/company/create.do" type="button" code="position.list.create"/>
</jstl:if>

<display:table pagesize="5" name="positions" id="position" requestURI="${requestURI}">
		<display:column titleKey="position.edit.title"><jstl:out value="${position.title}"/></display:column>
			<display:column titleKey="position.edit.deadline"><jstl:out value="${position.deadline}"/></display:column>
			<display:column titleKey="position.edit.salaryOffered"><jstl:out value="${position.salaryOffered}"/></display:column>
			<jstl:if test="${owner}">
				<!-- Columna de see more -->
				<display:column titleKey="position.list.seeMore">
					<acme:button url="position/company/display.do?idPosition=${position.id}" type="button" code="position.list.seeMore"/>
				</display:column>
								
				<!-- Columna de draft -->
				<display:column titleKey="position.list.changeDraft">
					<jstl:if test="${position.draft && positionsChangeDraft.contains(position)}">
						<acme:button url="position/company/changeDraft.do?idPosition=${position.id}" type="button" code="position.list.changeDraft"/>
					</jstl:if>
				</display:column>

				<!-- Columna de edit -->
				<display:column titleKey="position.list.edit">
					<jstl:if test="${position.draft}">
						<acme:button url="position/company/edit.do?idPosition=${position.id}" type="button" code="position.list.edit"/>
					</jstl:if>
				</display:column>

				<!-- Columna de delete-->
				<display:column titleKey="position.list.delete">
					<jstl:if test="${position.draft}">
						<acme:button url="position/company/delete.do?idPosition=${position.id}" type="button" code="position.list.delete"/>
					</jstl:if>
				</display:column>

				<!-- Columna de cancel -->
				<display:column titleKey="position.list.cancel">
					<jstl:if test="${!position.draft}">
						<acme:button url="position/company/changeCancellation.do?idPosition=${position.id}" type="button" code="position.list.cancel"/>
					</jstl:if>
				</display:column>
				
				
			</jstl:if>
			<jstl:if test="${viewAll}">
				<display:column titleKey="position.list.seeMore">
					<acme:button url="position/display.do?idPosition=${position.id}" type="button" code="position.list.seeMore"/>
				</display:column>
				
				<!-- Columna de info la company -->
				<display:column titleKey="position.list.company">
						<jstl:out value="${position.company.companyName}"/>
				</display:column>
								
				<!-- Columna de ver la company -->
				<display:column titleKey="position.list.viewCompany">
						<acme:button url="/" type="button" code="position.list.viewCompany"/>
				</display:column>

			
			</jstl:if>
</display:table>