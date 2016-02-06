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

<form:form action="content/consumer/edit.do" modelAttribute="content">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="item" />
	<form:hidden path="shoppingCart" />

	<form:label path="quantity">
		<spring:message code="content.quantity" />:</form:label>
	<form:input path="quantity" />
	<form:errors cssClass="error" path="quantity" />
	<br />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="content.save" />" />&nbsp; 
	
	<jstl:if test="${content.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="content.delete" />"
			onclick="return confirm('<spring:message code="content.confirm.delete" />')" />&nbsp;
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="content.cancel" />"
		onclick="javascript: window.location.replace('shoppingCart/consumer/list.do')" />
	<br />

</form:form>

