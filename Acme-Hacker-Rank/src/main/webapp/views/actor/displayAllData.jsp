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
	
	<acme:button url="actor/deleteAllData.do" type="button" code="master.page.deleteAll"/>

<hr>
	<%-- METER LA ACCION DE EXPORTAR DATOS <acme:button url="actor/deleteAllData.do" type="button" code="master.page.deleteAll"/> --%>
<hr>

<jstl:choose>
	<jstl:when test="${authority == 'CHAPTER'}">
		
		<b><spring:message code="actor.name" /></b>:
		<jstl:out value="${chapter.name }" />
		<br />
		<b><spring:message code="actor.middleName" /></b>:
		<jstl:out value="${chapter.middleName }" />
		<br />
		<b><spring:message code="actor.surname" /></b>:
		<jstl:out value="${chapter.surname }" />
		<br />
		<b><spring:message code="actor.photoURL" /></b>:
		<jstl:out value="${chapter.photoURL }" />
		<br />
		<b><spring:message code="actor.email" /></b>:
		<jstl:out value="${chapter.email }" />
		<br />
		<b><spring:message code="actor.address" /></b>:
		<jstl:out value="${chapter.address }" />
		<br />
		<b><spring:message code="actor.phoneNumber" /></b>:
		<jstl:out value="${chapter.phoneNumber }" />
		<br />
		<b><spring:message code="chapter.title" /></b>:
		<jstl:out value="${chapter.title }" />
		
		<br />
		
		<!-- Social profiles table -->
		<b><spring:message code="actor.socialProfile" /></b>
		<display:table name="${chapter.socialProfiles}" id="profile">
			<spring:message code="actor.socialProfiles.name" var="name" />
			<display:column title="${name }">
				<jstl:out value="${ profile.name}" />
			</display:column>
			<spring:message code="actor.socialProfiles.network" var="network" />
			<display:column title="${network }">
				<jstl:out value="${profile.socialNetwork}" />
			</display:column>
			<spring:message code="actor.socialProfiles.link" var="link" />
			<display:column title="${link }">
				<jstl:out value="${ profile.link}" />
			</display:column>
		</display:table><br/>
		
		<!-- Table with messages -->
		<b><spring:message code="actor.messages" /></b>
		<display:table pagesize="5" name="${messages}" id="message" requestURI="actor/displayAllData.do">
			<display:column titleKey="message.sender"><jstl:out value="${message.sender.email}"/></display:column>
			<display:column titleKey="message.recipients">
				<jstl:forEach var="recipient" items="${message.recipients}">
					<jstl:out value="${recipient}"/> 
				</jstl:forEach>
			</display:column>
			<display:column titleKey="message.moment"><jstl:out value="${message.moment}"/></display:column>
			<display:column titleKey="sender.email" ><jstl:out value="${message.sender.email}"/></display:column>
			<display:column titleKey="message.body"><jstl:out value="${message.subject}"/></display:column>
			<display:column titleKey="message.priority"><jstl:out value="${message.priority}"/></display:column>
			<display:column titleKey="message.tags">
				<jstl:forEach var="tag" items="${message.tags}">
					<jstl:out value="${tag}"/><br/>
				</jstl:forEach>
			</display:column>
		</display:table>
		
		<!-- Table with proclaims  -->
		<b><spring:message code="chapter.proclaims" /></b>
		<display:table pagesize="5" name="${chapter.proclaims}" id="proclaim" requestURI="actor/displayAllData.do">			 
			<display:column titleKey="proclaim.text"><jstl:out value="${proclaim.text}"/></display:column>
			<display:column titleKey="proclaim.moment"><jstl:out value="${proclaim.moment}"/></display:column>
		</display:table>
				
	</jstl:when>
	
	
	<jstl:when test="${authority == 'SPONSOR'}">
	<b><spring:message code="actor.name" /></b>:
		<jstl:out value="${sponsor.name }" />
		<br />
		<b><spring:message code="actor.middleName" /></b>:
		<jstl:out value="${sponsor.middleName }" />
		<br />
		<b><spring:message code="actor.surname" /></b>:
		<jstl:out value="${sponsor.surname }" />
		<br />
		<b><spring:message code="actor.photoURL" /></b>:
		<jstl:out value="${sponsor.photoURL }" />
		<br />
		<b><spring:message code="actor.email" /></b>:
		<jstl:out value="${sponsor.email }" />
		<br />
		<b><spring:message code="actor.address" /></b>:
		<jstl:out value="${sponsor.address }" />
		<br />
		<b><spring:message code="actor.phoneNumber" /></b>:
		<jstl:out value="${sponsor.phoneNumber }" />
		
		<br />
		
		<!-- Social profiles table -->
		<b><spring:message code="actor.socialProfile" /></b>
		<display:table name="${sponsor.socialProfiles}" id="profile">
			<spring:message code="actor.socialProfiles.name" var="name" />
			<display:column title="${name }">
				<jstl:out value="${ profile.name}" />
			</display:column>
			<spring:message code="actor.socialProfiles.network" var="network" />
			<display:column title="${network }">
				<jstl:out value="${profile.socialNetwork}" />
			</display:column>
			<spring:message code="actor.socialProfiles.link" var="link" />
			<display:column title="${link }">
				<jstl:out value="${ profile.link}" />
			</display:column>
		</display:table><br/>
		
		<!-- Table with messages -->
		<b><spring:message code="actor.messages" /></b>
		<display:table pagesize="5" name="${messages}" id="message" requestURI="actor/displayAllData.do">
			<display:column titleKey="message.sender"><jstl:out value="${message.sender.email}"/></display:column>
			<display:column titleKey="message.recipients">
				<jstl:forEach var="recipient" items="${message.recipients}">
					<jstl:out value="${recipient}"/> 
				</jstl:forEach>
			</display:column>
			<display:column titleKey="message.moment"><jstl:out value="${message.moment}"/></display:column>
			<display:column titleKey="sender.email" ><jstl:out value="${message.sender.email}"/></display:column>
			<display:column titleKey="message.body"><jstl:out value="${message.subject}"/></display:column>
			<display:column titleKey="message.priority"><jstl:out value="${message.priority}"/></display:column>
			<display:column titleKey="message.tags">
				<jstl:forEach var="tag" items="${message.tags}">
					<jstl:out value="${tag}"/><br/>
				</jstl:forEach>
			</display:column>
		</display:table><br/>
		
		<!-- Table with sponsorships  -->
		<b><spring:message code="sponsor.sponsorships" /></b>
		<display:table pagesize="5" name="${sponsor.sponsorships}" id="sponsorship" requestURI="actor/displayAllData.do">
			<display:column titleKey="sponsorship.targetURL" ><jstl:out value="${sponsorship.targetURL}"/></display:column>
			<display:column titleKey="sponsorship.bannerURL"><jstl:out value="${sponsorship.bannerURL}"/></display:column>
			<display:column titleKey="sponsorship.active" ><jstl:out value="${sponsorship.active}"/></display:column>
			<display:column titleKey="sponsorship.parade" ><jstl:out value="${sponsorship.parade.title}"/></display:column>
		</display:table>
	
	
	</jstl:when>
	
	<jstl:when test="${authority == 'ADMINISTRATOR'}">
	<b><spring:message code="actor.name" /></b>:
		<jstl:out value="${administrator.name }" />
		<br />
		<b><spring:message code="actor.middleName" /></b>:
		<jstl:out value="${administrator.middleName }" />
		<br />
		<b><spring:message code="actor.surname" /></b>:
		<jstl:out value="${administrator.surname }" />
		<br />
		<b><spring:message code="actor.photoURL" /></b>:
		<jstl:out value="${administrator.photoURL }" />
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
		
		<!-- Social profiles table -->
		<b><spring:message code="actor.socialProfile" /></b>
		<display:table name="${sponsor.socialProfiles}" id="profile">
			<spring:message code="actor.socialProfiles.name" var="name" />
			<display:column title="${name }">
				<jstl:out value="${ profile.name}" />
			</display:column>
			<spring:message code="actor.socialProfiles.network" var="network" />
			<display:column title="${network }">
				<jstl:out value="${profile.socialNetwork}" />
			</display:column>
			<spring:message code="actor.socialProfiles.link" var="link" />
			<display:column title="${link }">
				<jstl:out value="${ profile.link}" />
			</display:column>
		</display:table><br/>
		
		<!-- Table with messages -->
		<b><spring:message code="actor.messages" /></b>
		<display:table pagesize="5" name="${messages}" id="message" requestURI="actor/displayAllData.do">
			<display:column titleKey="message.sender"><jstl:out value="${message.sender.email}"/></display:column>
			<display:column titleKey="message.recipients">
				<jstl:forEach var="recipient" items="${message.recipients}">
					<jstl:out value="${recipient}"/> 
				</jstl:forEach>
			</display:column>
			<display:column titleKey="message.moment"><jstl:out value="${message.moment}"/></display:column>
			<display:column titleKey="sender.email" ><jstl:out value="${message.sender.email}"/></display:column>
			<display:column titleKey="message.body"><jstl:out value="${message.subject}"/></display:column>
			<display:column titleKey="message.priority"><jstl:out value="${message.priority}"/></display:column>
			<display:column titleKey="message.tags">
				<jstl:forEach var="tag" items="${message.tags}">
					<jstl:out value="${tag}"/><br/>
				</jstl:forEach>
			</display:column>
		</display:table><br/>
	
	
	</jstl:when>
	
	<jstl:when test="${authority == 'MEMBER'}">
	<b><spring:message code="actor.name" /></b>:
		<jstl:out value="${administrator.name }" />
		<br />
		<b><spring:message code="actor.middleName" /></b>:
		<jstl:out value="${administrator.middleName }" />
		<br />
		<b><spring:message code="actor.surname" /></b>:
		<jstl:out value="${administrator.surname }" />
		<br />
		<b><spring:message code="actor.photoURL" /></b>:
		<jstl:out value="${administrator.photoURL }" />
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
		
		<!-- Social profiles table -->
		<b><spring:message code="actor.socialProfile" /></b>
		<display:table name="${sponsor.socialProfiles}" id="profile">
			<spring:message code="actor.socialProfiles.name" var="name" />
			<display:column title="${name }">
				<jstl:out value="${ profile.name}" />
			</display:column>
			<spring:message code="actor.socialProfiles.network" var="network" />
			<display:column title="${network }">
				<jstl:out value="${profile.socialNetwork}" />
			</display:column>
			<spring:message code="actor.socialProfiles.link" var="link" />
			<display:column title="${link }">
				<jstl:out value="${ profile.link}" />
			</display:column>
		</display:table><br/>
		
		<!-- Table with messages -->
		<b><spring:message code="actor.messages" /></b>
		<display:table pagesize="5" name="${messages}" id="message" requestURI="actor/displayAllData.do">
			<display:column titleKey="message.sender"><jstl:out value="${message.sender.email}"/></display:column>
			<display:column titleKey="message.recipients">
				<jstl:forEach var="recipient" items="${message.recipients}">
					<jstl:out value="${recipient}"/> 
				</jstl:forEach>
			</display:column>
			<display:column titleKey="message.moment"><jstl:out value="${message.moment}"/></display:column>
			<display:column titleKey="sender.email" ><jstl:out value="${message.sender.email}"/></display:column>
			<display:column titleKey="message.body"><jstl:out value="${message.subject}"/></display:column>
			<display:column titleKey="message.priority"><jstl:out value="${message.priority}"/></display:column>
			<display:column titleKey="message.tags">
				<jstl:forEach var="tag" items="${message.tags}">
					<jstl:out value="${tag}"/><br/>
				</jstl:forEach>
			</display:column>
		</display:table><br/>
		
		<!-- Table with requests -->
		<b><spring:message code="member.requests" /></b>
		<display:table pagesize="5" name="${member.requests}" id="requestMember" requestURI="actor/displayAllData.do">
			<display:column titleKey="member.status"><jstl:out value="${requestMember.status}"/></display:column>
			<display:column titleKey="member.paradeRow"><jstl:out value="${requestMember.paradeRow}"/></display:column>
			<display:column titleKey="member.paradeColumn"><jstl:out value="${requestMember.paradeColumn}"/></display:column>
			<display:column titleKey="member.reasonReject"><jstl:out value="${requestMember.reasonReject}"/></display:column>
			<display:column titleKey="member.parade"><jstl:out value="${requestMember.parade.title}"/></display:column>
		</display:table><br/>
		
		
		<!-- Table with enrolements -->
		<b><spring:message code="member.enrolements" /></b>
		<display:table pagesize="5" name="${enrolements}" id="enrolement" requestURI="actor/displayAllData.do">
			<display:column titleKey="enrolement.position"><jstl:out value="${enrolement.position}"/></display:column>
			<display:column titleKey="enrolement.enrolMoment"><jstl:out value="${enrolement.enrolMoment}"/></display:column>
			<display:column titleKey="enrolement.dropOutMoment"><jstl:out value="${enrolement.dropOutMoment}"/></display:column>
			<display:column titleKey="enrolement.brotherhood"><jstl:out value="${enrolement.brotherhood.title}"/></display:column>

		</display:table><br/>
	
	
	</jstl:when>
	
		<jstl:when test="${authority == 'BROTHERHOOD'}">
		<b><spring:message code="actor.name" /></b>:
		<jstl:out value="${brotherhood.name }" />
		<br />
		<b><spring:message code="actor.middleName" /></b>:
		<jstl:out value="${brotherhood.middleName }" />
		<br />
		<b><spring:message code="actor.surname" /></b>:
		<jstl:out value="${brotherhood.surname }" />
		<br />
		<b><spring:message code="actor.photoURL" /></b>:
		<jstl:out value="${brotherhood.photoURL }" />
		<br />
		<b><spring:message code="actor.email" /></b>:
		<jstl:out value="${brotherhood.email }" />
		<br />
		<b><spring:message code="actor.address" /></b>:
		<jstl:out value="${brotherhood.address }" />
		<br />
		<b><spring:message code="actor.phoneNumber" /></b>:
		<jstl:out value="${brotherhood.phoneNumber }" />
		<br />
		<b><spring:message code="brotherhood.title" /></b>:
		<jstl:out value="${brotherhood.title }" />
		<br />
		<b><spring:message code="brotherhood.establismentDate" /></b>:
		<jstl:out value="${brotherhood.establismentDate}" />
		<br />
		<b><spring:message code="brotherhood.area" /></b>:
		<jstl:out value="${brotherhood.area.name}" />
		<br />
		<b><spring:message code="brotherhood.pictureURLs" /></b>:
		<jstl:forEach items="${brotherhood.pictureURLs}" var="picture">
				<jstl:out value="${picture}"></jstl:out>
		</jstl:forEach>
		<b><spring:message code="brotherhood.parades" /></b>:
		<jstl:forEach items="${parades}" var="parade">
				<jstl:out value="${parade.title}"></jstl:out>
			<br>
		</jstl:forEach>
		
		<br />
		
		<!-- Social profiles table -->
		<b><spring:message code="actor.socialProfile" /></b>
		<display:table name="${sponsor.socialProfiles}" id="profile">
			<spring:message code="actor.socialProfiles.name" var="name" />
			<display:column title="${name }">
				<jstl:out value="${ profile.name}" />
			</display:column>
			<spring:message code="actor.socialProfiles.network" var="network" />
			<display:column title="${network }">
				<jstl:out value="${profile.socialNetwork}" />
			</display:column>
			<spring:message code="actor.socialProfiles.link" var="link" />
			<display:column title="${link }">
				<jstl:out value="${ profile.link}" />
			</display:column>
		</display:table><br/>
		
		<!-- Table with messages -->
		<b><spring:message code="actor.messages" /></b>
		<display:table pagesize="5" name="${messages}" id="message" requestURI="actor/displayAllData.do">
			<display:column titleKey="message.sender"><jstl:out value="${message.sender.email}"/></display:column>
			<display:column titleKey="message.recipients">
				<jstl:forEach var="recipient" items="${message.recipients}">
					<jstl:out value="${recipient}"/> 
				</jstl:forEach>
			</display:column>
			<display:column titleKey="message.moment"><jstl:out value="${message.moment}"/></display:column>
			<display:column titleKey="sender.email" ><jstl:out value="${message.sender.email}"/></display:column>
			<display:column titleKey="message.body"><jstl:out value="${message.subject}"/></display:column>
			<display:column titleKey="message.priority"><jstl:out value="${message.priority}"/></display:column>
			<display:column titleKey="message.tags">
				<jstl:forEach var="tag" items="${message.tags}">
					<jstl:out value="${tag}"/><br/>
				</jstl:forEach>
			</display:column>
		</display:table><br/>
			
		<!-- Table with enrolements -->
		<b><spring:message code="member.enrolements" /></b>
		<display:table pagesize="5" name="${enrolements}" id="enrolement" requestURI="actor/displayAllData.do">
			<display:column titleKey="enrolement.position"><jstl:out value="${enrolement.position}"/></display:column>
			<display:column titleKey="enrolement.enrolMoment"><jstl:out value="${enrolement.enrolMoment}"/></display:column>
			<display:column titleKey="enrolement.dropOutMoment"><jstl:out value="${enrolement.dropOutMoment}"/></display:column>
			<display:column titleKey="enrolement.brotherhood"><jstl:out value="${enrolement.member.name}"/></display:column>

		</display:table><br/>
		
		<!-- Table with enrolements -->
		<b><spring:message code="brotherhood.floats" /></b>
		<display:table pagesize="5" name="${floats}" id="floatEntity" requestURI="actor/displayAllData.do">
			<display:column titleKey="float.title"><jstl:out value="${floatEntity.title}"/></display:column>
			<display:column titleKey="float.description"><jstl:out value="${floatEntity.description}"/></display:column>
			<display:column titleKey="float.pictureURLs">
			<jstl:forEach items="${floatEntity.pictureURLs}" var="picture">
				<jstl:out value="${picture}"></jstl:out>
			</jstl:forEach>
		</display:column>
		</display:table>
		
		
		<!-- Table with the history  -->
		<h2><spring:message code="history.history"/></h2>
		<h4><spring:message code="history.inception" />:</h4>
		<b><spring:message code="history.title" />:</b>
		<jstl:out value="${brotherhood.history.inceptionRecord.title }" />
		<br />
		<b><spring:message code="history.description" />:</b>
		<jstl:out value="${brotherhood.history.inceptionRecord.description }" />
		<br />
		<b><spring:message code="brotherhood.pictureURLs" />:</b>
		<jstl:forEach items="${brotherhood.history.inceptionRecord.photosURL }" var="photo">
			<jstl:out value="photo" />
			<br />
		</jstl:forEach>
		<br />

		<b><spring:message code="history.legalRecords" />:</b>
		<display:table name="${brotherhood.history.legalRecords}" id="legalRecord">
			<display:column titleKey="history.title"><jstl:out value="${legalRecord.title}"/></display:column>
			<display:column titleKey="history.description"><jstl:out value="${legalRecord.description}"/></display:column>
			<display:column titleKey="history.legalName" ><jstl:out value="${legalRecord.legalName}"/></display:column>
			<display:column titleKey="history.VAT" ><jstl:out value="${legalRecord.VAT}"/></display:column>		
		</display:table>
		
		<b><spring:message code="history.periodRecords" />:</b>
		<display:table name="${brotherhood.history.periodRecords}" id="periodRecord">
			<display:column titleKey="history.title" ><jstl:out value="${periodRecord.title}"/></display:column>	
			<display:column titleKey="history.description"><jstl:out value="${periodRecord.description}"/></display:column>	
			<display:column titleKey="history.startYear"><jstl:out value="${periodRecord.startYear}"/></display:column>	
			<display:column titleKey="history.endYear"><jstl:out value="${periodRecord.endYear}"/></display:column>	
		</display:table>
		
		<b><spring:message code="history.miscellaneousRecords" />:</b>
		<display:table name="${brotherhood.history.miscellaneousRecords}" id="miscRecord" >
			<display:column titleKey="history.title" ><jstl:out value="${miscRecord.title}"/></display:column>
			<display:column titleKey="history.description" ><jstl:out value="${miscRecord.title}"/></display:column>
		</display:table>

		<b><spring:message code="history.linkRecords" />:</b>
		<display:table name="${brotherhood.history.linkRecords}" id="linkRecord">
			<display:column titleKey="history.title"><jstl:out value="${linkRecord.title}"/></display:column>
			<display:column titleKey="history.description"><jstl:out value="${linkRecord.description}"/></display:column>
			<display:column titleKey="history.link"><jstl:out value="${linkRecord.brotherhoodURL}"/></display:column>
		</display:table>

		
	
	
	</jstl:when>


</jstl:choose>