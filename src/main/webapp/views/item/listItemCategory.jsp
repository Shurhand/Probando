<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	
<spring:message code="item.searchLabel" var="textSearch" />
	<h4><jstl:out value="${textSearch}"/></h4>
		<form:form action="item/listsearch.do" method="POST">
			<input type="text" name="keyword" />
			<input type="submit" name="search"
		value="<spring:message code="item.search" />" />&nbsp;
</form:form>



<spring:message code="item.category.select" var="text" />
<h4>
	<jstl:out value="${text}" />
</h4>
<display:table name="categories" id="row"
	requestURI="item/categorylist.do" pagesize="5" class="displaytag">

	<!-- Attributes -->

	<spring:message code="item.category.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="item.category.description"
		var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="false" />

	<spring:message code="item.category.picture" var="pictureHeader" />
	<display:column title="${pictureHeader}"
		sortable="false">
		<IMG src="${row.picture}" width="100" height="100">
	</display:column>


	<spring:message code="item.category.items" var="itemsHeader" />
	<security:authorize access="hasRole('CONSUMER')">
		<display:column title="${itemsHeader}" sortable="false">
			<a href=item/consumer/list.do?categoryID=${row.id}> <spring:message
					code="item.category.seeItems" />
			</a>
		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('ADMIN')">
		<display:column title="${itemsHeader}" sortable="false">
			<a href=item/administrator/list.do?categoryID=${row.id}> <spring:message
					code="item.category.seeItems" />
			</a>
		</display:column>
	</security:authorize>
	<security:authorize access="isAnonymous()">
		<display:column title="${itemsHeader}" sortable="false">
			<a href=item/list.do?categoryID=${row.id}> <spring:message
					code="item.category.seeItems" />
			</a>
		</display:column>
	</security:authorize>


</display:table>
