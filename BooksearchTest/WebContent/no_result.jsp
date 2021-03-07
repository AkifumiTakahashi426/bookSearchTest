<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--セッションは利用しない --%>
<%@ page session="false" %>

<!DOCTYPE html>
<html>
<title>書籍検索のサンプル</title>
</head>
<body>
    <h3>検索結果</h3>    
    <hr>
         検索キーワード: ${requestScope.key}<BR>
         検索結果数： ${requestScope.count} 件　該当書籍はありません。
    <br><br>
    <A href="/booksearch/Control">書籍検索画面へ戻る</A>
    <%@ include file="copyright.jsp" %>
</body>
</html>
