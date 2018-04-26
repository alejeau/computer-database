<%@ tag body-content="empty" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cst" tagdir="/WEB-INF/tags" %>

${pageContext.response.locale} <a href='<cst:links target="self" lang="en" />'><img src="<spring:url value="/resources/img/thumbs/en.png"/>"></a> | <a href='<cst:links target="self" lang="fr" />'><img src="<spring:url value="/resources/img/thumbs/fr.png"/>"></a>