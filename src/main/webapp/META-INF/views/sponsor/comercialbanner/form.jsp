<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:form>
	<acme:form-url code="sponsor.comercialbanner.form.label.urlPicture" path="urlPicture"/>
	<acme:form-textarea code="sponsor.comercialbanner.form.label.slogan" path="slogan"/>
	<acme:form-url code="sponsor.comercialbanner.form.label.urlTarget" path="urlTarget"/>
	<jstl:if test="${command == 'show' }">
	<acme:form-textarea code="sponsor.comercialbanner.form.label.creditCard" path="creditCard" readonly="true"/>
	</jstl:if>
	
	<jstl:if test="${finalMode = false }">
	<acme:form-select code="sponsor.comercialbanner.form.label.finalmode" path="finalMode">
	<acme:form-option code="sponsor.comercialbanner.form.label.finalmode.false" value="false"/>
	<acme:form-option code="sponsor.comercialbanner.form.label.finalmode.true" value="true"/>
	</acme:form-select>
	</jstl:if>
	
	<jstl:if test="${finalMode = true }">
	<acme:form-select code="sponsor.comercialbanner.form.label.finalmode" path="finalMode">
	<acme:form-option code="sponsor.comercialbanner.form.label.finalmode.true" value="true"/>
	<acme:form-option code="sponsor.comercialbanner.form.label.finalmode.false" value="false"/>
	</acme:form-select>
	</jstl:if>

	<acme:form-submit test ="${command == 'create'}" code="provider.request.form.button.create" action="/sponsor/comercialbanner/create"/>
	<acme:form-submit test ="${command == 'update'}" code="provider.request.form.button.create" action="/sponsor/comercialbanner/update"/>
	<acme:form-submit test ="${command == 'show'}" code="provider.request.form.button.create" action="/sponsor/comercialbanner/update"/>
	<acme:form-return code="sponsor.comercialbanner.form.label.button.return"/>
</acme:form>