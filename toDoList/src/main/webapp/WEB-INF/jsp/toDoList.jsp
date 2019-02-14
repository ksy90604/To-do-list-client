<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/ui.css" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	$(function() {
	
		//추가 버튼 클릭
		$("#addBtn").click(function(e) {
			if ($("#title").val() == "") {
				alert("빈 칸을 입력해주세요.");
				return false;
			} else {
				
				// 참조 여부 확인
				var ref = confirm('참조할 할 일이 있습니까?','Title', function () {
				      $('.btn-no').text("네");
				      $('.btn-yes').text("아니오. 그냥 추가하겠습니다.");
				});
				if (ref) {
					$("#layerPopup").show();
				} else {
					addList();
				}
			}

		});
	});


	// 할 일 추가
	function addList() {

		var title = $("#title").val();
		var parentId = $("#parentId").val();

		$.ajax({
			url : "/client/add",
			type : 'post',
			data : {
				title : title,
				parentId : parentId
			},
			success : function(data, status, xhr) {
				location.reload();
			},
			error : function(data) {
				location.reload();
			}
		});
	}

	// 팝업
	$(document).ready(function() {
		$("#layerPopup").hide();
		$("#contents > a").click(function() {
			$("#contents > a").blur();
			$("#layerPopup").show();
			$("#layerPopup a").focus();
			return false;
		});
		$("#layerPopup a").keydown(function(e) {
			if (e.shiftKey && e.keyCode == 9) { // Shift + Tab 키를 의미합니다.
				$("#contents > a").focus();
				$("#layerPopup").hide();
				return false;
			}
		});

		$("#layerPopup .popClose").click(function() {
			$("#contents > a").focus();
			$("#parentId").val("");
			$("#layerPopup").hide();
		});

		$('#parentSaveBtn').click(function() {
			// getter
			var radioVal = $('input[name="selParent"]:checked').val();
			$("#parentId").val(radioVal);
			addList();
		});
	});
</script>
</head>
<body>
	<div id="wrap" class="wrap">

		<!-- container -->
		<div id="container">
			<div class="listTitle">TO-DO LIST</div>
			<div class="inputArea">
				<form id="addFrm" action="" method="post">
					<input type="text" id="title" name="title"></input> 
					<input type="hidden" id="parentId" name="parentId"></input> 
					<a href="javscript:void(0);" id="addBtn" class="btn">추가</a>
				</form>
			</div>
			<!-- table -->
			<table class="tbltype">
				<tbody id="listArea">
					<tr>
						<th>id</th>
						<th>할 일</th>
						<th>작성 일시</th>
						<th>최종 수정 일시</th>
						<th>완료 처리</th>
					</tr>
					<c:if test="${!empty toDoList }">
						<c:forEach var="todo" items="${toDoList}" varStatus="no">
							<tr>
								<td>${todo.id}</td>
								<td>
									<a href="/client/toDoView/${todo.id}" class="aStyle">
										${todo.title}
										<c:if test="${todo.parentId ne null}">
											@ ${todo.parentId}
										</c:if>
									</a>
								</td>
								<td>${todo.regDate}</td>
								<td>${todo.updDate}</td>
								<td>${todo.finYn}</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty toDoList }">
						<tr>
							<td colspan="4"><div class="td">리스트가 없습니다.</div></td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<!-- //table -->
		</div>
		<!-- //container -->

		<!-- 팝업 -->
		<div id="contents">
			<div id="layerPopup">
				<span class="rightArea"><a href="javscript:void(0);" id="popClose" class="popClose">X&nbsp;&nbsp;&nbsp;</a></span>
				<div class="listTitle"><br/>참조할 할 일 선택<br/></div>
				<form id="parentFrm" action="" method="post">
					<!-- table -->
					<table class="tbltype">
						<tbody id="listArea">
							<tr>
								<th>선택</th>
								<th>id</th>
								<th>할 일</th>
								<th>작성일시</th>
								<th>최종수정일시</th>
								<th>완료처리</th>
							</tr>
							<c:if test="${!empty toDoList }">
								<c:forEach var="todo" items="${toDoList}" varStatus="no">
									<tr>
										<td><input type="radio" id="selParent" name="selParent" value="${todo.id}"/></td>
										<td>${todo.id}</td>
										<td>
												${todo.title}
												<c:if  test="${todo.parentId ne NULL or todo.parentId ne ''}">
													@ ${todo.parentId}
												</c:if>
										</td>
										<td>${todo.regDate}</td>
										<td>${todo.updDate}</td>
										<td>${todo.finYn}</td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty toDoList }">
								<tr>
									<td colspan="6"><div class="td">리스트가 없습니다.</div></td>
								</tr>
							</c:if>
						</tbody>
					</table>
					<!-- //table -->
				</form>
				<div class="btnArea">
					<a href="javscript:void(0);" id="parentSaveBtn" class="btn popClose">저장</a>
					<a href="javscript:void(0);" id="popClose" class="btn popClose">취소</a>
				</div>
				<br/><br/>
			</div>
		</div>
	</div>
</body>
</html>
