<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="worker.application.form.label.reference" path="reference"/>
	<jstl:if test="${command != 'create'}">
	<acme:form-moment code="consumer.offers.form.label.moment" path="moment"/>
	<acme:form-textbox code="consumer.application.form.label.satus" path="status"/>
	</jstl:if>
	<jstl:if test="${command == 'create' }">
	<acme:form-textbox code="worker.application.form.label.status" path="status" readonly="true"/>
	</jstl:if>
	<acme:form-textarea code="worker.application.form.label.skills" path="skills"/>
	<acme:form-textbox code="worker.application.form.label.statement" path="statement"/>
	<acme:form-textarea code="worker.application.form.label.qualifications" path="qualifications"/>

	
	<acme:form-submit test ="${command == 'create'}" code="worker.application.form.label.button.create" action="/worker/application/create?jobId=${param.jobId}"/>
	<acme:form-return code="worker.application.form.label.button.return"/>
</acme:form>








<!-- 

<acme:form>
	<acme:form-textbox code="consumer.offers.form.label.title" path="title"/>
	<acme:form-textarea code="consumer.offers.form.label.description" path="description"/>
	
	<jstl:if test="${command != 'create'}">
	<acme:form-moment code="consumer.offers.form.label.moment" path="moment"/>
	</jstl:if>
	
	<acme:form-moment code="consumer.offers.form.label.deadline" path="deadline"/>
	
	<acme:form-money code="consumer.offers.form.label.major" path="majorRange"/>
	<acme:form-money code="consumer.offers.form.label.lower" path="lowerRange"/>
	
	
	
	
	<acme:form-textbox code="consumer.offers.form.label.ticker" path="ticker"/>
	
	<jstl:if test="${command == 'create'}">
	<acme:message code="consumer.offers.form.message.explanation"/>
	<acme:form-checkbox code="consumer.offers.form.checkbox.agree" path="accept"/>
	</jstl:if>
	
	<acme:form-submit test ="${command == 'create'}" code="consumer.offers.form.button.create" action="/consumer/offers/create"/>
	<acme:form-return code="consumer.offers.form.label.button.return"/>
</acme:form> -->