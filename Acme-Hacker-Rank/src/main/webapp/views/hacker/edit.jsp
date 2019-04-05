<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<script type='text/javascript'>
	function checkPhone(str) {
		if (str != "") {
			var patt = new RegExp("^(\[+][1-9][0-9]{0,2}[ ]{1}\[(][1-9][0-9]{0,2}\[)][ ]{1}[0-9]{4,}|\[+][1-9][0-9]{0,2}[ ]{1}[0-9]{4,}|[0-9]{4,}|[ ]{1,})$");
			if (patt.test(str) == false) { return confirm("<spring:message code="hacker.edit.phone.error"/>"); }
		}
	}
</script>

<jstl:if test="${edit }">
	<form:form action="hacker/hacker/save.do"
		modelAttribute="hacker">
		<acme:textbox code="hacker.edit.name" path="name" />
		<acme:textarea code="hacker.edit.surnames" path="surnames" />
		<acme:textbox code="hacker.edit.photoURL" path="photo" />
		<acme:textbox code="hacker.edit.address" path="address" />
		<acme:textbox code="hacker.edit.vat" path="vatNumber" />
		<acme:textbox code="hacker.edit.email" path="email" />
		<acme:textbox code="hacker.edit.phoneNumber" path="phoneNumber" id="phone" />
		<br />
		<acme:textbox code="hacker.edit.creditcard.holder" path="creditCard.holder"/>
		<acme:textbox code="hacker.edit.creditcard.make" path="creditCard.make"/>
		<acme:textbox code="hacker.edit.creditcard.number" path="creditCard.number"/>
		<acme:textbox code="hacker.edit.creditcard.expirationMonth" path="creditCard.expirationMonth" placeholder="MM"/>
		<acme:textbox code="hacker.edit.creditcard.expirationYear" path="creditCard.expirationYear" placeholder="YY"/>
		<acme:textbox code="hacker.edit.creditcard.CVV" path="creditCard.cvv"/>
		<br/>
		<input type="submit" name="submit" onclick="return checkPhone(this.form.phone.value)" />
		<acme:cancel url="/" code="hacker.edit.cancel" />
	</form:form>
</jstl:if>
<jstl:if test="${not edit }">
	<form:form action="hacker/save.do"
		modelAttribute="hackerForm">
		<acme:textbox code="hacker.edit.username"
			path="userAccount.username" />
		<acme:password code="hacker.edit.password"
			path="userAccount.password" />
		<acme:password code="hacker.edit.confirmPassword"
			path="confirmPassword" />

		<acme:textbox code="hacker.edit.name" path="name" />
		<acme:textarea code="hacker.edit.surnames" path="surnames" />
		<acme:textbox code="hacker.edit.photoURL" path="photo" />
		<acme:textbox code="hacker.edit.address" path="address" />
		<acme:textbox code="hacker.edit.vat" path="vatNumber" />
		<acme:textbox code="hacker.edit.email" path="email" />
		<acme:textbox code="hacker.edit.phoneNumber" path="phoneNumber"
			id="phone" />
		<br />
		<acme:textbox code="hacker.edit.creditcard.holder" path="creditCard.holder"/>
		<acme:textbox code="hacker.edit.creditcard.make" path="creditCard.make"/>
		<acme:textbox code="hacker.edit.creditcard.number" path="creditCard.number"/>
		<acme:textbox code="hacker.edit.creditcard.expirationMonth" path="creditCard.expirationMonth"/>
		<acme:textbox code="hacker.edit.creditcard.expirationYear" path="creditCard.expirationYear"/>
		<acme:textbox code="hacker.edit.creditcard.CVV" path="creditCard.cvv"/>
		<br/>
		<form:checkbox path="termsAndConditions" />
		<b><spring:message code="hacker.edit.termsAndConditions" /></b>
		<form:errors path="termsAndConditions" cssClass="error" />
		<br />
		<input type="submit" name="submit"
			onclick="return checkPhone(this.form.phone.value)" />
		<acme:cancel url="/" code="hacker.edit.cancel" />
	</form:form>
</jstl:if>