<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
    uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${!curricula.copy }">
    <jstl:if test="${!show}">
   	 <acme:button url="/curricula/company/list.do" code="curricula.back"
   		 type="button" />
    </jstl:if>
</jstl:if>


<h4><jstl:out value="${curricula.title}" /></h4>
<hr>

<jstl:if test="${personalData eq null }">
    <jstl:if test="${!show}">
   	 <acme:button url="/personalData/hacker/create.do" code="curricula.create"
   		 type="button" />
    </jstl:if>
    <jstl:if test="${show}">
   	 <b><spring:message code="curricula.display.nothing" /></b>
    </jstl:if>
</jstl:if>
<jstl:if test="${personalData ne null }">
<br />
<b><spring:message code="curricula.personalData" /></b>
<br />
<b><spring:message code="curricula.personalData.fullName" />: </b>
<jstl:out value="${personalData.fullName }" />
<br />
<b><spring:message code="curricula.personalData.statement" />: </b>
<jstl:out value="${personalData.statement }" />
<br />
<b><spring:message code="curricula.personalData.phoneNumber" />: </b>
<jstl:out value="${personalData.phoneNumber }" />
<br />
<b><spring:message code="curricula.personalData.gitHubProfile" />:
</b>
<jstl:out value="${personalData.gitHubProfile }" />
<br />
<b><spring:message code="curricula.personalData.linkedinProfile" />:
</b>
<jstl:out value="${personalData.linkedinProfile }" />
<br />
</jstl:if>

<jstl:if test="${!curricula.copy }">


    <jstl:if test="${show }">
   	 <jstl:if test="${personalData eq null }">
   		 <button
   			 onClick="window.location.href='personalData/hacker/create.do?curriculaId=${curricula.id }'">
   			 <spring:message code="curricula.create" />
   		 </button>
   	 </jstl:if>

   	 <jstl:if test="${personalData.id gt 0 }">
   		 <button
   			 onClick="window.location.href='personalData/hacker/edit.do?personalDataId=${personalData.id }'">
   			 <spring:message code="curricula.edit" />
   		 </button>
   	 </jstl:if>

    </jstl:if>
</jstl:if>
<br />
<br />
<hr>

<b><spring:message code="curricula.educationData" /></b>
<display:table name="${educationsData}" id="educationData">
    <display:column titleKey="curricula.educationData.degree">
   	 <jstl:out value="${educationData.degree }" />
    </display:column>
    <display:column titleKey="curricula.educationData.institution">
   	 <jstl:out value="${educationData.institution}" />
    </display:column>
    <display:column titleKey="curricula.educationData.mark">
   	 <jstl:out value="${educationData.mark}" />
    </display:column>
    <display:column titleKey="curricula.educationData.startDate">
   	 <jstl:out value="${educationData.startDate}" />
    </display:column>
    <display:column titleKey="curricula.educationData.endDate">
   	 <jstl:out value="${educationData.endDate}" />
    </display:column>
    <jstl:if test="${!curricula.copy }">


   	 <display:column>
   		 <jstl:if test="${show }">
   			 <button
   				 onClick="window.location.href='educationData/hacker/edit.do?educationDataId=${educationData.id }'">
   				 <spring:message code="curricula.edit" />
   			 </button>
   		 </jstl:if>
   	 </display:column>

   	 <display:column>
   		 <jstl:if test="${show }">
   			 <button
   				 onClick="window.location.href='educationData/hacker/delete.do?educationDataId=${educationData.id }'">
   				 <spring:message code="curricula.delete" />
   			 </button>
   		 </jstl:if>
   	 </display:column>
    </jstl:if>
</display:table>
<jstl:if test="${!curricula.copy }">


    <jstl:if test="${show }">
   	 <button
   		 onClick="window.location.href='educationData/hacker/create.do?curriculaId=${curricula.id}'">
   		 <spring:message code="curricula.create" />
   	 </button>
    </jstl:if>
</jstl:if>
<hr>
<b><spring:message code="curricula.miscellaneousData" /></b>
<display:table name="${miscellaneousData}" id="miscData">
    <display:column titleKey="curricula.miscellaneousData.text">
   	 <jstl:out value="${miscData.text }" />
    </display:column>
    <display:column titleKey="curricula.miscellaneousData.attachments">
   	 <jstl:forEach items="${miscData.attachments }" var="attachment">
   		 <jstl:out value="attachment" />
   	 </jstl:forEach>
    </display:column>
    <jstl:if test="${!curricula.copy }">


   	 <display:column>
   		 <jstl:if test="${show }">
   			 <button
   				 onClick="window.location.href='miscellaneousData/hacker/edit.do?miscellaneousDataId=${miscData.id }'">
   				 <spring:message code="curricula.edit" />
   			 </button>
   		 </jstl:if>
   	 </display:column>
   	 <display:column>
   		 <jstl:if test="${show }">
   			 <button
   				 onClick="window.location.href='miscellaneousData/hacker/delete.do?miscellaneousDataId=${miscData.id }'">
   				 <spring:message code="curricula.delete" />
   			 </button>
   		 </jstl:if>
   	 </display:column>
    </jstl:if>
</display:table>
<jstl:if test="${!curricula.copy }">


    <jstl:if test="${show }">
   	 <button
   		 onClick="window.location.href='miscellaneousData/hacker/create.do?curriculaId=${curricula.id}'">
   		 <spring:message code="curricula.create" />
   	 </button>
    </jstl:if>
</jstl:if>
<hr>
<b><spring:message code="curricula.positionData" /></b>
<display:table name="${positionsData}" id="positionData">
    <display:column titleKey="curricula.positionData.title">
   	 <jstl:out value="${positionData.title }" />
    </display:column>
    <display:column titleKey="curricula.positionData.description">
   	 <jstl:out value="${positionData.description }" />
    </display:column>
    <display:column titleKey="curricula.positionData.startDate">
   	 <jstl:out value="${positionData.startDate }" />
    </display:column>
    <display:column titleKey="curricula.positionData.endDate">
   	 <jstl:out value="${positionData.endDate }" />
    </display:column>
    <jstl:if test="${!curricula.copy }">


   	 <display:column>
   		 <jstl:if test="${show }">
   			 <button
   				 onClick="window.location.href='positionData/hacker/edit.do?postionDataId=${postionData.id }'">
   				 <spring:message code="curricula.edit" />
   			 </button>
   		 </jstl:if>
   	 </display:column>
   	 <display:column>
   		 <jstl:if test="${show }">
   			 <button
   				 onClick="window.location.href='positionData/hacker/delete.do?postionDataId=${postionData.id }'">
   				 <spring:message code="curricula.delete" />
   			 </button>
   		 </jstl:if>
   	 </display:column>
    </jstl:if>
</display:table>
<jstl:if test="${!curricula.copy }">


    <jstl:if test="${show }">
   	 <button
   		 onClick="window.location.href='positionData/hacker/create.do?curriculaId=${curricula.id}'">
   		 <spring:message code="curricula.create" />
   	 </button>
    </jstl:if>
</jstl:if>











