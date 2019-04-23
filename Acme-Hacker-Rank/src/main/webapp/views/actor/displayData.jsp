<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>




<hr>
	
	<acme:button url="actor/deleteData.do" type="button" code="actor.displayData.deleteAll"/>

<hr>

	<acme:button url="actor/saveData.do" type="button" code="actor.displayData.saveData"/>
<hr>




<div id="print">
<jstl:choose>
	<jstl:when test="${authority == 'HACKER'}">
		
		<acme:text value="${hacker.name}" label="actor.displayData.name"/>
		<acme:text value="${hacker.userAccount.username}" label="actor.displayData.username"/>
		<acme:text value="${hacker.surnames}" label="actor.displayData.surnames"/>
		<acme:text value="${hacker.photo}" label="actor.displayData.photo"/>
		<acme:text value="${hacker.email}" label="actor.displayData.email"/>
		<acme:text value="${hacker.address}" label="actor.displayData.address"/>
		<acme:text value="${hacker.phoneNumber}" label="actor.displayData.phoneNumber"/>
		<acme:text value="${hacker.vatNumber}" label="actor.displayData.vatNumber"/>

		

		
		<!-- Social profiles table -->
		<b><spring:message code="actor.displayData.socialProfile" /></b>
		<display:table name="${socialProfiles}" id="profile">
			<spring:message code="actor.displayData.socialProfilesName" var="name" />
			<display:column title="${name }">
				<jstl:out value="${ profile.name}" />
			</display:column>
			<spring:message code="actor.displayData.socialProfilesNetwork" var="network" />
			<display:column title="${network }">
				<jstl:out value="${profile.socialNetwork}" />
			</display:column>
			<spring:message code="actor.displayData.socialProfilesLink" var="link" />
			<display:column title="${link }">
				<jstl:out value="${ profile.link}" />
			</display:column>
		</display:table>
		
		<br>
		
		<!-- Table with messages -->
		<b><spring:message code="actor.displayData.messages" /></b>
		<display:table name="${messages}" id="message" >
			<display:column titleKey="actor.displayData.messageSender"><jstl:out value="${message.sender.email}"/></display:column>
			<display:column titleKey="actor.displayData.messageRecipients">
				<jstl:forEach var="recipient" items="${message.recipients}">
					<jstl:out value="${recipient}"/> 
				</jstl:forEach>
			</display:column>
			<display:column titleKey="actor.displayData.messageSubject"><jstl:out value="${message.subject}"/></display:column>
			<display:column titleKey="actor.displayData.messageMoment"><jstl:out value="${message.moment}"/></display:column>
			<display:column titleKey="actor.displayData.messageBody" ><jstl:out value="${message.body}"/></display:column>
			<display:column titleKey="actor.displayData.messagePriority"><jstl:out value="${message.priority}"/></display:column>
			<display:column titleKey="actor.displayData.messageTags">
				<jstl:forEach var="tag" items="${message.tags}">
					<jstl:out value="${tag}"/><br/>
				</jstl:forEach>
			</display:column>
		</display:table><br/>
		
		
		<!-- Table with cv -->
		
		<b><spring:message code="actor.displayData.personalData" /></b>
		<display:table name="${personalDatas}" id="personalData">
			<display:column titleKey="actor.displayData.cv.title"><jstl:out value="${personalData.curricula.title}"/></display:column>
			<display:column titleKey="actor.displayData.personalData.fullName"><jstl:out value="${personalData.fullName}"/></display:column>
			<display:column titleKey="actor.displayData.personalData.statement"><jstl:out value="${personalData.statement}"/></display:column>
			<display:column titleKey="actor.displayData.personalData.phoneNumber"><jstl:out value="${personalData.phoneNumber}"/></display:column>
			<display:column titleKey="actor.displayData.personalData.gitHubProfile"><jstl:out value="${personalData.gitHubProfile}"/></display:column>
			<display:column titleKey="actor.displayData.personalData.linkedinProfile"><jstl:out value="${personalData.linkedinProfile}"/></display:column>
		</display:table><br/>
		
		<b><spring:message code="actor.displayData.positionData" /></b>
		<display:table name="${positionDatas}" id="positionData">
			<display:column titleKey="actor.displayData.cv.title"><jstl:out value="${positionData.curricula.title}"/></display:column>
			<display:column titleKey="actor.displayData.positionData.title"><jstl:out value="${positionData.title}"/></display:column>
			<display:column titleKey="actor.displayData.positionData.description"><jstl:out value="${positionData.description}"/></display:column>
			<display:column titleKey="actor.displayData.positionData.startDate"><jstl:out value="${positionData.startDate}"/></display:column>
			<display:column titleKey="actor.displayData.positionData.endDate"><jstl:out value="${positionData.endDate}"/></display:column>
		</display:table><br/>
		
		<b><spring:message code="actor.displayData.educationData" /></b>
		<display:table name="${educationDatas}" id="educationData">
			<display:column titleKey="actor.displayData.cv.title"><jstl:out value="${personalData.curricula.title}"/></display:column>
			<display:column titleKey="actor.displayData.educationData.degree"><jstl:out value="${educationData.degree}"/></display:column>
			<display:column titleKey="actor.displayData.educationData.institution"><jstl:out value="${educationData.institution}"/></display:column>
			<display:column titleKey="actor.displayData.educationData.mark"><jstl:out value="${educationData.mark}"/></display:column>
			<display:column titleKey="actor.displayData.educationData.startDate"><jstl:out value="${educationData.startDate}"/></display:column>
			<display:column titleKey="actor.displayData.educationData.endDate"><jstl:out value="${educationData.endDate}"/></display:column>
		</display:table><br/>
		
		<b><spring:message code="actor.displayData.miscellaneousData" /></b>
		<display:table name="${miscellaneousDatas}" id="miscellaneousData">
			<display:column titleKey="actor.displayData.cv.title"><jstl:out value="${miscellaneousData.curricula.title}"/></display:column>
			<display:column titleKey="actor.displayData.miscellaneousData.text"><jstl:out value="${miscellaneousData.text}"/></display:column>
			<display:column titleKey="actor.displayData.miscellaneousData.attachments"><jstl:out value="${miscellaneousData.attachments}"/></display:column>
		</display:table><br/>
	
	
				
	</jstl:when>
	
	
	<jstl:when test="${authority == 'COMPANY'}">
		<acme:text value="${company.name}" label="actor.displayData.name"/>
		<acme:text value="${company.userAccount.username}" label="actor.displayData.username"/>
		<acme:text value="${company.surnames}" label="actor.displayData.surnames"/>
		<acme:text value="${company.photo}" label="actor.displayData.photo"/>
		<acme:text value="${company.email}" label="actor.displayData.email"/>
		<acme:text value="${company.address}" label="actor.displayData.address"/>
		<acme:text value="${company.phoneNumber}" label="actor.displayData.phoneNumber"/>
		<acme:text value="${company.vatNumber}" label="actor.displayData.vatNumber"/>

		

		
		<!-- Social profiles table -->
		<b><spring:message code="actor.displayData.socialProfile" /></b>
		<display:table name="${socialProfiles}" id="profile">
			<spring:message code="actor.displayData.socialProfilesName" var="name" />
			<display:column title="${name }">
				<jstl:out value="${ profile.name}" />
			</display:column>
			<spring:message code="actor.displayData.socialProfilesNetwork" var="network" />
			<display:column title="${network }">
				<jstl:out value="${profile.socialNetwork}" />
			</display:column>
			<spring:message code="actor.displayData.socialProfilesLink" var="link" />
			<display:column title="${link }">
				<jstl:out value="${ profile.link}" />
			</display:column>
		</display:table>
		
		<br>
		
		<!-- Table with messages -->
		<b><spring:message code="actor.displayData.messages" /></b>
		<display:table pagesize="5" name="${messages}" id="message" requestURI="actor/displayAllData.do">
			<display:column titleKey="actor.displayData.messageSender"><jstl:out value="${message.sender.email}"/></display:column>
			<display:column titleKey="actor.displayData.messageRecipients">
				<jstl:forEach var="recipient" items="${message.recipients}">
					<jstl:out value="${recipient}"/> 
				</jstl:forEach>
			</display:column>
			<display:column titleKey="actor.displayData.messageSubject"><jstl:out value="${message.subject}"/></display:column>
			<display:column titleKey="actor.displayData.messageMoment"><jstl:out value="${message.moment}"/></display:column>
			<display:column titleKey="actor.displayData.messageBody" ><jstl:out value="${message.body}"/></display:column>
			<display:column titleKey="actor.displayData.messagePriority"><jstl:out value="${message.priority}"/></display:column>
			<display:column titleKey="actor.displayData.messageTags">
				<jstl:forEach var="tag" items="${message.tags}">
					<jstl:out value="${tag}"/><br/>
				</jstl:forEach>
			</display:column>
		</display:table><br/>
	
	
	
	</jstl:when>
	
	<jstl:when test="${authority == 'ADMINISTRATOR'}">
		<acme:text value="${administrator.name}" label="actor.displayData.name"/>
		<acme:text value="${administrator.userAccount.username}" label="actor.displayData.username"/>
		<acme:text value="${administrator.surnames}" label="actor.displayData.surnames"/>
		<acme:text value="${administrator.photo}" label="actor.displayData.photo"/>
		<acme:text value="${administrator.email}" label="actor.displayData.email"/>
		<acme:text value="${administrator.address}" label="actor.displayData.address"/>
		<acme:text value="${administrator.phoneNumber}" label="actor.displayData.phoneNumber"/>
		<acme:text value="${administrator.vatNumber}" label="actor.displayData.vatNumber"/>

		

		
		<!-- Social profiles table -->
		<b><spring:message code="actor.displayData.socialProfile" /></b>
		<display:table name="${socialProfiles}" id="profile">
			<spring:message code="actor.displayData.socialProfilesName" var="name" />
			<display:column title="${name }">
				<jstl:out value="${ profile.name}" />
			</display:column>
			<spring:message code="actor.displayData.socialProfilesNetwork" var="network" />
			<display:column title="${network }">
				<jstl:out value="${profile.socialNetwork}" />
			</display:column>
			<spring:message code="actor.displayData.socialProfilesLink" var="link" />
			<display:column title="${link }">
				<jstl:out value="${ profile.link}" />
			</display:column>
		</display:table>
		
		<br>
		
		<!-- Table with messages -->
		<b><spring:message code="actor.displayData.messages" /></b>
		<display:table pagesize="5" name="${messages}" id="message" requestURI="actor/displayAllData.do">
			<display:column titleKey="actor.displayData.messageSender"><jstl:out value="${message.sender.email}"/></display:column>
			<display:column titleKey="actor.displayData.messageRecipients">
				<jstl:forEach var="recipient" items="${message.recipients}">
					<jstl:out value="${recipient}"/> 
				</jstl:forEach>
			</display:column>
			<display:column titleKey="actor.displayData.messageSubject"><jstl:out value="${message.subject}"/></display:column>
			<display:column titleKey="actor.displayData.messageMoment"><jstl:out value="${message.moment}"/></display:column>
			<display:column titleKey="actor.displayData.messageBody" ><jstl:out value="${message.body}"/></display:column>
			<display:column titleKey="actor.displayData.messagePriority"><jstl:out value="${message.priority}"/></display:column>
			<display:column titleKey="actor.displayData.messageTags">
				<jstl:forEach var="tag" items="${message.tags}">
					<jstl:out value="${tag}"/><br/>
				</jstl:forEach>
			</display:column>
		</display:table><br/>
	
	
	</jstl:when>


</jstl:choose>
</div>


<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.min.js"></script>

<script>
    function printHTML() {
        var pdf = new jsPDF('p', 'pt', 'letter');
        
        source = $('#print')[0];

        specialElementHandlers = {
            // element with id of "bypass" - jQuery style selector
            '#bypassme': function (element, renderer) {
                // true = "handled elsewhere, bypass text extraction"
                return true
            }
        };
        margins = {
            top: 80,
            bottom: 60,
            left: 40,
            width: 522
        };
        // all coords and widths are in jsPDF instance's declared units
        // 'inches' in this case
        pdf.fromHTML(
            source, // HTML string or DOM elem ref.
            margins.left, // x coord
            margins.top, { // y coord
                'width': margins.width, // max width of content on PDF
                'elementHandlers': specialElementHandlers
            },

            function (dispose) {
                // dispose: object with X, Y of the last line add to the PDF 
                //          this allow the insertion of new lines after html
                pdf.save('Test.pdf');
            }, margins
        );
    }
</script>

<input type="button" value="<spring:message code="actor.exportData.export"/>" onclick="printHTML()"/>



