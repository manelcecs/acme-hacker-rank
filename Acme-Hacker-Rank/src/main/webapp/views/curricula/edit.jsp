 <%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
    uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="curricula" action="curricula/hacker/save.do" method="post">

    <acme:hidden path="id"/>
    <acme:hidden path="version" />

    <acme:textbox path="title" code="curricula.edit.title" />
    
    <acme:submit name="submit" code="curricula.edit.submit"/>
    <acme:cancel url="/curricula/hacker/list.do" code="curricula.edit.cancel" />
    
</form:form>
