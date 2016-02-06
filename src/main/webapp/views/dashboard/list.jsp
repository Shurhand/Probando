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

<!-- The consumer/s who has/have placed more orders -->

<spring:message code="dashboard.consumer.dashboardMoreOrders" />
<display:table name="consumersMoreOrders" id="row"
	requestURI="dashboard/administrator/display.do" pagesize="5"
	class="displaytag">

	<!-- Attributes -->

	<spring:message code="dashboard.consumer.name"
		var="nameHeaderMoreOrders" />
	<display:column property="name" title="${nameHeaderMoreOrders}"
		sortable="true" />

	<spring:message code="dashboard.consumer.surnames"
		var="surnamesHeaderMoreOrders" />
	<display:column property="surname" title="${surnamesHeaderMoreOrders}"
		sortable="false" />

	<spring:message code="dashboard.consumer.email"
		var="emailHeaderMoreOrders" />
	<display:column property="email" title="${emailHeaderMoreOrders}"
		sortable="false" />

	<spring:message code="dashboard.consumer.address"
		var="addressHeaderMoreOrders" />
	<display:column property="address" title="${addressHeaderMoreOrders}"
		sortable="false" />

	<spring:message code="dashboard.consumer.phone"
		var="phoneHeaderMoreOrders" />
	<display:column property="phone" title="${phoneHeaderMoreOrders}"
		sortable="false" />

</display:table>

<!-- The consumer/s who has/have spent more money on their orders -->

<spring:message code="dashboard.consumer.dashboardMoreMoneyOnOrders" />
<display:table name="consumersMoreMoneyOnOrders" id="row"
	requestURI="dashboard/administrator/display.do" pagesize="5"
	class="displaytag">

	<!-- Attributes -->

	<spring:message code="dashboard.consumer.name"
		var="nameHeaderMoreMoneyOnOrders" />
	<display:column property="name" title="${nameHeaderMoreMoneyOnOrders}"
		sortable="true" />

	<spring:message code="dashboard.consumer.surnames"
		var="surnamesHeaderMoreMoneyOnOrders" />
	<display:column property="surname"
		title="${surnamesHeaderMoreMoneyOnOrders}" sortable="false" />

	<spring:message code="dashboard.consumer.email"
		var="emailHeaderMoreMoneyOnOrders" />
	<display:column property="email"
		title="${emailHeaderMoreMoneyOnOrders}" sortable="false" />

	<spring:message code="dashboard.consumer.address"
		var="addressHeaderMoreMoneyOnOrders" />
	<display:column property="address"
		title="${addressHeaderMoreMoneyOnOrders}" sortable="false" />

	<spring:message code="dashboard.consumer.phone"
		var="phoneHeaderMoreMoneyOnOrders" />
	<display:column property="phone"
		title="${phoneHeaderMoreMoneyOnOrders}" sortable="false" />

</display:table>

<!-- The best-selling item/s in the inventory -->

<spring:message code="dashboard.item.dashboardBestSeller" />
<display:table name="itemsBestSeller" id="row"
	requestURI="dashboard/administrator/display.do" pagesize="5"
	class="displaytag">

	<!-- Attributes -->

	<spring:message code="dashboard.item.sku" var="skuHeaderBestSeller" />
	<display:column property="sku" title="${skuHeaderBestSeller}"
		sortable="true" />

	<spring:message code="dashboard.item.name" var="nameHeaderBestSeller" />
	<display:column property="name" title="${nameHeaderBestSeller}"
		sortable="false" />

	<spring:message code="dashboard.item.description"
		var="descriptionHeaderBestSeller" />
	<display:column property="description"
		title="${descriptionHeaderBestSeller}" sortable="false" />

	<spring:message code="dashboard.item.price" var="priceHeaderBestSeller" />
	<display:column property="price" title="${priceHeaderBestSeller}"
		sortable="true" />

	<spring:message code="dashboard.item.tags" var="tagsHeaderBestSeller" />
	<display:column property="tags" title="${tagsHeaderBestSeller}"
		sortable="false" />
	<jstl:forEach var="t" items="${row.tags}">
		<jstl:out value="${t}"></jstl:out>
	</jstl:forEach>

	<spring:message code="dashboard.item.picture"
		var="pictureHeaderBestSeller" />
	<display:column property="picture" title="${pictureHeaderBestSeller}"
		sortable="false" />

	<spring:message code="dashboard.item.category"
		var="categoryHeaderBestSeller" />
	<display:column property="category.name"
		title="${categoryHeaderBestSeller}" sortable="true" />

</display:table>

<!-- The worst-selling item/s in the inventory -->

<spring:message code="dashboard.item.dashboardWorstSeller" />
<display:table name="itemsWorstSeller" id="row"
	requestURI="dashboard/administrator/display.do" pagesize="5"
	class="displaytag">

	<!-- Attributes -->

	<spring:message code="dashboard.item.sku" var="skuHeaderWorstSeller" />
	<display:column property="sku" title="${skuHeaderWorstSeller}"
		sortable="true" />

	<spring:message code="dashboard.item.name" var="nameHeaderWorstSeller" />
	<display:column property="name" title="${nameHeaderWorstSeller}"
		sortable="false" />

	<spring:message code="dashboard.item.description"
		var="descriptionHeaderWorstSeller" />
	<display:column property="description"
		title="${descriptionHeaderWorstSeller}" sortable="false" />

	<spring:message code="dashboard.item.price"
		var="priceHeaderWorstSeller" />
	<display:column property="price" title="${priceHeaderWorstSeller}"
		sortable="true" />

	<spring:message code="dashboard.item.tags" var="tagsHeaderWorstSeller" />
	<display:column property="tags" title="${tagsHeaderWorstSeller}"
		sortable="false" />
	<jstl:forEach var="t" items="${row.tags}">
		<jstl:out value="${t}"></jstl:out>
	</jstl:forEach>

	<spring:message code="dashboard.item.picture"
		var="pictureHeaderWorstSeller" />
	<display:column property="picture" title="${pictureHeaderWorstSeller}"
		sortable="false" />

	<spring:message code="dashboard.item.category"
		var="categoryHeaderWorstSeller" />
	<display:column property="category.name"
		title="${categoryHeaderWorstSeller}" sortable="true" />

</display:table>

