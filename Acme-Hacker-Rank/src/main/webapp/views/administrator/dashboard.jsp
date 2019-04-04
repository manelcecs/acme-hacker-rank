<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<table>
	<tr>
		<th> <spring:message code="administrator.dashboard.avgOfPositionsPerCompany"/> </th>
		<th> <spring:message code='administrator.dashboard.minimumOfPositionsPerCompany'/> </th>
		<th> <spring:message code='administrator.dashboard.maximumOfPositionsPerCompany'/> </th>
		<th> <spring:message code='administrator.dashboard.sDOfPositionsPerCompany'/> 	  </th>
	</tr>
	<tr>
		<td> <jstl:out value="${avgOfPositionsPerCompany}"/>	</td>
		<td> <jstl:out value="${minimumOfPositionsPerCompany}"/>	</td>
		<td> <jstl:out value="${maximumOfPositionsPerCompany}"/>	</td>
		<td> <jstl:out value="${sDOfPositionsPerCompany}"/>	</td>
	</tr>
</table>

<table>
	<tr>
		<th> <spring:message code="administrator.dashboard.avgOfApplicationsPerHacker"/> </th>
		<th> <spring:message code='administrator.dashboard.minimumOfApplicationsPerHacker'/> </th>
		<th> <spring:message code='administrator.dashboard.maximumOfApplicationsPerHacker'/> </th>
		<th> <spring:message code='administrator.dashboard.sDOfApplicationsPerHacker'/> 	  </th>
	</tr>
	<tr>
		<td> <jstl:out value="${avgOfApplicationsPerHacker}"/>	</td>
		<td> <jstl:out value="${minimumOfApplicationsPerHacker}"/>	</td>
		<td> <jstl:out value="${maximumOfApplicationsPerHacker}"/>	</td>
		<td> <jstl:out value="${sDOfApplicationsPerHacker}"/>	</td>
	</tr>
</table>

<table>
	<tr>
		<th> <spring:message code="administrator.dashboard.avgOfSalariesOffered"/> </th>
		<th> <spring:message code='administrator.dashboard.minimumOfSalariesOffered'/> </th>
		<th> <spring:message code='administrator.dashboard.maximumOfSalariesOffered'/> </th>
		<th> <spring:message code='administrator.dashboard.sDOfSalariesOffered'/> 	  </th>
	</tr>
	<tr>
		<td> <jstl:out value="${avgOfSalariesOffered}"/>	</td>
		<td> <jstl:out value="${minimumOfSalariesOffered}"/>	</td>
		<td> <jstl:out value="${maximumOfSalariesOffered}"/>	</td>
		<td> <jstl:out value="${sDOfSalariesOffered}"/>	</td>
	</tr>
</table>

<table>
	<tr>
		<th> <spring:message code="administrator.dashboard.avgOfCurriculaPerHacker"/> </th>
		<th> <spring:message code='administrator.dashboard.minimumOfCurriculaPerHacker'/> </th>
		<th> <spring:message code='administrator.dashboard.maximumOfCurriculaPerHacker'/> </th>
		<th> <spring:message code='administrator.dashboard.sDOfCurriculaPerHacker'/> 	  </th>
	</tr>
	<tr>
		<td> <jstl:out value="${avgOfCurriculaPerHacker}"/>	</td>
		<td> <jstl:out value="${minimumOfCurriculaPerHacker}"/>	</td>
		<td> <jstl:out value="${maximumOfCurriculaPerHacker}"/>	</td>
		<td> <jstl:out value="${sDOfCurriculaPerHacker}"/>	</td>
	</tr>
</table>

<table>
	<tr>
		<th> <spring:message code="administrator.dashboard.avgOfResultsInFinders"/> </th>
		<th> <spring:message code='administrator.dashboard.minimumOfResultsInFinders'/> </th>
		<th> <spring:message code='administrator.dashboard.maximumOfResultsInFinders'/> </th>
		<th> <spring:message code='administrator.dashboard.sDOfResultsInFinders'/> 	  </th>
		<th> <spring:message code='administrator.dashboard.ratioOfEmptyVsNotEmptyFinders'/> 	  </th>
	</tr>
	<tr>
		<td> <jstl:out value="${avgOfResultsInFinders}"/>	</td>
		<td> <jstl:out value="${minimumOfResultsInFinders}"/>	</td>
		<td> <jstl:out value="${maximumOfResultsInFinders}"/>	</td>
		<td> <jstl:out value="${sDOfResultsInFinders}"/>	</td>
		<td> <jstl:out value="${ratioOfEmptyVsNotEmptyFinders}"/>	</td>
	</tr>
</table>

<table>
	<tr>
		<th> <spring:message code='administrator.dashboard.positionsWithTheBestSalary'/> </th>
		<th> <spring:message code='administrator.dashboard.positionsWithTheWorstSalary'/> </th>
	</tr>
	<tr>
		<td> 
			<jstl:forEach var="positionWithTheBestSalary" items="${positionsWithTheBestSalary}" >
				- <jstl:out value="${positionWithTheBestSalary.title} (${positionWithTheBestSalary.ticker.identifier})"/>
				<br/>
			</jstl:forEach>	
		</td>
		<td> 
			<jstl:forEach var="positionWithTheWorstSalary" items="${positionsWithTheWorstSalary}">
				- <jstl:out value="${positionWithTheWorstSalary.title} (${positionWithTheWorstSalary.ticker.identifier})"/>
				<br/>
			</jstl:forEach> 
		</td>
	</tr>
</table>

<table>
	<tr>
		<th> <spring:message code='administrator.dashboard.hackersWithMoreApplications'/> </th>
	</tr>
	<tr>
		<td> 
			<jstl:forEach var="hackerWithMoreApplications" items="${hackersWithMoreApplications}" >
				- <jstl:out value="${hackerWithMoreApplications.name}"/>
				<jstl:forEach var="surname" items="${hackerWithMoreApplications.surnames}" >
					<jstl:out value=" ${surname}"/>
				</jstl:forEach>
				<jstl:out value="(${hackerWithMoreApplications.id})"/>
				<br/>  
			</jstl:forEach>	
		</td>
	</tr>
</table>

<table>
	<tr>
		<th> <spring:message code='administrator.dashboard.companiesWithMoreOffersOfPositions'/> </th>
	</tr>
	<tr>
		<td> 
			<jstl:forEach var="companyWithMoreOffersOfPositions" items="${companiesWithMoreOffersOfPositions}" >
				- <jstl:out value="${companyWithMoreOffersOfPositions.companyName}"/>
				<jstl:out value="(${companyWithMoreOffersOfPositions.id})"/>
				
				<br/>
			</jstl:forEach>	
		</td>
	</tr>
</table>

