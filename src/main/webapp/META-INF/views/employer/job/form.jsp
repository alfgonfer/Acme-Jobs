<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	
	<jstl:set var="read" value="true"/>
	<jstl:if test="${command == 'create'}">
	<jstl:set var="read" value="false"/>
	</jstl:if>
	
	<acme:form-textarea code="employer.job.form.label.reference" path="reference" readonly="${read}"/>
	<acme:form-textbox code="employer.job.form.label.title" path="title"/>
	
	<jstl:if test="${command == 'create'}">
	<acme:form-textarea code="employer.job.form.label.description" path="description"/>
	</jstl:if>
	
	<acme:form-moment code="employer.job.form.label.deadline" path="deadline"/>
	<acme:form-money code="employer.job.form.label.salary" path="salary"/>
	<acme:form-textbox code="employer.job.form.label.moreInfo" path="moreInfo"/>
	
	<jstl:set var="idJob" value="${id}"/>
	<jstl:set var="jobId" value="${id}"/>
	<jstl:if test="${command != 'create'}">
	<h4><acme:menu-suboption code="employer.job.form.label.duties" action="/employer/descriptor/show?jobId=${jobId}"/></h4>
  	<h4><acme:menu-suboption code="employer.job.form.label.auditRecords" action="/employer/auditrecord/list_mine?id=${idJob}"/></h4>
	</jstl:if>

	<acme:form-submit test ="${command == 'create'}" code="employer.job.form.button.create" action="/employer/job/create"/>
	<acme:form-submit test="${command == 'show'}" code="employer.job.form.button.update" action="/employer/job/update"/>
	<acme:form-submit test="${command == 'update'}" code="employer.job.form.button.update" action="/employer/job/update"/>
	<acme:form-return code="employer.job.form.label.button.return"/>
</acme:form>