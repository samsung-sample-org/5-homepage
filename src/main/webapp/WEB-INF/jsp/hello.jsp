<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP 검증</title>
</head>
<body>
    <h1>JSP 동작 확인</h1>
    <p>메시지: ${message}</p>
    <p>현재 시간: <c:out value="${now}"/></p>
</body>
</html>
