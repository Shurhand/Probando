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

<display:table name="taxes" id="row"
	requestURI="tax/administrator/list.do" pagesize="5" class="displaytag">

	<!-- Action links -->

	<display:column>
		<a href="tax/administrator/edit.do?taxID=${row.id}"> <spring:message
				code="tax.edit" />
		</a>
	</display:column>

	<!-- Attributes -->

	<spring:message code="tax.nameCategory" var="nameCategoryHeader" />
	<display:column property="nameCategory" title="${nameCategoryHeader}"
		sortable="true" />

	<spring:message code="tax.percent" var="percentHeader" />
	<display:column property="percent" title="${percentHeader}"
		sortable="true" />

	<spring:message code="tax.categories" var="categoriesHeader" />
	<display:column property="categories" title="${categoriesHeader}"
		sortable="false" />
				
	<spring:message code="tax.checkCategories" var="checkCategories" />

		<display:column title="${checkCategories}" sortable="false">
			<a href=tax/administrator/listcategories.do?taxID=${row.id}> <spring:message
					code="tax.seeCategories" />
			</a>
		</display:column>

</display:table>

<!-- Action links -->

<div>
	<a href="tax/administrator/create.do"> <spring:message
			code="tax.create" />
	</a>
</div>
</body>
</html>