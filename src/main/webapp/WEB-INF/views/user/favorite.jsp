<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>       
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath }/public/css/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath }/public/js/jquery.min.1.12.4.js"></script>
<script src="${pageContext.request.contextPath }/public/js/bootstrap.min.js"></script>
<link href="${pageContext.request.contextPath }/public/css/index.css"  rel="stylesheet">
<script type="text/javascript" src="/public/js/checkbox.js" ></script>
</head>
<body>
	<form class="form-inline" id="queryForm">
		<input type="hidden" id="pageNum" name="pageNum" value="${pageInfo.pageNum }">
		<button type="button" class="btn btn-primary mb-2" onclick="query();">查询</button>
	</form>

	<div style="padding-top: 10px">
		<h2>我的收藏夹:</h2>
		<hr style="border-color: red">
		<div>
			<c:forEach items="${pageInfo.list }" var="item">
				<div class="media-body text-center">
					<a href="${item.url }" style="font-size: 15px;">${item.text }</a><br>
					时间:${item.created }&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:deleteFavorite(${item.id})"><input type="button" value="删除" class="btn btn-danger"></a>
				</div>
				<hr>
			</c:forEach>
			<div class="col-9">
				<jsp:include page="../common/page.jsp"></jsp:include>
			</div>
		</div>
		
		
		
		<%-- <ul class="list-unstyled">
			<c:forEach items="${info.list }" var="a">
				<li class="media">
					<div class="media-body text-center">
						<h5 class="mt-0 mb-1 "><a href="${a.url }" style="font-size: 15px;">${a.text }</a></h5><br>
						时间:${a.created }&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:deleteFavorite(${a.id})"><input type="button" value="删除" class="btn btn-danger"></a>
					</div>
				</li>
				<hr>
			</c:forEach>
			
		</ul> --%>
		
	</div>
	
</body>
<script type="text/javascript">
	function deleteFavorite(id) {
		if(confirm("你确定要删除id为"+id+"的数据吗？")){
			$.ajax({
				url:'${pageContext.request.contextPath }/article/deleteFavorite',
				type:'post',
				data:{id:id},
				success:function(obj){
					if(obj){
						alert("删除成功");
						reload();
						//location.href="${pageContext.request.contextPath }/user/center";
					}else{
						alert("删除失败");
					}
				}
			});
		}
	}
	
	function query(){
		var params = $("#queryForm").serialize();
		reload(params)
	}
	
	function gotoPage(pageNum){
		$("#pageNum").val(pageNum);
		query();
	}
	
	/* function gotoPage(pageNum) {
		$("#pageNum").val(pageNum);
		reload();
		//location.href="${pageContext.request.contextPath }/article/selects?page="+page;
	} */
</script>
</html>