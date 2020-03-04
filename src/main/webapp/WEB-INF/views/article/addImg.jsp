<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form id="saveForm">
	<input type="hidden" id="id" name="id" value="${article.id }">
	<div class="form-group row">
		<label for="inputPassword3" class="col-sm-2 col-form-label">文章图片</label>
		<div class="col-sm-5">
			<jsp:include page="../common/file.jsp">
				<jsp:param name="fieldName" value="picture"/>
				<jsp:param name="fieldValue" value="${article.picture }"/>
			</jsp:include>
		</div>
	</div>
	

	
	<button type="button" class="btn btn-primary mb-2" onclick="save(0);">提交</button>
	<div class="alert alert-success" role="alert" style="display: none"></div>
</form>

<script type="text/javascript">
	
	
	function save(status){
		var title = $("#title").val();
		var picture = $("#picture").val();
		var channel_id = $("#channel_id").val();
		var category_id = $("#category_id").val();

		if(picture==null || picture==""){
			$(".alert").html("请选择图片");
			$(".alert").show();
			return;
		}
		
		$(".alert").hide();
		/** 提交数据 **/
		var formData = new FormData($("#saveForm")[0]);
		$.ajax({
			url:"/article/saveImg",
			type:"post",
			data:formData,
			processData:false,
			contentType:false,
			success:function(res){
				console.log(res);
				location.href="${pageContext.request.contextPath }/";
			}
		});
	}
</script>