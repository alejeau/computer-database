<%@ tag body-content="empty"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<option value="-1"><spring:message code="add-computer.no-company" /></option>
<c:forEach items="${ companyList }" var="companyDTO">
    <option value="<c:out value="${ companyDTO.id }" />"
            <c:out value="${ (companyDTO.name == computerDTO.companyName) ? 'selected' : '' }" />>
        <c:out value="${ companyDTO.name }" />
    </option>
</c:forEach>