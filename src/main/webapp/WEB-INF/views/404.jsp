<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cst" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
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
    </div>
</header>

<section id="main">
    <div class="container">
        <div class="alert alert-danger">
            <spring:message code="error404.message" />
            <br/>
            <spring:message code="error404.goto" /> <a href="<cst:links target="dashboard"/>"><spring:message code="error404.main-page" /></a>.
        </div>
    </div>
</section>

<script src="<spring:url value="/resources"/>/js/jquery.min.js"></script>
<script src="<spring:url value="/resources"/>/js/bootstrap.min.js"></script>
<script src="<spring:url value="/resources"/>/js/dashboard.js"></script>

</body>
</html>