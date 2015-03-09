<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<header id="header" class="header cf">
   this is header
</header>
<div class="msgBox appMsg" style="display:${not empty exception or not empty successMessage ? 'block' : 'none'};">
    <div id="msgBox" class="${not empty exception ? 'faild' : 'success'}"><c:if test="${not empty exception}">${exception.message}</c:if><c:if test="${not empty successMessage}">${successMessage}</c:if></div>
</div>