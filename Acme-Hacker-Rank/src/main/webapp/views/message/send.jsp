<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<section id="SendMessage">

	<form:form action="message/send.do" modelAttribute="Message">

		<acme:hidden path="id" />
		<acme:hidden path="moment" />
		<acme:hidden path="sender" />

		<acme:textbox code="message.subject" path="subject" />
		<acme:textarea code="message.body" path="body" />

		<form:label path="priority">
			<spring:message code="message.priority" />
		</form:label>
		<form:select path="priority" multiple="false">
			<form:option value="HIGH">
				<jstl:out value="HIGH" />
			</form:option>
			<form:option value="MEDIUM">
				<jstl:out value="MEDIUM" />
			</form:option>
			<form:option value="LOW">
				<jstl:out value="LOW" />
			</form:option>
		</form:select>


		<form:label class="textboxLabel" path="tags">
			<spring:message code="message.tags" />
		</form:label>
		<div id="tags">
			<form:input class="textbox" path="tags" type="text" />
		</div>
		<form:errors path="tags" cssClass="error" />


		<form:label path="recipients">
			<spring:message code="message.recipient" />
		</form:label>
		<form:select path="recipients" multiple="false">
			<security:authorize access="hasRole('ADMINISTRATOR')">
				<form:option value="${actors}">
					<spring:message code="message.broadCastMessage" />
				</form:option>
			</security:authorize>
			<form:options items="${actors}" itemValue="id" itemLabel="email" />
		</form:select>
		<form:errors path="recipients" cssClass="error" />


		<div class="botones">
			<acme:submit name="save" code="message.save" />
			<acme:cancel url="messageBox/list.do" code="message.cancel" />
		</div>

	</form:form>
	<button class="addTag" onclick="addComment('tags','tags', 'textbox')">
		<spring:message code="message.addTag" />
	</button>

</section>



<style>
#SendMessage {
	border: 1px solid black;
	width: 15%;
	padding: 10px 20px 20px 20px;
	margin: 50px 20px;
	position: relative;
}

.botones {
	margin-left: 70px;
}

.botones>button {
	margin-left: 10px;
}

.selectLabel {
	width: 100%;
	float: left;
	margin-bottom: 5px;
}

.select {
	width: 100%;
	float: left;
	margin-bottom: 20px;
}

.textboxLabel {
	width: 100%;
	float: left;
	margin-top: 20px;
	margin-bottom: 5px;
}

.textbox {
	width: 100%;
	float: left;
	margin-bottom: 20px;
}

.textAreaLabel {
	width: 100%;
	float: left;
	margin-top: 20px;
	margin-bottom: 5px;
}

.textArea {
	width: 100%;
	float: left;
	margin-bottom: 20px;
}

.addTag {
	position: absolute;
	top: 250px;
	right: 5%;
}
</style>