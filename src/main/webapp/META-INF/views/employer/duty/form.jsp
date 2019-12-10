<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textarea code="authenticated.duty.form.label.jobTitle" path="jobTitle" readonly="true"/>
	<acme:form-integer code="authenticated.duty.form.label.jobId" path="jobId" readonly="true"/>
	
	<acme:form-textarea code="authenticated.duty.form.label.title" path="title" readonly="true"/>
	<acme:form-textbox code="authenticated.duty.form.label.percentage" path="percentage" readonly="true"/>
	<acme:form-textarea code="authenticated.duty.form.label.description" path="description"/>
	

      <acme:form-submit test="${command == 'show'}" code="authenticated.duty.form.button.update" action="/employer/duty/update"/>
	<acme:form-submit test="${command == 'update'}" code="authenticated.duty.form.button.update" action="/employer/duty/update"/>
	<acme:form-return code="authenticated.job.form.label.button.return"/>
</acme:form>