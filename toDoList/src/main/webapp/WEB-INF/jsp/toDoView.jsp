<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/ui.css" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">

function reloadPage(){
	location.reload();
}

function goList(){
	location.href="http://localhost:8090/client/toDoList";
}
$(document).ready(function() {
	
	// 할 일 추가
	$('#updSaveBtn').click(function() {

		var frmData = $("updFrm").serialize();
		
		var id = $("#id").val();
		var title = $("#title").val();
		var parentId = $("#parentId").val();
		var finYn = $('input[name="finYn"]:checked').val();
		
		$.ajax({
			url : "/client/update",
			type : 'post',
			data : {
				  id : id
				, title : title
				, parentId : parentId
				, finYn : finYn
			},
			success : function(data, status, xhr) {
				goList();
			},
			error : function(data) {
				goList();
			}
		});
	});
});
</script>
</head>
<body>
	<div id="wrap" class="wrap">

		<!-- container -->
		<div id="container">
			<div class="listTitle">수정</div>
			
			<form id="updFrm" action="" method="post">
				<input type="hidden" id="parentId" name="parentId" value="${toDo.parentId}"/>
				<!-- table -->
				<table class="tbltype">
					<tbody id="listArea">
						<tr>
							<th>id</th>
							<td>
							<input type="hidden" id="id" name="id" value="${toDo.id}"/>
							${toDo.id}
							</td>
						</tr>
						<tr>
							<th>할 일</th>
							<td><input type="text" id="title" name="title" value="${toDo.title}"/></td>
						</tr>
						<tr>
							<th>작성일시</th>
							<td>${toDo.regDate}</td>
						</tr>
						<tr>
							<th>최종수정일시</th>
							<td>${toDo.updDate}</td>
						</tr>
						<tr>
							<th>완료처리</th>
							<td>
								
								Y<input type="radio" id="finY" name="finYn" value="Y" ${toDo.finYn eq 'Y'? 'checked' : '' } ${0 < ChildCnt? 'disabled' : '' }/>
								N<input type="radio" id="finN" name="finYn" value="N" ${toDo.finYn eq 'N'? 'checked' : '' }/>
								<c:if test="${0 < ChildCnt}">
									<br/>참조된 할 일이 ${ChildCnt} 개 있어 완료처리 할 수 없습니다.
								</c:if>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- //table -->
				<div class="btnArea">
					<a href="/client/toDoList" id="updSaveBtn" class="btn">저장</a>
					<a href="/client/toDoList" id="goToDoList" class="btn">취소</a>
				</div>
			</form>
		</div>
		<!-- //container -->
	</div>
</body>
</html>
