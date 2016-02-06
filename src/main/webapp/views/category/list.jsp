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

<!-- Listing grid -->

<display:table name="categories" id="row"
	requestURI="category/administrator/list.do" pagesize="5"
	class="displaytag">

	<!-- Action links -->

	<display:column>
		<a href="category/administrator/edit.do?categoryID=${row.id}"> <spring:message
				code="category.edit" />
		</a>
	</display:column>

	<!-- Attributes -->

	<spring:message code="category.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="category.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="false" />

	<spring:message code="category.picture" var="pictureHeader" />
	<display:column title="${pictureHeader}" sortable="false" >
	<IMG src="${row.picture}" width="100" height="100">
	</display:column>
	<spring:message code="category.tax" var="taxHeader" />
	<display:column property="tax.nameCategory" title="${taxHeader}"
		sortable="true" />
</display:table>

<!-- Action links -->

<div>
	<a href="category/administrator/create.do"> <spring:message
			code="category.create" />
	</a>
</div>
</body>
</html>