<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div>
		<h1>자유게시판</h1>
	</div>
	<div style="width:650px;" align="right">
		<a href="./freeBoardInsert.ino">글쓰기</a>
	</div>
	<hr style="width: 600px">
	
	<c:forEach items="${freeBoardList }" var="dto">
			<div style="width: 50px; float: left;">${dto.num }</div>	
			<div style="width: 300px; float: left;"><a href="./freeBoardDetail.ino?num=${dto.num }">${dto.title }</a></div>
			<div style="width: 150px; float: left;">${dto.name }</div>
			<div style="width: 150px; float: left;">${dto.regdate }</div> 
		<hr style="width: 600px">
	</c:forEach>
	
	<!-- 검색기능 -->
	<form name="form1" method="get" action="main.ino">
	
		<select name="search_option">
		
			<option value="name"
				<c:if test="${search_option == 'name'}">selected</c:if>>작성자</option>
			<option value="title"
				<c:if test="${search_option == 'title'}">selected</c:if>>제목</option>

		</select>
		
		<input name="keyword" value="${keyword}">
		<input type="submit" value="조회">
		
	</form>


	<!-- 페이징처리 -->
		<div>
            <c:if test="${pagination.curRange ne 1 }">
                <a href="#" onClick="fn_paging(1)">[처음]</a> 
            </c:if>
            <c:if test="${pagination.curPage ne 1}">
                <a href="#" onClick="fn_paging('${pagination.prevPage }')">[이전]</a> 
            </c:if>
            <c:forEach var="pageNum" begin="${pagination.startPage }" end="${pagination.endPage }">
                <c:choose>
                    <c:when test="${pageNum eq  pagination.curPage}">
                        <span style="font-weight: bold;"><a href="#" onClick="fn_paging('${pageNum }')">${pageNum }</a></span> 
                    </c:when>
                    <c:otherwise>
                        <a href="#" onClick="fn_paging('${pageNum }')">${pageNum }</a> 
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${pagination.curPage ne pagination.pageCnt && pagination.pageCnt > 0}">
                <a href="#" onClick="fn_paging('${pagination.nextPage }')">[다음]</a> 
            </c:if>
            <c:if test="${pagination.curRange ne pagination.rangeCnt && pagination.rangeCnt > 0}">
                <a href="#" onClick="fn_paging('${pagination.pageCnt }')">[끝]</a> 
            </c:if>
        </div>

		<div>총 게시글 수 : ${pagination.listCnt } / 총 페이지 수 :
			${pagination.pageCnt } / 현재 페이지 : ${pagination.curPage } / 현재 블럭 :
			${pagination.curRange } / 총 블럭 수 : ${pagination.rangeCnt }
		</div>
</body>
<script type="text/javascript">
function fn_paging(curPage) {
	location.href = "./main.ino?curPage=" + curPage;
	}
	
</script>
</html>