<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cst" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title><spring:message code="application.title" /></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="<spring:url value="/resources"/>/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="<spring:url value="/resources"/>/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="<spring:url value="/resources"/>/css/errors.css" rel="stylesheet" media="screen">
    <link href="<spring:url value="/resources"/>/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="<cst:links target="dashboard"/>"> <spring:message code="application.header" /> </a>
        <span style="float: right; color: white;"><cst:language /></span>
    </div>
</header>

<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1><spring:message code="edit-computer.title" /></h1>
                <cst:successMessage />
                <form action="<cst:links target="edit"/>" method="POST">
                    <input type="hidden" name="id" value="<c:out value='${ computerDTO.id }' />"/>
                    <fieldset>
                        <div class="form-group">
                            <label for="name"><spring:message code="dashboard.computer-name" /></label> <span class="errmsg"><cst:errors error="name" /></span>
                            <input type="text" class="form-control" id="name" name="name" placeholder="<spring:message code="dashboard.computer-name" />"
                                   value="<c:out value='${ computerDTO.name }' />"
                                   pattern="^[\wÀ-ÿ\-'\+\*\.]+[\wÀ-ÿ\-'\+\*\. ]+$"
                                   data-validation-error-msg="<spring:message code="add-computer.error-validation-msg" />"
                                   required="required">
                        </div>
                        <div class="form-group">
                            <label for="introduced"><spring:message code="dashboard.introduced-date" /></label> <span class="errmsg"><cst:errors error="introduced" /> <cst:errors error="dates" /></span>
                            <input type="date" class="form-control" id="introduced" name="introduced" placeholder="<spring:message code="dashboard.introduced-date" />"
                                   value="<c:out value='${ computerDTO.introduced }' />"
                                   data-validation="date"
                                   data-validation-format="yyyy-mm-dd"
                                   data-validation-optional="true">
                        </div>
                        <div class="form-group">
                            <label for="discontinued"><spring:message code="dashboard.discontinued-date" /></label> <span class="errmsg"><cst:errors error="discontinued" /> <cst:errors error="dates" /></span>
                            <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="<spring:message code="dashboard.discontinued-date" />"
                                   value="<c:out value='${ computerDTO.discontinued }' />"
                                   data-validation="date"
                                   data-validation-format="yyyy-mm-dd"
                                   data-validation-optional="true">
                        </div>
                        <div class="form-group">
                            <label for="companyId"><spring:message code="dashboard.company" /></label>
                            <select class="form-control" id="companyId" name="companyId">
                                <cst:companyList/>
                            </select>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" value="<spring:message code="edit-computer.edit" />" class="btn btn-primary">
                        <spring:message code="edit-computer.or" />
                        <a href="<cst:links target="dashboard" pageNb="${currentPageNumber}" displayBy="${currentDisplayBy}"/>" class="btn btn-default"><spring:message code="edit-computer.cancel" /></a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>

<script src="<spring:url value="/resources"/>/js/jquery.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
<script>
    $.validate({
        lang: 'en',
        modules: 'html5'
    });
</script>
</body>
</html>