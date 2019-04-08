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
			if (patt.test(str) == false) { return confirm("<spring:message code="company.edit.phone.error"/>"); }
		}
	}
</script>

<jstl:if test="${edit }">
	<form:form action="company/company/save.do"
		modelAttribute="company">
		<acme:textbox code="company.edit.name" path="name" />
		<form:label class="textboxLabel" path="surnames"><spring:message code="company.edit.surnames" /></form:label>
    	<div id="surnames">
    		<jstl:forEach items="${company.surnames }" var="surname">
    			<form:input class="textbox" path="surnames" type="text" value="${ surname}"/>  
    		</jstl:forEach>  
   		 </div>					
    	<form:errors path="surnames" cssClass="error" />  
		<acme:textbox code="company.edit.companyName" path="companyName" />
		<acme:textbox code="company.edit.photoURL" path="photo" />
		<acme:textbox code="company.edit.address" path="address" />
		<acme:textbox code="company.edit.vat" path="vatNumber" />
		<acme:textbox code="company.edit.email" path="email" />
		<acme:textbox code="company.edit.phoneNumber" path="phoneNumber" id="phone" />
		<br />
		<acme:textbox code="company.edit.creditcard.holder" path="creditCard.holder"/>
		<acme:textbox code="company.edit.creditcard.make" path="creditCard.make"/>
		<acme:textbox code="company.edit.creditcard.number" path="creditCard.number"/>
		<acme:textbox code="company.edit.creditcard.expirationMonth" path="creditCard.expirationMonth" placeholder="company.edit.creditcard.expirationMonth.placeholder"/>
		<acme:textbox code="company.edit.creditcard.expirationYear" path="creditCard.expirationYear" placeholder="company.edit.creditcard.expirationYear.placeholder"/>
		<acme:textbox code="company.edit.creditcard.CVV" path="creditCard.cvv"/>
		<br/>
		<input type="submit" name="submit" onclick="return checkPhone(this.form.phone.value)" ><spring:message code="company.edit.submit" /></input>
		<acme:cancel url="/" code="company.edit.cancel" />
	</form:form>
	<button class="addTag" onclick="addComment('surnames','surnames', 'textbox')"><spring:message code="company.edit.surnames.add" /></button>
</jstl:if>
<jstl:if test="${not edit }">
	<form:form action="company/save.do"
		modelAttribute="companyForm">
		<acme:textbox code="company.edit.username"
			path="userAccount.username" />
		<acme:password code="company.edit.password"
			path="userAccount.password" />
		<acme:password code="company.edit.confirmPassword"
			path="confirmPassword" />

		<acme:textbox code="company.edit.name" path="name" />
		<form:label class="textboxLabel" path="surnames"><spring:message code="company.edit.surnames" /></form:label>
    	<div id="surnames">
    		<form:input class="textbox" path="surnames" type="text"/>    
   		 </div>			
    	<form:errors path="surnames" cssClass="error" />  
		<acme:textbox code="company.edit.companyName" path="companyName" />
		<acme:textbox code="company.edit.photoURL" path="photo" />
		<acme:textbox code="company.edit.address" path="address" />
		<acme:textbox code="company.edit.vat" path="vatNumber" />
		<acme:textbox code="company.edit.email" path="email" />
		<acme:textbox code="company.edit.phoneNumber" path="phoneNumber"
			id="phone" />
		<br />
		<acme:textbox code="company.edit.creditcard.holder" path="creditCard.holder"/>
		<acme:textbox code="company.edit.creditcard.make" path="creditCard.make"/>
		<acme:textbox code="company.edit.creditcard.number" path="creditCard.number"/>
		<acme:textbox code="company.edit.creditcard.expirationMonth" path="creditCard.expirationMonth"/>
		<acme:textbox code="company.edit.creditcard.expirationYear" path="creditCard.expirationYear"/>
		<acme:textbox code="company.edit.creditcard.CVV" path="creditCard.cvv"/>
		<br/>
		<form:checkbox path="termsAndConditions" />
		<b><spring:message code="company.edit.termsAndConditions" /></b>
		<form:errors path="termsAndConditions" cssClass="error" />
		<br />
		<input type="submit" name="submit"
			onclick="return checkPhone(this.form.phone.value)" ><spring:message code="company.edit.submit" /></input>
		<acme:cancel url="/" code="company.edit.cancel" />
	</form:form>
	<button class="addTag" onclick="addComment('surnames','surnames', 'textbox')"><spring:message code="company.edit.surnames.add" /></button>
</jstl:if>