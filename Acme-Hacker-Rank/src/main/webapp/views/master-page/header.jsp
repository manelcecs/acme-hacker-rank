<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${banner}" alt="${systemName} Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li>
						<a href="adminConfig/administrator/display.do"><spring:message code="master.page.administrator.configuration" /></a>
					</li>
					
					<li>
						<a href="administrator/process.do"><spring:message code="master.page.process.launch" /></a>
					</li>					
					<li>
					    <a href="dashboard/administrator/display.do"><spring:message code="master.page.header.dashboard" /></a>
					</li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv" href="search/display.do"><spring:message code="master.page.search.display" /></a>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('HACKER')">
			<li>
				<a class="fNiv" href="finder/hacker/edit.do"><spring:message code="master.page.finder.edit" /></a>
			</li>
			<li>
				<a class="fNiv" href="application/hacker/list.do"><spring:message code="master.page.list.application" /></a>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
		
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
		</security:authorize>
		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv"><spring:message	code="master.page.positions" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="position/company/create.do"><spring:message code="master.page.company.create" /></a></li>
					<li><a href="position/company/list.do"><spring:message code="master.page.company.list" /></a></li>			
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message	code="master.page.problems" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="problem/company/create.do"><spring:message code="master.page.company.problem.create" /></a></li>
					<li><a href="problem/company/list.do"><spring:message code="master.page.company.problem.list" /></a></li>			
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li>
				<a class="fNiv" href="search/display.do"><spring:message code="master.page.search.display" /></a>
			</li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="position/list.do"><spring:message code="master.page.list.position" /></a></li>
			<li><a class="fNiv" href="company/list.do"><spring:message code="master.page.list.company" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv" href="messageBox/list.do"><spring:message code="master.page.boxes" /></a>
			</li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/action-1.do"><spring:message code="master.page.profile.action.1" /></a></li>
					<li><a href="profile/action-2.do"><spring:message code="master.page.profile.action.2" /></a></li>
					<li><a href="profile/action-3.do"><spring:message code="master.page.profile.action.3" /></a></li>					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="position/list.do"><spring:message code="master.page.list.position" /></a></li>
			<li><a class="fNiv" href="company/list.do"><spring:message code="master.page.list.company" /></a></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

