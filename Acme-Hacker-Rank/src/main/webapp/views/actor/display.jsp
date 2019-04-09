<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${actor.userAccount == userLogged}">

	<acme:button url="actor/edit.do" type="button" code="actor.edit" />
	<br />
</jstl:if>


<jstl:choose>
	<jstl:when test="${authority == 'COMPANY'}">

		<b><spring:message code="actor.name" /></b>:
		<jstl:out value="${company.name }" />
		<br />
		<b><spring:message code="actor.surname" /></b>:
		<jstl:forEach items="${company.surnames }" var="surname">
			<jstl:out value="${surname }" />
		</jstl:forEach>
		<br />
		<b><spring:message code="actor.photo" /></b>:
		<jstl:out value="${company.photo }" />
		<br />
		<b><spring:message code="actor.email" /></b>:
		<jstl:out value="${company.email }" />
		<br />
		<b><spring:message code="actor.address" /></b>:
		<jstl:out value="${company.address }" />
		<br />
		<b><spring:message code="actor.phoneNumber" /></b>:
		<jstl:out value="${company.phoneNumber }" />
		<br />
		<b><spring:message code="company.companyName" /></b>:
		<jstl:out value="${company.companyName }" />

		<br />
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<b><spring:message code="actor.spammer" /></b>
			<jstl:if test="${ company.spammer eq null}">
				<jstl:out value="N/A" />
			</jstl:if>
			<jstl:if test="${ company.spammer != null }">
				<jstl:out value="${company.spammer }" />
			</jstl:if>

		</security:authorize>

		<br />

	</jstl:when>



	<jstl:when test="${authority == 'ADMINISTRATOR'}">
		<b><spring:message code="actor.name" /></b>:
		<jstl:out value="${administrator.name }" />
		<br />
		<b><spring:message code="actor.surname" /></b>:
		<jstl:forEach items="${administrator.surnames }" var="surname">
			<jstl:out value="${surname }" />
		</jstl:forEach>
		<br />
		<b><spring:message code="actor.photo" /></b>:
		<jstl:out value="${administrator.photo }" />
		<br />
		<b><spring:message code="actor.email" /></b>:
		<jstl:out value="${administrator.email }" />
		<br />
		<b><spring:message code="actor.address" /></b>:
		<jstl:out value="${administrator.address }" />
		<br />
		<b><spring:message code="actor.phoneNumber" /></b>:
		<jstl:out value="${administrator.phoneNumber }" />
		<br />

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<b><spring:message code="actor.spammer" /></b>
			<jstl:if test="${ administrator.spammer eq null}">
				<jstl:out value="N/A" />
			</jstl:if>
			<jstl:if test="${ administrator.spammer != null }">
				<jstl:out value="${administrator.spammer }" />
			</jstl:if>
		</security:authorize>


		<br />

	</jstl:when>

	<jstl:when test="${authority == 'HACKER'}">
		<b><spring:message code="actor.name" /></b>:
		<jstl:out value="${hacker.name }" />
		<br />
		<b><spring:message code="actor.surname" /></b>:
		<jstl:forEach items="${hacker.surnames }" var="surname">
			<jstl:out value="${surname }" />
		</jstl:forEach>
		<br />
		<b><spring:message code="actor.photo" /></b>:
		<jstl:out value="${hacker.photo }" />
		<br />
		<b><spring:message code="actor.email" /></b>:
		<jstl:out value="${hacker.email }" />
		<br />
		<b><spring:message code="actor.address" /></b>:
		<jstl:out value="${hacker.address }" />
		<br />
		<b><spring:message code="actor.phoneNumber" /></b>:
		<jstl:out value="${hacker.phoneNumber }" />

		<br />
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<b><spring:message code="actor.spammer" /></b>
			<jstl:if test="${ hacker.spammer eq null}">
				<jstl:out value="N/A" />
			</jstl:if>
			<jstl:if test="${ hacker.spammer != null }">
				<jstl:out value="${hacker.spammer }" />
			</jstl:if>
		</security:authorize>

		<br />

	</jstl:when>
</jstl:choose>

<!-- Social profiles table -->
<b><spring:message code="actor.socialProfile" /></b>
<display:table name="${socialProfiles}" id="profile">
	<display:column titleKey="actor.socialProfiles.name">
		<jstl:out value="${ profile.nick}" />
	</display:column>
	<display:column titleKey="actor.socialProfiles.network">
		<jstl:out value="${profile.nameSocialNetwork}" />
	</display:column>
	<display:column titleKey="actor.socialProfiles.link">
		<jstl:out value="${ profile.link}" />
	</display:column>
	<display:column titleKey="actor.socialProfiles.edit">
		<acme:button url="/socialProfile/edit.do?socialProfileId=${profile.id}" type="button" code="actor.socialProfiles.edit"/>
	</display:column>
	<display:column titleKey="actor.socialProfiles.delete">
		<acme:button url="/socialProfile/delete.do?socialProfileId=${profile.id}" type="button" code="actor.socialProfiles.delete"/>
	</display:column>
</display:table>
<br />


<jstl:if test="${actor.userAccount == userLogged}">
	<acme:button url="socialProfile/create.do" type="button"
		code="actor.socialProfile.create" />
</jstl:if>