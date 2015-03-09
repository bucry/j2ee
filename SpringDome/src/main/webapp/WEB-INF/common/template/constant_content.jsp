<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!--[if lt IE 9]><nst:js src="html5.js"/><![endif]-->
<script>
    var rootPath = '${pageContext.request.contextPath}',
		staticPath = '<nst:static/>',
		uploadPath = '<nst:fstatic/>';

    function createUrl(path) {
        return  encodeURI('${pageContext.request.contextPath}' + path);
    }
	function uploadUrl(path){
		return encodeURI('<nst:fstatic/>' + path);
	}
</script>
<%--其他 js 文件请在 transaction-template.jsp 文件中引入--%>
