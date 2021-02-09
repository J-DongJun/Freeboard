<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<title>Insert title here</title>
</head>
<body>

	<div>
		<h1>자료실</h1>
	</div>
	<div style="width:650px;" align="right">
		<a href="./ref.ino">리스트로</a>
	</div>
	<hr style="width: 600px">
	
	<form action="./refBoardUpdatePro.ino" method="POST" enctype="multipart/form-data">
		<input type="hidden" id="refnum" name="refnum" value="${refBoardDto.refnum }">
		
		<div style="width: 150px; float: left;">이름 :</div>
		<div style="width: 500px; float: left;" align="left"><input type="text" name="refname" value="${refBoardDto.refname }" readonly/></div>
		
		<div style="width: 150px; float: left;">제목 :</div>
		<div style="width: 500px; float: left;" align="left">
			<input type="text" name="reftitle"  value="${refBoardDto.reftitle }"/>
		</div> 
	
		<div style="width: 150px; float: left;">작성날자 : </div>
		<div style="width: 500px; float: left;" align="left">
			<input type="text" name="refregdate"  value="${refBoardDto.refregdate }" readonly/>
		</div>
	
		<div style="width: 150px; float: left;">내용 :</div>
		<div style="width: 500px; float: left;" align="left">
			<textarea name="refcontent" rows="25" cols="65">${refBoardDto.refcontent }</textarea>
		</div>
		<div align="right">
		<input type="hidden" name="filenum" value="${fileDto.filenum }">
		<c:choose>
			<c:when test="${flag == '1'}">
				<label>파일추가 : </label>
				<input multiple="multiple" type="file" name="file1">
			</c:when>
			
			<c:otherwise>
				<div id="filenum" style="width: 280px; float: left;">
					<label>파일 다운로드 : ${fileDto.org_file_name}</label>
				</div>
			</c:otherwise>
		</c:choose>
		
		<div style="width: 60px; float: left;">
			<input type="button" value="삭제" id="delete" onclick="button_confirm2()">
		</div>
		<input type="submit" value="수정">
		<input type="button" value="취소" onclick="button_confirm();">
		&nbsp;&nbsp;&nbsp;
		</div>
	
	</form>
	
</body>

<!--  취소버튼 확연여부 -->
<script type="text/javascript"> 

// 	console.log("${refBoardDto.reftitle }");
// 	console.log("${refBoardDto.refregdate }");
	
	console.log("${fileDto.filenum  }");
	console.log("${refBoardDto.refnum }");

	function button_confirm(){
		if(confirm("취소하시겠습니까?") == true){
			location.href="./ref.ino";
		}else{
			return;
		}
	}
	
	
 	 function button_confirm2(){
		if(confirm("삭제하시겠습니까?") == true){
			location.href="./fileDelete.ino?filenum=${fileDto.filenum }&refnum=${refBoardDto.refnum }";
		}else{
			return;
		}
	}  
</script>
</html>

