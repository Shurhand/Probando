<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme-Supermarket Co., Inc."
		height="100" width="400" />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv" href="consumer/administrator/list.do"><spring:message
						code="master.page.administrator.consumers" /></a></li>
			<li><a class="fNiv" href="order/administrator/list.do"><spring:message
						code="master.page.administrator.orders" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.administrator.taxes" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="tax/administrator/create.do"><spring:message
								code="master.page.administrator.create" /></a></li>
					<li><a href="tax/administrator/list.do"><spring:message
								code="master.page.administrator.list" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.catalogue" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="item/administrator/create.do"><spring:message
								code="master.page.administrator.create" /></a></li>
					<li><a href="item/administrator/categorylist.do"><spring:message
								code="master.page.administrator.list" /></a></li>
					<li><a href="item/administrator/list-itemsDeleted.do"><spring:message
								code="master.page.administrator.listDeleted" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.administrator.categories" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="category/administrator/create.do"><spring:message
								code="master.page.administrator.create" /></a></li>
					<li><a href="category/administrator/list.do"><spring:message
								code="master.page.administrator.list" /></a></li>
				</ul>
			<li><a class="fNiv" href="dashboard/administrator/list.do"><spring:message
						code="master.page.administrator.dashBoard" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('CONSUMER')">
			<li><a class="fNiv" href="shoppingCart/consumer/list.do"><spring:message
						code="master.page.consumer.shoppingCart" /></a></li>
			<li><a class="fNiv" href="order/consumer/list.do"><spring:message
						code="master.page.consumer.orders" /></a></li>
			<li><a class="fNiv" href="item/consumer/categorylist.do"><spring:message
						code="master.page.catalogue" /></a></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv" href="item/categorylist.do"><spring:message
						code="master.page.catalogue" /></a></li>
			<li><a class="fNiv" href="consumer/create.do"><spring:message
						code="master.page.consumer.create" /></a></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
				
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				<security:authorize access="hasRole('CONSUMER')">	
					<li><a href="consumer/modifyProfile.do"><spring:message
								code="master.page.modifyProfile" /> </a></li>
				
				
				</security:authorize>			
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<img src="images/icon_id_en.png"><a href="?language=en">en</a> |
	<img src="images/icon_id_es.png"><a href="?language=es">es</a>
</div>

