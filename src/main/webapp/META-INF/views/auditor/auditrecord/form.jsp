<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="auditor.auditrecord.form.label.title" path="title" />
	<jstl:if test="${command != 'create'}">
	<acme:form-moment code="auditor.auditrecord.form.label.moment" path="moment" readonly="true" />
		</jstl:if>
	<acme:form-textarea code="auditor.auditrecord.form.label.body" path="body" />
	<jstl:if test="${command == 'create'}">
		<acme:form-checkbox code="auditor.auditrecord.form.checkbox.status" path="status"/>
	</jstl:if>
	<jstl:if test="${isFinalMode == false}">
	<acme:form-select code="auditor.auditrecord.form.label.finalMode" path="isFinalMode">
	<acme:form-option code="auditor.auditrecord.form.label.finalMode.true" value="true"/>
	</acme:form-select>
	</jstl:if>
	<acme:form-submit test="${command == 'create'}" code="auditor.auditrecord.form.button.create"
		action="/auditor/auditrecord/create?idJob=${param.idJob}" />
	<acme:form-submit test="${command == 'show'}" code="auditor.auditrecord.form.button.update" action="/auditor/auditrecord/update?idJob=${param.idJob}"/>
    <acme:form-submit test="${command == 'update'}" code="auditor.auditrecord.form.button.update" action="/auditor/auditrecord/update?idJob=${param.idJob}"/>
		
	
	<acme:form-return code="auditor.auditrecord.form.label.button.return"/>
</acme:form>