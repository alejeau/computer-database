<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<option value="-1">No company</option>
<c:forEach items="${ companyList }" var="companyDTO">
    <option value="<c:out value="${ companyDTO.id }" />"
            <c:out value="${ (selectedId == companyDTO.id) ? 'selected' : '' }" />>
        <c:out value="${ companyDTO.name }" />
    </option>
</c:forEach>