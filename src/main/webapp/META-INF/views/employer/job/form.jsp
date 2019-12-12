<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	
	<acme:form-textarea code="employer.job.form.label.reference" path="reference"/>
	<acme:form-textbox code="employer.job.form.label.title" path="title"/>
	<acme:form-moment code="employer.job.form.label.deadline" path="deadline"/>
	<acme:form-money code="employer.job.form.label.salary" path="salary"/>
	<acme:form-textbox code="employer.job.form.label.moreInfo" path="moreInfo"/>
	<jstl:set var="idJob" value="${id}"/>
	<jstl:set var="jobId" value="${id}"/>
	<h4><acme:menu-suboption code="employer.job.form.label.duties" action="/employer/descriptor/show?jobId=${jobId}"/></h4>
  	<h4><acme:menu-suboption code="employer.job.form.label.auditRecords" action="/employer/auditrecord/list_mine?id=${idJob}"/></h4>
	

	<acme:form-submit test="${command == 'show'}" code="employer.job.form.button.update" action="/employer/job/update"/>
	<acme:form-submit test="${command == 'update'}" code="employer.job.form.button.update" action="/employer/job/update"/>
	<acme:form-submit test ="${command == 'show'}" code="employer.job.form.button.delete" action="/employer/job/delete?id=${jobId}"/>
	<acme:form-submit test ="${command == 'delete'}" code="employer.job.form.button.delete" action="/employer/job/delete?id=${jobId}"/>
	<acme:form-return code="employer.job.form.label.button.return"/>
</acme:form>