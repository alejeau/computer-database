<%@ tag body-content="empty" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="error" required="true" %>

<c:choose>
    <c:when test="${not empty error}">
        <c:choose>
            <c:when test="${ error.equals('name') }">
                <c:out value="${ errorMap['COMPUTER_NAME'] }" />
            </c:when>
            <c:when test="${ error.equals('introduced') }">
                <c:out value="${ errorMap['COMPUTER_INTRODUCED'] }" />
            </c:when>
            <c:when test="${ error.equals('discontinued') }">
                <c:out value="${ errorMap['COMPUTER_DISCONTINUED'] }" />
            </c:when>
            <c:when test="${ error.equals('dates') }">
                <c:out value="${ errorMap['COMPUTER_DATES'] }" />
            </c:when>
            <c:otherwise>
                <c:out value="${ '' }" />
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:out value="${ '' }" />
    </c:otherwise>
</c:choose>