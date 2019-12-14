<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	
	<acme:form-textbox code="authenticated.sponsor.form.label.orgName" path="orgName"/>
	<jstl:if test="${command == 'create'}">
	<h4><acme:message code="authenticated.sponsor.form.message"/></h4>
	<acme:form-textbox code="authenticated.sponsor.form.label.creditNumber" path="creditNumber"/>
	<acme:form-textbox code="authenticated.sponsor.form.label.name" path="name"/>
	<acme:form-textbox code="authenticated.sponsor.form.label.surname" path="surname"/>
	<acme:form-moment code="authenticated.sponsor.form.label.expiration" path="expiration"/>
	<acme:form-textbox code="authenticated.sponsor.form.label.securityCode" path="securityCode"/>
	</jstl:if>
	
	<jstl:if test="${command != 'create'}">
	<h4><acme:menu-suboption code="authenticated.sponsor.form.creditCard" action="/authenticated/creditcard/update?id=${id}"/></h4>
	</jstl:if>
	<acme:form-submit test="${command == 'create'}" code="authenticated.sponsor.form.button.create" action="/authenticated/sponsor/create"/>
	<acme:form-submit test="${command == 'update'}" code="authenticated.sponsor.form.button.update" action="/authenticated/sponsor/update"/>
	<acme:form-return code="authenticated.sponsor.form.label.button.return"/>
	
</acme:form>