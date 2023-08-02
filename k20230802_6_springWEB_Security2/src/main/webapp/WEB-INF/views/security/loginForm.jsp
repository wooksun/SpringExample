<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- taglib 추가 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LoginForm</title>
</head>
<body>

<h1>loginForm.jsp</h1>

<!-- action 속성에 j_spring_security_check를 넣어줘야 security 인증 절차를 거칠 수 있다. -->
<c:url var="loginUrl" value="j_spring_security_check"/>
<form action="${loginUrl}" method="post">
	<%-- 
		security를 사용하려면 id와 password는 미리 정해진 약속된 키워드인 j_username과 j_password를 사용해야
		security-context.xml 파일의 <security:authentication-manager>에서 사용할 수 있다.
	--%>
	ID: <input type="text" name="j_username"/><br/>
	PW: <input type="password" name="j_password"/><br/>
	<input type="submit" value="LOGIN"/>
</form>

</body>
</html>