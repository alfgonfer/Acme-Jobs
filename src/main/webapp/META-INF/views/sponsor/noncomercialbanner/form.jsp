<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:form>
	<acme:form-url code="sponsor.noncomercialbanner.form.label.urlPicture" path="urlPicture"/>
	<acme:form-textarea code="sponsor.noncomercialbanner.form.label.slogan" path="slogan"/>
	<acme:form-url code="sponsor.noncomercialbanner.form.label.urlTarget" path="urlTarget"/>
	<acme:form-url code="sponsor.noncomercialbanner.form.label.jingle" path="jingle"/>

	<acme:form-return code="sponsor.noncomercialbanner.form.label.button.return"/>
</acme:form>