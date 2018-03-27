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
    <link href="<c:url value="/static"/>/css/index.css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="<cst:links target="dashboard"/>"> Application - Computer Database </a>
    </div>
</header>

<section id="main">
    <div>
        <h1><a href="<cst:links target="dashboard"/>">TO THE MAIN PAGE!</a></h1>
    </div>
</section>

<script src="<c:url value="/static"/>/js/jquery.min.js"></script>
</body>
</html>