<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="cst" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="<c:url value="/static"/>/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="<c:url value="/static"/>/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="<c:url value="/static"/>/css/errors.css" rel="stylesheet" media="screen">
    <link href="<c:url value="/static"/>/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="<cst:links target="dashboard"/>"> Application - Computer Database </a>
    </div>
</header>

<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1>Add Computer</h1>
                <form action="<cst:links target="add"/>" method="POST">
                    <fieldset>
                        <div class="form-group">
                            <label for="computerName">Computer name</label> <span class="errmsg"><cst:errors error="name" /></span>
                            <input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name"
                                   pattern="^[\wÀ-ÿ]+[\wÀ-ÿ_\-'\+\* ]+$"
                                   data-validation-error-msg="The computer name can not be empty, nor start nor end with a space, but may contain the special following chars: _-'+*."
                                   required="required">
                        </div>
                        <div class="form-group">
                            <label for="introduced">Introduced date</label> <span class="errmsg"><cst:errors error="introduced" /> <cst:errors error="dates" /></span>
                            <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduction date"
                                   data-validation="date" data-validation-format="yyyy-mm-dd" data-validation-optional="true">
                        </div>
                        <div class="form-group">
                            <label for="discontinued">Discontinued date</label> <span class="errmsg"><cst:errors error="discontinued" /> <cst:errors error="dates" /></span>
                            <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinuation date"
                                   data-validation="date" data-validation-format="yyyy-mm-dd" data-validation-optional="true">
                        </div>
                        <div class="form-group">
                            <label for="companyId">Company</label>
                            <select class="form-control" id="companyId" name="companyId">
                                <cst:companyList/>
                            </select>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" value="Add" class="btn btn-primary">
                        or
                        <a href="<cst:links target="dashboard" pageNb="${currentPageNumber}" displayBy="${currentDisplayBy}"/>" class="btn btn-default">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>

<script src="<c:url value="/static"/>/js/jquery.min.js"></script>
<%--<script src="<c:url value="/static"/>/js/jquery.form-validator.min.js"></script>--%>
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
<script>
    $.validate({
        lang: 'en',
        modules: 'html5'
    });
</script>
</body>
</html>