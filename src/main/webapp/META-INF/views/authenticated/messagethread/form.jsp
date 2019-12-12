<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="authenticated.messagethread.form.label.title" path="title"/>
	<acme:form-moment code="authenticated.messagethread.form.label.moment" path="moment"/>
	<acme:form-textbox code="authenticated.messagethread.form.label.usernames" path="usernames"/>
	<jstl:set var="id" value="${id}"/>
	<h4><acme:menu-suboption code="authenticated.messagethread.form.button.message" action="/authenticated/message/list_mine?id=${id}"/></h4>
    <h4><acme:menu-suboption code="authenticated.messagethread.form.button.message.create" action="/authenticated/message/create?id=${id}"/></h4>

	<acme:form-return code="authenticated.messagethread.form.label.button.return"/>
	
		
</acme:form>