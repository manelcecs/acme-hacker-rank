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

<%@taglib prefix="jstl"    uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<acme:button code="position.edit.cancel" type="button" url="/position/company/list.do"/>

<form:form modelAttribute="positionForm" action="position/company/save.do">
   	 <acme:hidden path="id"/>
   	 <acme:hidden path="version"/>
   	 
   	 <p>
   		 <acme:textbox code="position.edit.title" path="title"/>
   	 </p>
   	 <p>
   		 <acme:textarea code="position.edit.description" path="description"/>
   	 </p>
   	 <p>
   		 <acme:inputDate code="position.edit.deadline" path="deadline"/>
   	 </p>
   	 
   	 <p>
   		 <acme:textbox code="position.edit.profileRequired" path="profileRequired"/>
   	 </p>
   	 
   	 <p>
   		 <acme:textbox code="position.edit.salaryOffered" path="salaryOffered"/>
   	 </p>
   	 
   	 <form:label class="textboxLabel" path="skillsRequired"><spring:message code="position.edit.skillsRequired" /></form:label>
   	 <div id="skillsRequired">
   									   
   		 <jstl:if test="${empty positionForm.skillsRequired}">
   			 <form:input path="skillsRequired" type="text"/>  		 
   		 </jstl:if>
   		 
   		 <jstl:forEach items="${positionForm.skillsRequired}" var="skill" >
   			 <form:input class="textbox" path="skillsRequired" type="text" value="${skill}"/>   
   		 </jstl:forEach>  
  		  </div>
   	 <form:errors path="skillsRequired" cssClass="error" />   
   	 
   	 
   	 <form:label class="textboxLabel" path="technologiesRequired"><spring:message code="position.edit.technologiesRequired" /></form:label>
   	 <div id="technologiesRequired">
   		 <jstl:if test="${empty positionForm.technologiesRequired}">
   			 <form:input class="textbox" path="technologiesRequired" type="text"/> 			 
   		 </jstl:if>
   	 
   		 <jstl:forEach items="${positionForm.technologiesRequired}" var="technology">
   			 <form:input class="textbox" path="technologiesRequired" type="text" value="${technology}"/>  
   		 </jstl:forEach>  
  		  </div>
   	 <form:errors path="technologiesRequired" cssClass="error" />   
   	 
   	 
   	 <acme:submit name="save" code="position.edit.save"/>
    </form:form>
    
    <button class="addTag" onclick="addComment('skillsRequired','skillsRequired', 'textbox')"><spring:message code="position.edit.addSkillsRequired" /></button>
    
    <button class="addTag" onclick="addComment('technologiesRequired','technologiesRequired', 'textbox')"><spring:message code="position.edit.addTechnologiesRequired" /></button>


