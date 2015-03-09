<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<!--[if lt IE 7 ]><html lang="en" class="IE IE8- IE7- IE6"><![endif]-->
<!--[if IE 7 ]><html lang="en" class="IE IE8- IE7- IE7"><![endif]-->
<!--[if IE 8 ]><html lang="en" class="IE IE8- IE8"><![endif]-->
<!--[if (gt IE 8)|!(IE)]><!--><html lang="en"><!--<![endif]-->
<head>
    <meta charset="UTF-8">
    <title><tiles:getAsString name="title"/></title>
    <tiles:insertAttribute name="constant-content"/>
</head>
<!-- <body class="<tiles:getAsString name="screen-id"/> miniMenu_<nst:cookie key="mini_menu"/>"> -->
 <body>
	<div id="wrap" class="cf">
	<!--[if lt IE 9]>
		<div class="oldIENotices">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience. Thanks!</div>
	<![endif]-->
		<tiles:insertAttribute name="constant-header"/>
		<tiles:insertAttribute name="constant-menu"/>
		<div id="main" class="main">
			<tiles:insertAttribute name="main-content1"/>
			<tiles:insertAttribute name="main-content2"/>
			<tiles:insertAttribute name="main-content3"/>
			<tiles:insertAttribute name="main-content4"/>
			<tiles:insertAttribute name="main-content5"/>
			<tiles:insertAttribute name="main-content6"/>
			<tiles:insertAttribute name="main-content7"/>
			<div class="cl"></div>
		</div>	<!-- #main END -->
		<div class="push"></div>
	</div>
<tiles:insertAttribute name="constant-footer"/>
</body>
</html>