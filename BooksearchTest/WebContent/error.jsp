<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--セッションは利用しない --%>
<%@ page session="false" %>
    
<!DOCTYPE html>
<html>
<title>書籍検索のサンプル</title>
</head>
<body>
    <h3>例外発生</h3>
    <hr>
         例外内容: ${requestScope.error}<br><br>
    <a href="/booksearch/Control">書籍検索画面へ戻る</a>
    <%@ include file="copyright.jsp" %>
</body>
</html>
