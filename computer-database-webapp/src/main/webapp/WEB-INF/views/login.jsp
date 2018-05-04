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
    <link href="<spring:url value="/resources"/>/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="<cst:links target="dashboard"/>"> <spring:message code="application.header" /> </a>
        <span style="float: right; color: white;"><cst:language /><cst:logout/></span>
    </div>
</header>

<section id="main">
    <div class="container">
        <c:if test="${logout != null && not empty logout}">
            <div class="alert alert-success">
                <spring:message code="login.logout" />
            </div>
        </c:if>

        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1>
                    <spring:message code="login.title" text="Login" />
                </h1>
                <form action="${loginUrl}" modelAttribute="user" method="post"
                           class="form-horizontal">
                    <fieldset>
                        <div class="form-group">
                            <label for="username"> <spring:message code="login.username" text="Username" />
                            </label> <input type="text" class="form-control" id="username"
                                            name="username"
                                            placeholder="<spring:message code="login.username" text="" />">
                        </div>
                        <div class="form-group">
                            <label for="password"> <spring:message code="login.password"
                                                                   text="Password" />
                            </label> <input type="password" class="form-control" id="password"
                                            name="password"
                                            placeholder="<spring:message code="login.password" text="" />">
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" name="submit"
                               value="<spring:message code="login.login" text="Login" />"
                               class="btn btn-primary">
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}" />
                </form>
            </div>
        </div>
    </div>
</section>

<script src="<spring:url value="/resources"/>/js/jquery.min.js"></script>
<script src="<spring:url value="/resources"/>/js/bootstrap.min.js"></script>
<script src="<spring:url value="/resources"/>/js/dashboard.js"></script>
</body>
</html>