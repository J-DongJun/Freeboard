<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
	
	<form action="./refBoardInsertPro.ino" method="POST" enctype="multipart/form-data">
		<div style="width: 150px; float: left;">이름 :</div>
		<div style="width: 500px; float: left;" align="left"><input type="text" name="refname" /></div>
		
		<div style="width: 150px; float: left;">제목 :</div>
		<div style="width: 500px; float: left;" align="left"><input type="text" name="reftitle" /></div>
	
		<div style="width: 150px; float: left;">내용 :</div>
		<div style="width: 500px; float: left;" align="left"><textarea name="refcontent" rows="25" cols="65"  ></textarea></div>
		
		
		<div align="right">
		<label>파일추가 : </label>
		<input multiple="multiple" type="file" name="file1">
		<input type="submit" value="글쓰기" >
		<input type="reset" value="다시쓰기">
		<input type="button" value="취소" onclick="button_confirm();">
		&nbsp;&nbsp;&nbsp;
		</div>
		
		
	</form>
	
</body>

<!--  취소버튼 확연여부 -->
<script type="text/javascript">
	function button_confirm(){
		if(confirm("취소하시겠습니까?") == true){
			location.href="./ref.ino";
		}else{
			return;
		}
	}
	
</script>
</html>