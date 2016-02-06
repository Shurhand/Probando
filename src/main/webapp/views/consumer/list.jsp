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

<display:table name="consumers" id="row"
	requestURI="consumer/administrator/list.do" pagesize="5"
	class="displaytag">


	<!-- Attributes -->

	<spring:message code="actor.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="actor.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}"
		sortable="false" />
	
	<spring:message code="actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />
	
	<spring:message code="actor.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true" />
	
	<spring:message code="consumer.create.creditCard" var="creditCardHeader" />
	<display:column property="creditCard.number" title="${creditCardHeader}" sortable="true" />
	
	<spring:message code="actor.address" var="addressHeader" />
	<display:column property="address" title="${addressHeader}"
		sortable="true" />


</display:table>

<!-- Action links -->

</body>
</html>