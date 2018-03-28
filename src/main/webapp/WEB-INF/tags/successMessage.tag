<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cst" tagdir="/WEB-INF/tags" %>

<c:if test="${displaySuccessMessage == true}">
    <div class="alert alert-success">
        The modifications have been successfully recorded.
    </div>
</c:if>

