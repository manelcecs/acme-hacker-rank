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

<jstl:if test="${!show}">
	<acme:button url="/curricula/company/list.do" code="curricula.back" type="button" />
</jstl:if>
<br />
<b><spring:message code="curricula.personalData" /></b>
<br />
<b><spring:message code="curricula.personalData.fullName" />: </b><jstl:out value="${personalData.fullName }" />
<br />
<b><spring:message code="curricula.personalData.statement" />: </b><jstl:out value="${personalData.statement }" />
<br />
<b><spring:message code="curricula.personalData.phoneNumber" />: </b><jstl:out value="${personalData.phoneNumber }" />
<br />
<b><spring:message code="curricula.personalData.gitHubProfile" />: </b><jstl:out value="${personalData.gitHubProfile }" />
<br />
<b><spring:message code="curricula.personalData.linkedinProfile" />: </b><jstl:out value="${personalData.linkedinProfile }" />
<br />

<jstl:if test="${show }">
	<button
		onClick="window.location.href='personalData/hacker/edit.do?id=${personalData.id }'">
		<spring:message code="curricula.edit" />
	</button>
</jstl:if>
<br />
<br />
<hr>

<b><spring:message code="curricula.educationData" /></b>
<display:table name="${educationsData}" id="educationData">
	<display:column titleKey="curricula.educationData.degree" ><jstl:out value="${educationData.degree }" /></display:column> 
	<display:column titleKey="curricula.educationData.institution" ><jstl:out value="${educationData.institution}" /></display:column>
	<display:column titleKey="curricula.educationData.mark" ><jstl:out value="${educationData.mark}" /></display:column>
	<display:column titleKey="curricula.educationData.startDate" ><jstl:out value="${educationData.startDate}" /></display:column>
	<display:column titleKey="curricula.educationData.endDate" ><jstl:out value="${educationData.endDate}" /></display:column>
	<display:column>
		<button
			onClick="window.location.href='educationData/hacker/display.do?id=${educationData.id }'">
			<spring:message code="curricula.seeMore" />
		</button>
	</display:column>

	<display:column>
		<jstl:if test="${show }">
			<button
				onClick="window.location.href='educationData/hacker/edit.do?id=${educationData.id }'">
				<spring:message code="curricula.edit" />
			</button>
		</jstl:if>
	</display:column>

	<display:column>
		<jstl:if test="${show }">
			<button
				onClick="window.location.href='educationData/hacker/delete.do?id=${educationData.id }'">
				<spring:message code="curricula.delete" />
			</button>
		</jstl:if>
	</display:column>

</display:table>
<jstl:if test="${show }">
	<button
		onClick="window.location.href='educationData/hacker/create.do?curriculaId='${curricula.id}">
		<spring:message code="curricula.create" />
	</button>
</jstl:if>
<hr>
<b><spring:message code="curricula.miscellaneousData" /></b>
<display:table name="${miscellaneousData}" id="miscData">
	<display:column property="text" ><jstl:out value="${miscData.text }" /></display:column>
	<display:column property="attachments" >
		<jstl:forEach items="${miscData.attachments }" var="attachment">
			<jstl:out value="attachment" />
		</jstl:forEach>
	</display:column>
	<display:column>
		<button
			onClick="window.location.href='miscellaneousData/hacker/display.do?id=${miscData.id }'">
			<spring:message code="curricula.seeMore" />
		</button>
	</display:column>
	<display:column>
		<jstl:if test="${show }">
			<button
				onClick="window.location.href='miscellaneousData/hacker/edit.do?id=${miscData.id }'">
				<spring:message code="curricula.edit" />
			</button>
		</jstl:if>
	</display:column>
	<display:column>
		<jstl:if test="${show }">
			<button
				onClick="window.location.href='miscellaneousData/hacker/delete.do?id=${miscData.id }'">
				<spring:message code="curricula.delete" />
			</button>
		</jstl:if>
	</display:column>
</display:table>
<jstl:if test="${show }">
	<button
		onClick="window.location.href='miscellaneousData/hacker/create.do?curriculaId='${curricula.id}">
		<spring:message code="curricula.create" />
	</button>
</jstl:if>
<hr>
<b><spring:message code="curricula.positionData" /></b>
<display:table name="${positionsData}" id="postionData">
	<display:column property="title" ><jstl:out value="${postionData.title }" /></display:column>
	<display:column property="description" ><jstl:out value="${postionData.description }" /></display:column>
	<display:column property="startDate" > <jstl:out value="${postionData.startDate }" /></display:column>
	<display:column property="endDate" > <jstl:out value="${postionData.endDate }" /></display:column>
	<display:column>
		<button
			onClick="window.location.href='postionData/hacker/display.do?id=${postionData.id }'">
			<spring:message code="curricula.seeMore" />
		</button>
	</display:column>
	<display:column>
		<jstl:if test="${show }">
			<button
				onClick="window.location.href='postionData/hacker/edit.do?id=${postionData.id }'">
				<spring:message code="curricula.edit" />
			</button>
		</jstl:if>
	</display:column>
	<display:column>
		<jstl:if test="${show }">
			<button
				onClick="window.location.href='postionData/hacker/delete.do?id=${postionData.id }'">
				<spring:message code="curricula.delete" />
			</button>
		</jstl:if>
	</display:column>
</display:table>
<jstl:if test="${show }">
	<button
		onClick="window.location.href='postionData/hacker/create.do?curriculaId='${curricula.id}">
		<spring:message code="curricula.create" />
	</button>
</jstl:if>
