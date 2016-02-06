<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="shoppingCart/consumer/edit.do" modelAttribute="shoppingCart">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="contents" />
	<form:hidden path="consumer" />


	<form:label path="comments">
	<spring:message code="shoppingCart.comments"/>:</form:label>
	<form:input path="comments" />
	<form:errors cssClass="error" path="comments" />
	<br /><br />
	
	<input type="submit" name="save" value="<spring:message code="shoppingCart.comments.save" />" />&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="shoppingCart.comments.cancel"/>"
		onclick="javascript: window.location.replace('shoppingCart/consumer/list.do')" />
	

</form:form>

