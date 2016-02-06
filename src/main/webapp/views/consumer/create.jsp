


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form action="consumer/create.do" modelAttribute="consumer">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="shoppingCart" />
	<form:hidden path="cashOrders" />
	<form:hidden path="userAccount.authorities" />
	
	
		<b><spring:message code="consumer.create.PersonalData" /></b>

	<br/>

	<form:label path="name">
		<spring:message code="consumer.create.name" />
	</form:label>
	<form:input path="name"/>
	<form:errors class="error" path="name" />
	<br />

	<form:label path="surname">
		<spring:message code="consumer.create.surname" />
	</form:label>
	<form:input path="surname" />
	<form:errors class="error" path="surname" />
	<br />

	<form:label path="address">
		<spring:message code="consumer.create.address" />
	</form:label>
	<form:input path="address" />
	<form:errors class="error" path="address" />
	<br />

	<form:label path="email">
		<spring:message code="consumer.create.email" />
	</form:label>
	<form:input path="email" />
	<form:errors class="error" path="email" />
	<br />

	<form:label path="phone">
		<spring:message code="consumer.create.phone" />
	</form:label>
	<form:input path="phone" />
	<form:errors class="error" path="phone" />
	<br />
	
		<b><spring:message code="consumer.create.creditCard" /></b>
	
	<br/>

	<form:label path="creditCard.holderName">
		<spring:message code="consumer.create.holderName" />
	</form:label>
	<form:input path="creditCard.holderName" />
	<form:errors class="error" path="creditCard.holderName" />
	<br />

	<form:label path="creditCard.brandName">
		<spring:message code="consumer.create.brandName" />
	</form:label>
	<form:input path="creditCard.brandName" />
	<form:errors class="error" path="creditCard.brandName" />
	<br />

	<form:label path="creditCard.number">
		<spring:message code="consumer.create.number" />
	</form:label>
	<form:input path="creditCard.number" />
	<form:errors class="error" path="creditCard.number" />
	<br />

	<form:label path="creditCard.expirationMonth">
		<spring:message code="consumer.create.expirationMonth" />
	</form:label>
	<form:input path="creditCard.expirationMonth" />
	<form:errors class="error" path="creditCard.expirationMonth" />
	<br />

	<form:label path="creditCard.expirationYear">
		<spring:message code="consumer.create.expirationYear" />
	</form:label>
	<form:input path="creditCard.expirationYear" />
	<form:errors class="error" path="creditCard.expirationYear" />
	<br />

	<form:label path="creditCard.cvvCode">
		<spring:message code="consumer.create.cvvCode" />
	</form:label>
	<form:input path="creditCard.cvvCode" />
	<form:errors class="error" path="creditCard.cvvCode" />
	<br />
	
			<b><spring:message code="consumer.create.LoginData" /></b>
	
	<br/>

	<form:label path="userAccount.username">
		<spring:message code="consumer.create.username" />
	</form:label>
	<form:input path="userAccount.username" />
	<form:errors class="error" path="userAccount.username" />
	<br />
	
	
	<form:label path="userAccount.password">
		<spring:message code="consumer.create.password" />
	</form:label>
	<form:password path="userAccount.password" />
	<form:errors class="error" path="userAccount.password" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="consumer.create.signIn" />" />&nbsp; 

	<spring:message code="consumer.create.cancelConfirm"
		var="cancelConfirm" />
	<input type="button"
		value="<spring:message code="consumer.create.cancel" />"
		onclick="return confirm('${cancelConfirm}')" />


</form:form>
