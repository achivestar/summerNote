<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
	<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
</head>
<body>
<form id="form" enctype="multipart/form-data">
	<div class="form-group">
	    <label for="subject">제목</label>
	    <input type="text" class="form-control" id="subject" name="subject">
	</div>
  <textarea id="summernote" name="contents"></textarea>
  <input type="hidden" name="thumburl" id="thumburl" />
  <input type="button" class="btn btn-primary" id="save" value="글 등록">
  <input type="button" class="btn btn-secondary" id="list" value="글 목록">
</form>
<script>
$(document).ready(function() {
	// 툴바생략
	var setting = {
            height : 900,
            minHeight : null,
            maxHeight : null,
            focus : true,
            lang : 'ko-KR',
            //콜백 함수
            callbacks : { 
            	onImageUpload : function(files, editor, welEditable) {
            // 파일 업로드(다중업로드를 위해 반복문 사용)
            for (var i = files.length - 1; i >= 0; i--) {
            uploadSummernoteImageFile(files[i],
            this);
            		}
            	}
            }
         };
        $('#summernote').summernote(setting);
        });
        
$("#list").click(function(){
	location.href="./list";
});
        
$("#save").click(function(){
	
	var subject =  document.getElementById("subject");
	
	
	if(subject.value.length==0){
		 alert("제목을 입력해 주세요.");
		 subject.focus();
	     return false;
	}else{
		 var form = new FormData(document.getElementById('form'));
		 //alert(form);
			$.ajax({
			    type: 'POST',
				url: "${pageContext.request.contextPath}/summerRegist", 
				data: form,
				dataType: 'html',
				processData: false, 
				contentType: false, 		
				enctype: 'multipart/form-data',
				success: function(data){
					alert("등록되었습니다..");
					location.href="${pageContext.request.contextPath}/";
				}
				,beforeSend : function(){
					$(".spinner").removeClass("displayLoding");
				},
				complete:function(){
					$(".spinner").addClass("displayLoding");	
				},error : function(){
					alert("등록 실패!");
					location.href="${pageContext.request.contextPath}/";
				}
			}) ;
			//$("#msform").submit();
		}
		

	}) //save btn end
        
        function uploadSummernoteImageFile(file, el) {
			data = new FormData();
			data.append("file", file);
			$.ajax({
				data : data,
				type : "POST",
				url : "uploadSummernoteImageFile",
				contentType : false,
				enctype : 'multipart/form-data',
				processData : false,
				success : function(data) {
					$(el).summernote('editor.insertImage', data.url);
					$("#thumburl").val(data.url);
				}
			});
			
		}


</script>
</body>
</html>
