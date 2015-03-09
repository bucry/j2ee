<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="content errorPage" id="content">
    <h1><i class="icon-warning"></i> Forbidden</h1>
    <div class="errorNote">${authorizationException.message}</div>
</div>