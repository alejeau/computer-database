<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cst" tagdir="/WEB-INF/tags" %>


<div class="btn-group btn-group-sm pull-right" role="group">
    <c:forEach items="${ limitValues }" var="limit">
        <a href='<cst:links target="self" displayBy="${limit}" />'>
            <button type="button" class="btn btn-default">${limit}</button>
        </a>
    </c:forEach>
</div>