<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="tax/administrator/edit.do" modelAttribute="tax">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="used" />
	

	<form:label path="nameCategory">
		<spring:message code="tax.nameCategory" />:
	</form:label>
	<form:input path="nameCategory" />
	<form:errors cssClass="error" path="nameCategory" />
	<br />

	<form:label path="percent">
		<spring:message code="tax.percent" />:
	</form:label>
	<form:input path="percent" />
	<form:errors cssClass="error" path="percent" />
	<br />


	<form:label path="categories">
		<spring:message code="tax.categories" />:
	</form:label>
	<form:select id="categories" path="categories">
		<form:option value="0" label="----" />		
		<form:options items="${categories}" itemLabel="name" itemValue="id"	/>
	</form:select>
	<form:errors cssClass="error" path="categories" />
	<br />

	<input type="submit" name="save" value="<spring:message code="tax.save" />" />&nbsp; 
	<jstl:if test="${tax.id != 0}">
		<input type="submit" name="delete" 
			value="<spring:message code="tax.delete" />"
			onclick="return confirm('<spring:message code="tax.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="tax.cancel" />"
		onclick="javascript: window.location.replace('tax/administrator/list.do');" />
	<br />

</form:form>
