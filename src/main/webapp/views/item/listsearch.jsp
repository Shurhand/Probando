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

<display:table name="items" id="row" requestURI="item/listsearch.do"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="item/administrator/edit.do?itemID=${row.id}"> <spring:message
					code="item.edit" />
			</a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('CONSUMER')">
		<display:column>
			<a href="item/consumer/add.do?itemID=${row.id}"> <spring:message
					code="item.add" />
			</a>
		</display:column>
	</security:authorize>
	
	
	<spring:message code="item.sku" var="skuHeader" />
	<display:column property="sku" title="${skuHeader}" sortable="true" />
	

	<spring:message code="item.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="item.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="false" />

	<spring:message code="item.price" var="priceHeader" />
	<display:column property="price" title="${priceHeader}" sortable="true" />

	<spring:message code="item.picture" var="pictureHeader" />
	<display:column title="${pictureHeader}"
		sortable="false" ><IMG src="${row.picture}" width="100" height="100">
	</display:column>

	<spring:message code="item.tags" var="tagsHeader" />
	<display:column property="tags" title="${tagsHeader}" sortable="false" />

	<spring:message code="item.category" var="categoryHeader" />
	<display:column property="category.name" title="${categoryHeader}"
		sortable="true" />

	<spring:message code="item.tax" var="taxHeader" />
	<display:column property="category.tax.nameCategory" title="${taxHeader}"
		sortable="true" />
</display:table>

<security:authorize access="hasRole('ADMIN')">
	<div>
		<a href="item/administrator/create.do"> <spring:message
				code="item.create" />
		</a>
		<div>
			<a href="item/categorylist.do"> <spring:message
					code="item.vueltatras" />
			</a>
		</div>
	</div>
</security:authorize>
<security:authorize access="hasRole('CONSUMER')">
	<div>
		<a href="item/consumer/categorylist.do"> <spring:message
				code="item.vueltatras" />
		</a>
	</div>
</security:authorize>
<security:authorize access="isAnonymous()">
	<div>
		<a href="item/categorylist.do"> <spring:message
				code="item.vueltatras" />
		</a>
	</div>
</security:authorize>

</body>
</html>








