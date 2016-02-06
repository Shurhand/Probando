<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<jstl:choose>
	<jstl:when
		test="${comments.size() != 0 || contentsShoppingCart.size() != 0}">
		<spring:message code="shoppingCart.listComments" var="listComments" />

		<h2>
			<jstl:out value="${listComments}" />
		</h2>

		<display:table name="shoppingCart" id="row"
			requestURI="shoppingCart/consumer/list.do" pagesize="5"
			class="displaytag">


			<spring:message code="shoppingCart.noComments" var="noComments" />
			<spring:message code="shoppingCart.comments" var="scComments" />
			<display:column title="${scComments}" sortable="false">
				<jstl:choose>
					<jstl:when test="${comments.size() != 0 }">
						<jstl:forEach var="comment" items="${comments}">
							<jstl:out value="${comment}" />
							<br />
						</jstl:forEach>
					</jstl:when>
					<jstl:otherwise>
						<jstl:out value="${noComments}" />
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
			
		</display:table>
		<div>
			<a href="shoppingCart/consumer/edit.do?shoppingCartID=${row.id}">
				<spring:message code="shoppingCart.comments.edit" />
			</a>
		</div>
		<spring:message code="shoppingCart.contents" var="contents" />
		<h2>
			<jstl:out value="${contents}" />
		</h2>
		<jstl:choose>
			<jstl:when test="${contentsShoppingCart.size() != 0 }">
				<display:table name="contentsShoppingCart" id="row"
					requestURI="shoppingCart/listContents.do" pagesize="5"
					class="displaytag">

					<!-- Action links -->


					<!-- Attributes -->
					<spring:message code="shoppingCart.contents.item.sku"
						var="SKUShoppingCart" />
					<display:column property="item.sku" title="${SKUShoppingCart}"
						sortable="true" />

					<spring:message code="shoppingCart.contents.quantity"
						var="quantity" />
					<display:column property="quantity" title="${quantity}"
						sortable="true" />

					<spring:message code="shoppingCart.item" var="itemShoppingCart" />
					<display:column property="item.name" title="${itemShoppingCart}"
						sortable="true" />

					<spring:message code="shoppingCart.contents.item.price"
						var="itemPrice" />
					<display:column property="item.price" title="${itemPrice}"
						sortable="true" />
		
					<spring:message code="shoppingCart.contents.item.category.tax"
						var="itemPrice" />
					<display:column property="item.category.tax.nameCategory" title="${itemPrice}"
						sortable="true" />	

					<display:column>
						<a href="content/consumer/edit.do?contentID=${row.id}"> <spring:message
								code="shoppingCart.contents.edit" />
						</a>
					</display:column>
				</display:table>
			</jstl:when>
			<jstl:otherwise>
				<display:table name="total" id="row" class="displaytag">
					<spring:message code="shoppingCart.cEmpty" var="cEmpty" />
					<spring:message code="shoppingCart.contents" var="contentsempty" />
					<display:column title="${contentsempty}" sortable="false">

						<jstl:out value="${cEmpty}" />

					</display:column>
				</display:table>
			</jstl:otherwise>
		</jstl:choose>
		<display:table name="total" id="row" class="displaytag">
			<spring:message code="shoppingCart.contents.item.total"
				var="totalPrice" />
			<display:column title="${totalPrice}" sortable="false">
				<jstl:choose>
					<jstl:when test="${contentsShoppingCart.size() != 0 }">
						<jstl:out value="${total} Euros" />
					</jstl:when>
					<jstl:otherwise>
						<jstl:out value="0.00 Euros" />
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
		</display:table>
		<div>
			<a
				href="shoppingCart/consumer/placeOrder.do?shoppingCartID=${shoppingCart.id}">
				<spring:message code="shoppingCart.placeOrder" />
			</a>
		</div>
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="shoppingCart.empty" var="scEmpty" />
		<h2>
			<jstl:out value="${scEmpty}" />
		</h2>
	</jstl:otherwise>
</jstl:choose>