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


<display:table name="items" id="row" requestURI="item/list-itemsDeleted.do"
	pagesize="5" class="displaytag">


	<display:column>
			<a href="item/administrator/edit.do?itemID=${row.id}"> <spring:message
					code="item.edit" />
			</a>
	</display:column>
		
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
	
	
	<display:column>
			<a href="item/administrator/delete.do?itemID=${row.id}"> <spring:message
					code="item.undelete" />
		</a>
	</display:column>
	
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
</body>
</html>








