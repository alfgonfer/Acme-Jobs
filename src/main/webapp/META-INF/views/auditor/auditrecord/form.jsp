<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="auditor.auditrecord.form.label.title" path="title"/>
	<jstl:if test="${command == 'show'}">
	<acme:form-textbox code="auditor.auditrecord.form.label.status" path="status"/>
	</jstl:if>
	<jstl:if test="${command != 'create'}">
	<acme:form-moment code="auditor.auditrecord.form.label.moment" path="moment" readonly="true" />
		</jstl:if>
	<acme:form-textbox code="auditor.auditrecord.form.label.body" path="body"/>
	<jstl:if test="${command == 'create'}">
		<acme:form-textbox code="auditor.auditrecord.form.label.jref" path="jref" />
		<acme:form-checkbox code="auditor.auditrecord.form.checkbox.status" path="status"/>
	</jstl:if>
	<acme:form-submit test="${command == 'create'}" code="auditor.auditrecord.form.button.create"
		action="/auditor/auditrecord/create" />
	
	<acme:form-return code="auditor.auditrecord.form.label.button.return"/>
</acme:form>