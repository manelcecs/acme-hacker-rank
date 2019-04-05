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
			if (patt.test(str) == false) { return confirm("<spring:message code="administrator.edit.phone.error"/>"); }
		}
	}
</script>

<jstl:if test="${edit }">
	<form:form action="administrator/administrator/save.do"
		modelAttribute="administrator">
		<acme:textbox code="administrator.edit.name" path="name" />
		<form:label class="textboxLabel" path="surnames"><spring:message code="administrator.edit.surnames" /></form:label>
    	<div id="surnames">
    		<form:input class="textbox" path="surnames" type="text"/>    
   		 </div>			
    	<form:errors path="surnames" cssClass="error" />  
		
		<acme:textbox code="administrator.edit.photoURL" path="photo" />
		<acme:textbox code="administrator.edit.address" path="address" />
		<acme:textbox code="administrator.edit.vat" path="vatNumber" />
		<acme:textbox code="administrator.edit.email" path="email" />
		<acme:textbox code="administrator.edit.phoneNumber" path="phoneNumber" id="phone" />
		<br />
		<acme:textbox code="administrator.edit.creditcard.holder" path="creditCard.holder"/>
		<acme:textbox code="administrator.edit.creditcard.make" path="creditCard.make"/>
		<acme:textbox code="administrator.edit.creditcard.number" path="creditCard.number"/>
		<acme:textbox code="administrator.edit.creditcard.expirationMonth" path="creditCard.expirationMonth" placeholder="MM"/>
		<acme:textbox code="administrator.edit.creditcard.expirationYear" path="creditCard.expirationYear" placeholder="YY"/>
		<acme:textbox code="administrator.edit.creditcard.CVV" path="creditCard.cvv"/>
		<br/>
		<input type="submit" name="submit" onclick="return checkPhone(this.form.phone.value)" />
		<acme:cancel url="/" code="administrator.edit.cancel" />
	</form:form>
	<button class="addTag" onclick="addComment('surnames','surnames', 'textbox')"><spring:message code="administrator.edit.surnames.add" /></button>
</jstl:if>
<jstl:if test="${not edit }">
	<form:form action="administrator/save.do"
		modelAttribute="administratorForm">
		<acme:textbox code="administrator.edit.username"
			path="userAccount.username" />
		<acme:password code="administrator.edit.password"
			path="userAccount.password" />
		<acme:password code="administrator.edit.confirmPassword"
			path="confirmPassword" />

		<acme:textbox code="administrator.edit.name" path="name" />
		<div id="surnames">
    		<form:input class="textbox" path="surnames" type="text"/>    
   		 </div>			
    	<form:errors path="surnames" cssClass="error" />  
		
		<acme:textbox code="administrator.edit.photoURL" path="photo" />
		<acme:textbox code="administrator.edit.address" path="address" />
		<acme:textbox code="administrator.edit.vat" path="vatNumber" />
		<acme:textbox code="administrator.edit.email" path="email" />
		<acme:textbox code="administrator.edit.phoneNumber" path="phoneNumber"
			id="phone" />
		<br />
		<acme:textbox code="administrator.edit.creditcard.holder" path="creditCard.holder"/>
		<acme:textbox code="administrator.edit.creditcard.make" path="creditCard.make"/>
		<acme:textbox code="administrator.edit.creditcard.number" path="creditCard.number"/>
		<acme:textbox code="administrator.edit.creditcard.expirationMonth" path="creditCard.expirationMonth"/>
		<acme:textbox code="administrator.edit.creditcard.expirationYear" path="creditCard.expirationYear"/>
		<acme:textbox code="administrator.edit.creditcard.CVV" path="creditCard.cvv"/>
		<br/>
		<form:checkbox path="termsAndConditions" />
		<b><spring:message code="administrator.edit.termsAndConditions" /></b>
		<form:errors path="termsAndConditions" cssClass="error" />
		<br />
		<input type="submit" name="submit"
			onclick="return checkPhone(this.form.phone.value)" />
		<acme:cancel url="/" code="administrator.edit.cancel" />
	</form:form>
	<button class="addTag" onclick="addComment('surnames','surnames', 'textbox')"><spring:message code="administrator.edit.surnames.add" /></button>
</jstl:if>