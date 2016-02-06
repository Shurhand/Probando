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

<display:table name="orders" id="row"
	requestURI="order/administrator/list.do" pagesize="5"
	class="displaytag">

	<!-- Attributes -->
	

	<spring:message code="order.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}"
		sortable="true" />

	<spring:message code="order.consumerName" var="consumerNameHeader" />
	<display:column property="consumerName" title="${consumerNameHeader}"
		sortable="true" />

	<spring:message code="order.consumerAddress"
		var="consumerAddressHeader" />
	<display:column property="consumerAddress" title="${consumerAddressHeader}"
		sortable="false" />

	<spring:message code="order.creditCard" var="creditCardHeader" />
	<display:column property="creditCard.number"
		title="${creditCardHeader}" sortable="false" />

	<spring:message code="order.comments" var="commentsHeader" />
	<display:column property="comments" title="${commentsHeader}"
		sortable="false" />
	<jstl:forEach var="c" items="${row.comments}">
		<jstl:out value="${c}"></jstl:out>
	</jstl:forEach>

	<spring:message code="order.placementMoment"
		var="placementMomentHeader" />
	<display:column property="placementMoment"
		title="${placementMomentHeader}" sortable="true"
		format="{0, date, dd/MM/yyyy HH:mm}" />

	<spring:message code="order.deliveryDate" var="deliveryDateHeader" />
	<display:column property="deliveryDate" title="${deliveryDateHeader}"
		sortable="true" format="{0, date, dd/MM/yyyy HH:mm}" />

	<spring:message code="order.cancellationDate"
		var="cancellationDateHeader" />
	<display:column property="cancellationDate"
		title="${cancellationDateHeader}" sortable="true"
		format="{0, date, dd/MM/yyyy HH:mm}" />
		
		<spring:message code="order.ordereditems" var="ordereditemsHeader" />
	<security:authorize access="hasRole('CONSUMER')">
		<display:column title="${ordereditemsHeader}" sortable="false">
			<a href=order/consumer/listitems.do?orderID=${row.id}> <spring:message
					code="order.ordereditems.seeItems" />
			</a>
		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('ADMIN')">
		<display:column title="${ordereditemsHeader}" sortable="false">
			<a href=order/administrator/listitems.do?orderID=${row.id}> <spring:message
					code="order.ordereditems.seeItems" />
			</a>
		</display:column>
	</security:authorize>

</display:table>
