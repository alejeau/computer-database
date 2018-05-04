<%@ tag body-content="empty" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cst" tagdir="/WEB-INF/tags" %>

<a href='<cst:links target="logout"/>'><spring:message code="login.logout"/> </a>