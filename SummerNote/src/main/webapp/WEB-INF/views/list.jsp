<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>       

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="table-responsive">
                  <table class="table">
                    <thead class=" text-primary">
                      <th width="20%" class="text-center">
                        No.
                      </th>
                      <th  width="40%"class="text-center">
                        subject
                      </th>
                       <th  width="15%"class="text-center">
                       	thumb
                      </th>
                      <th  width="15%"class="text-center">
                        regdate
                      </th>
                      <th width="10%" class="text-center">
                        manager
                      </th>
                    </thead>
                    <tbody>
                         <c:if test="${empty list}">
						        <tr><td colspan="4" class="text-center">등록된 데이터가 없습니다.</td></tr>
							</c:if>
						    <c:forEach items="${list}" var="list" varStatus="status">
						    	<tr class="text-center">
						        <td >${status.count}</td>
						        <td>${list.subject }</td>
						        <td><img src="${list.thumburl }" width="100%"/></td>
						        <td>${list.regdate }</td>
						        <td><a href="${pageContext.request.contextPath}/summerModify?idx=${list.idx}" class="btn btn-warning">수정</a>&nbsp;
						        <a href='#deleteModal' data-toggle='modal'  data-id='${list.idx}' class='btn btn-danger open-deleteDialog' onclick='deleteView(${list.idx})' class="btn btn-danger">삭제</a></td>
						      </tr>
						     </c:forEach>
						    
                    </tbody>
                  </table>
                </div>
                
                
                
                 <!--  Delete Modal -->
  <div class="modal fade" id="deleteModal"  tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel"></h5>
	      <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick='location.reload()'>
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">	
	    <p>정말 삭제 하시겠습니까?</p>
      </div>
       <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary"  id="delete">Delete</button>
      </div>
    </div>
  </div>
</div>



 <script>
function deleteView(idx){
	  $("#delete").click(function(){
			$.ajax({
				type: "post", 
				processData: false,
				contentType:'application/x-www-form-urlencoded; charset=UTF-8',
				url: "${pageContext.request.contextPath}/summerDelete", 
				data: 'idx='+idx, 
				dataType: 'html',
				success: function(data){
					alert("삭제 되었습니다..");
					location.href="${pageContext.request.contextPath}/list"
				}
				,beforeSend : function(){
					$(".spinner").removeClass("displayLoding");
				},
				complete:function(){
					$(".spinner").addClass("displayLoding");	
				},
			    error:function(){
			    	$('#failModal').modal('show');
			    }
				
			})
		});
}
</script>
</body>
</html>