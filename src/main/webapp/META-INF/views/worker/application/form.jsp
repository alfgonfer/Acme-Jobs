<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="worker.application.form.label.status" path="status"/>
	<acme:form-textbox code="worker.application.form.label.skills" path="skills"/>
	<acme:form-textbox code="worker.application.form.label.qualifications" path="qualifications"/>
	<acme:form-moment code="worker.application.form.label.moment" path="moment"/>
	<acme:form-textarea code="worker.application.form.label.reference" path="reference"/>

	<acme:form-return code="worker.application.form.label.button.return"/>
</acme:form>