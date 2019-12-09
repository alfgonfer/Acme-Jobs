<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:form>
	<acme:form-url code="sponsor.comercialbanner.form.label.urlPicture" path="urlPicture"/>
	<acme:form-textarea code="sponsor.comercialbanner.form.label.slogan" path="slogan"/>
	<acme:form-url code="sponsor.comercialbanner.form.label.urlTarget" path="urlTarget"/>
	<acme:form-textarea code="sponsor.comercialbanner.form.label.creditCard" path="creditCard"/>
	
	<acme:form-return code="sponsor.comercialbanner.form.label.button.return"/>
</acme:form>