<jsp:directive.tag body-content="empty"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="target" required="true" %>
<%@attribute name="search" required="false" %>
<%@attribute name="pageNb" required="false" %>
<%@attribute name="displayBy" required="false" %>
<%@attribute name="field" required="false" %>
<%@attribute name="ascending" required="false" %>
<%@attribute name="changeAscending" required="false" %>
<%@attribute name="computerId" required="false" %>
<%@attribute name="lang" required="false" %>

<c:set var="pathDash" value="/computer-database/access"/>
<c:set var="pathAdd" value="/computer-database/access/computer/add"/>
<c:set var="pathEdit" value="/computer-database/access/computer/edit"/>
<c:set var="pathSearch" value="/computer-database/access/computer/search"/>
<c:set var="pathReset " value="/computer-database/access?reset=true"/>

<c:set var="emptyText" value=""/>
<c:set var="tmpPath" value=""/>


<c:set var="whichPageNb" value="${ not empty targetPageNumber ? targetPageNumber : pageDTO.currentPageNumber }"/>
<c:set var="tmpPageNb" value="${ emptyText.concat('?pageNb=').concat(whichPageNb) }"/>

<c:set var="whichDisplayBy" value="${ not empty targetDisplayBy ? targetDisplayBy : pageDTO.objectsPerPage }"/>
<c:set var="tmpDisplayBy" value="${ emptyText.concat('&displayBy=').concat(whichDisplayBy) }"/>

<%-- --%>
<c:choose>
    <c:when test="${not empty target}">
        <c:choose>
            <c:when
                    test="${ target.equals('dashboard') }">
                <c:set var="tmpPath"
                       value="${ tmpPath.concat(pathDash) }"/>
            </c:when>
            <c:when test="${ target.equals('add') }">
                <c:set var="tmpPath"
                       value="${ tmpPath.concat(pathAdd) }"/>
            </c:when>
            <c:when test="${ target.equals('edit') }">
                <c:set var="tmpPath"
                       value="${ tmpPath.concat(pathEdit) }"/>
            </c:when>
            <c:when test="${ target.equals('search') }">
                <c:set var="tmpPath"
                       value="${ tmpPath.concat(pathSearch) }"/>
            </c:when>
            <c:when test="${ target.equals('reset') }">
                <c:set var="tmpPath"
                       value="${ tmpPath.concat(pathDash) }"/>
                <c:set var="tmpPageNb"
                       value="${ emptyText.concat('?pageNb=0') }"/>
                <c:set var="tmpDisplayBy"
                       value="${ emptyText.concat('&displayBy=10') }"/>
            </c:when>
            <c:when test="${ target.equals('self') }">
                <c:set var="tmpPath" value="${ tmpPath.concat(currentPath) }"/>
            </c:when>
            <c:otherwise>
                <c:out value="${ tmpPath.concat(pathDash) }"/>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:out value="${ tmpPath.concat(pathDash) }"/>
    </c:otherwise>
</c:choose>

<c:if test="${ not empty pageNb and pageNb.matches('[0-9]+') }">
    <c:set var="tmpPageNb"
           value="${ emptyText.concat('?pageNb=').concat(pageNb) }"/>
</c:if>

<c:if test="${ not empty displayBy and displayBy.matches('[0-9]+') }">
    <c:set var="tmpDisplayBy" value="${ emptyText.concat('&displayBy=').concat(displayBy) }"/>
</c:if>
<c:if test="${ not empty search }">
    <c:set var="tmpSearch" value="${ emptyText.concat('&search=').concat(search) }"/>
</c:if>
<c:if test="${ not empty field }">
    <c:set var="tmpField" value="${ emptyText.concat('&field=').concat(field) }"/>
    <c:if test="${ field.matches(orderBy) }">
        <c:if test="${not empty changeAscending}">
            <c:set var="ascending" value="${ !ascending }"/>
        </c:if>
    </c:if>
    <c:if test="${ !field.matches(orderBy) }">
        <c:set var="ascending" value="${true}"/>
    </c:if>
</c:if>
<c:if test="${ not empty ascending }">
    <c:set var="tmpAsc" value="${ emptyText.concat('&ascending=').concat(ascending) }"/>
</c:if>
<c:if test="${ not empty lang }">
    <c:set var="tmpAsc" value="${ emptyText.concat('&lang=').concat(lang) }"/>
</c:if>

<c:set var="tmpPath" value="${ tmpPath.concat(tmpPageNb) }"/>
<c:set var="tmpPath" value="${ tmpPath.concat(tmpDisplayBy) }"/>
<c:set var="tmpPath" value="${ tmpPath.concat(tmpSearch) }"/>
<c:set var="tmpPath" value="${ tmpPath.concat(tmpField) }"/>
<c:set var="tmpPath" value="${ tmpPath.concat(tmpAsc) }"/>

<c:out value="${ tmpPath }" escapeXml="false"/>