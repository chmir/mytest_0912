<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Phonebook Site</title>
<style>
    #addUser, #searchResult, #detailView {
        border: 1px solid red;
        margin-bottom: 20px;
        padding: 10px;
    }
    #detailView input[type="text"] {
        width: 100%;
    }
</style>
</head>
<body>
<h1>전화번호부 관리 페이지</h1>

<!-- 전화번호 검색 폼 -->
<div id="search">
    <h2>전화번호로 검색</h2>
    <form action="${pageContext.request.contextPath}/search" method="get">
        <label for="hpSearch">Phone Number:</label>
        <input type="text" id="hpSearch" name="hp" required>
        <input type="submit" value="Search">
    </form>
</div>

<!-- 검색 결과 리스트 -->
<div id="searchResult">
    <h2>검색 결과</h2>
    <c:if test="${!empty searchResult}">
        <ul>
            <c:forEach var="item" items="${searchResult}">
                <li>
                    <a href="${pageContext.request.contextPath}/detail?id=${item.id}">${item.name} - ${item.hp}</a>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</div>

<!-- 상세 정보 뷰 -->
<div id="detailView">
    <h2>전화번호부 항목 상세 정보</h2>
    <c:if test="${not empty detail}">
        <form action="${pageContext.request.contextPath}/update" method="post">
            <!-- 숨겨진 id 필드 -->
            <input type="hidden" name="id" value="${detail.id}">
            
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${detail.name}" required><br><br>
            
            <label for="hp">Phone Number:</label>
            <input type="text" id="hp" name="hp" value="${detail.hp}" required><br><br>
            
            <label for="memo">Memo:</label>
            <input type="text" id="memo" name="memo" value="${detail.memo}"><br><br>
            
            <input type="submit" value="Update">
        </form>
        
        <form action="${pageContext.request.contextPath}/delete" method="post" style="margin-top: 10px;">
            <input type="hidden" name="id" value="${detail.id}">
            <input type="submit" value="Delete">
        </form>
    </c:if>
</div>

<!-- 새로운 전화번호부 항목 추가 -->
<div id="addUser">
    <h2>전화번호를 입력해주세요</h2>
    <form action="${pageContext.request.contextPath}/addUser" method="post">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required><br><br>
        
        <label for="hp">Phone Number:</label>
        <input type="text" id="hp" name="hp" required><br><br>
        
        <label for="memo">Memo:</label>
        <input type="text" id="memo" name="memo"><br><br>
        
        <input type="submit" value="Submit">
    </form>
</div>

</body>
</html>
