<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%-- このページではセッションは使用しない --%>
<%@ page session="false" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>書籍検索のサンプル</title>
<script type="text/javascript"> 
<!-- 
function check(){
    if(!document.form1.isbn.value && !document.form1.title.value){
        window.alert('どちらにも入力されていません。');
        return false;
    }else{
        return true;
    }
}
// -->
</script>

</head>
<body>
    <h3>書籍検索</h3>
    <b>ISBN</b>あるいは<b>タイトル</b>で検索します。両方入力した場合は、<b>ISBN</b>で検索します。
    <hr>
    <form method="POST" action="/booksearch/Control" name="form1" onSubmit="return check()">
    ISBN: <input type="text" name="isbn" size=30><br><br>
        タイトル: <input type="text" name="title" size=30><br><br>
        <input type="submit" value="検索" name="button_name">
        <input type="reset" value="リセット">
    </form>
    <%@ include file="copyright.jsp" %>
</body>
</html>
