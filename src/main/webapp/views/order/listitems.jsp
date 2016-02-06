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


<display:table name="ordereditems" id="row" requestURI="order/listitems.do"
	pagesize="5" class="displaytag">
		
	<spring:message code="order.ordereditem.sku" var="skuHeader" />
	<display:column property="sku" title="${skuHeader}" sortable="true" />
	
	<spring:message code="order.ordereditem.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="order.ordereditem.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="false" />

	<spring:message code="order.ordereditem.price" var="priceHeader" />
	<display:column property="price" title="${priceHeader}" sortable="true" />

	<spring:message code="order.ordereditem.quantity" var="quantityHeader" />
	<display:column property="quantity" title="${quantityHeader}"
		sortable="true" />
</display:table>
<display:table name="total" id="row" class="displaytag">
			<spring:message code="shoppingCart.contents.item.total"
				var="totalPrice" />
			<display:column title="${totalPrice}" sortable="false">
						<jstl:out value="${total} Euros" />
			</display:column>
		</display:table>
<security:authorize access="hasRole('ADMIN')">
<div>
			<a href="order/administrator/list.do"> <spring:message
					code="order.vueltatras" />
			</a>
		</div>
</security:authorize>
<security:authorize access="hasRole('CONSUMER')">
	<div>
		<a href="order/consumer/list.do"> <spring:message
				code="order.vueltatras" />
		</a>
	</div>
</security:authorize>
</body>
</html>








