<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:form>
	<acme:form-url code="administrator.comercialbanner.form.label.urlPicture" path="urlPicture"/>
	<acme:form-textarea code="administrator.comercialbanner.form.label.slogan" path="slogan"/>
	<acme:form-url code="administrator.comercialbanner.form.label.urlTarget" path="urlTarget"/>
	<acme:form-textarea code="administrator.comercialbanner.form.label.creditCard" path="creditCard"/>
	
	<acme:form-submit test ="${command == 'show'}" code="administrator.comercialbanner.form.button.update" action="/administrator/comercialbanner/update"/>
	<acme:form-submit test ="${command == 'show'}" code="administrator.comercialbanner.form.button.delete" action="/administrator/comercialbanner/delete"/>
	<acme:form-submit test ="${command == 'create'}" code="administrator.comercialbanner.form.button.create" action="/administrator/comercialbanner/create"/>
	<acme:form-submit test ="${command == 'update'}" code="administrator.comercialbanner.form.button.update" action="/administrator/comercialbanner/update"/>
	<acme:form-submit test ="${command == 'delete'}" code="administrator.comercialbanner.form.button.delete" action="/administrator/comercialbanner/delete"/>
	
	<acme:form-return code="administrator.comercialbanner.form.label.button.return"/>
</acme:form>