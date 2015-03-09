<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content errorPage" id="content">
    <h1><i class="icon-warning"></i> ${exception.message}</h1>
    <div class="errorNote">
        <h2>StackTrace:</h2>
        <ul>
            <li>${exception.stackTrace[0]}</li>
            <li>${exception.stackTrace[1]}</li>
            <li>${exception.stackTrace[2]}</li>
            <li>${exception.stackTrace[3]}</li>
            <li>${exception.stackTrace[4]}</li>
            <li>${exception.stackTrace[5]}</li>
            <li>${exception.stackTrace[6]}</li>
            <li>${exception.stackTrace[7]}</li>
            <li>${exception.stackTrace[8]}</li>
            <li>${exception.stackTrace[9]}</li>
            <li>${exception.stackTrace[10]}</li>
            <li>${exception.stackTrace[11]}</li>
            <li>${exception.stackTrace[12]}</li>
            <li>${exception.stackTrace[13]}</li>
            <li>${exception.stackTrace[14]}</li>
            <li>${exception.stackTrace[15]}</li>
            <li>${exception.stackTrace[16]}</li>
            <li>${exception.stackTrace[17]}</li>
            <li>${exception.stackTrace[18]}</li>
            <li>${exception.stackTrace[19]}</li>
            <li>${exception.stackTrace[20]}</li>
        </ul>
    </div>
</div>