<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="authenticated.messagethread.form.label.title" path="title"/>
	<jstl:if test="${command != 'create'}">
	<acme:form-moment code="authenticated.messagethread.form.label.moment" path="moment"/>
	</jstl:if>
	<acme:form-textbox code="authenticated.messagethread.form.label.usernames" path="usernames"/>
	<h6><acme:message code="authenticated.messagethread.message.usernames"/></h6>
	<jstl:if test="${command != 'create'}">
	<jstl:set var="id" value="${id}"/>
	<h4><acme:menu-suboption code="authenticated.messagethread.form.button.message" action="/authenticated/message/list_mine?id=${id}"/></h4>
    <h4><acme:menu-suboption code="authenticated.messagethread.form.button.message.create" action="/authenticated/message/create?id=${id}"/></h4>
	</jstl:if>
	<acme:form-submit test ="${command == 'create'}" code="authenticated.messagethread.button.create" action="/authenticated/messagethread/create"/>
	<acme:form-return code="authenticated.messagethread.form.label.button.return"/>
	
		
</acme:form>