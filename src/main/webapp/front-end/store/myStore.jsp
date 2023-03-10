<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>E Store - eCommerce HTML Template</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="eCommerce HTML Template Free Download" name="keywords" />
<meta content="eCommerce HTML Template Free Download" name="description" />

<!-- Favicon -->
<link href="img/favicon.ico" rel="icon" />

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,400|Source+Code+Pro:700,900&display=swap"
	rel="stylesheet" />

<!-- CSS Libraries -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/front-end/store/lib/slick/slick.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/front-end/store/lib/slick/slick-theme.css"
	rel="stylesheet" />

<!-- Template Stylesheet -->
<link
	href="${pageContext.request.contextPath}/front-end/store/css/style.css"
	rel="stylesheet" />
<script src="${pageContext.request.contextPath}/JQ/jquery-3.6.1.min.js"></script>
<script src="${pageContext.request.contextPath}/JQ/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/JQ/messages_zh_TW.js"></script>


<script>
$().ready(function() {
	  $("#the_form").validate({
		    rules: {
		    	productName:{
		    		required: true,
		    		rangelength:[2,20]
		      	},
		      	productStock:{
		      		required:true,
		      		min:1,
		      		max:100000
		      	},
				productPrice:{
					required:true,
		      		min:1,
		      		max:1000000
		      	},
		      	productDesc:"required",
		      	source:"required",
	  			productSecID:"required",
	  			productStatus:"required"
		    },
		    messages: {
		    	productName:{
		    		required: "?????????????????????",
	    			rangelength:"??????????????????2-20???????????????"
		    	},
				productStock:{
					required:"?????????????????????",
					min:"????????????????????????1",
					max:"????????????????????????100000"
		      	},
				productPrice:{
					required:"?????????????????????",
					min:"????????????????????????1",
					max:"????????????????????????1000000"
		      	},
		    	productDesc:"?????????????????????",
		    	source:"????????????????????????",
		    	productSecID:"????????????????????????",
		    	productStatus:"??????????????????????????????"
		    },
		    submitHandler: function(form) {
		    	 alert("????????????");
		    	 form.submit();
		    }
	   })
});


$().ready(function() {
	  $("#storeForm").validate({
		    rules: {
	  			storeName:"required",
	  			storeDelBank:"required",
	  			storeBankAccount:"required",
	  			storeAddress:"required",
	  			phoneNumber:"required",
	  			taxID:"required"
		    },
		    messages: {
		    	storeName:"?????????????????????",
		    	storeDelBank:"???????????????????????????",
		    	storeBankAccount:"???????????????????????????",
		    	storeAddress:"?????????????????????",
		    	phoneNumber:"?????????????????????",
		    	taxID:"?????????????????????"
		    },
		    submitHandler: function(form) {
		    	 alert("????????????");
		    	 form.submit();
		    }
	   })
});
	$(function() {
		var template = 
		`<table class="table table-striped col-sm-12">
			<tr>
				<th>??????ID</th>
				<th>???????????????</th>
				<th>????????????</th>
				<th>??????</th>
				<th>?????????</th>
				<th>?????????</th>
				<th>??????</th>
				<th>????????????</th>
				<th>????????????</th>
				<th>??????</th>
				<th>??????</th>
				<th>??????</th>
			</tr>
		`;
		
		var str=``;
		function aaa(data){
			str=``;
			let status ='';
			for(let i=0;i<data.length;i++){
				if(data[i].productStatus==true){
					status = '?????????';
				}else{
					status = '?????????';
				}
				let templateList =   `
					<tr>
						<td>`+data[i].productID+`</td>
						<td>`+data[i].productSecID+`</td>
						<td>`+data[i].productName+`</td>
						<td><img src="${pageContext.request.contextPath}/product/picServlet?productID=`+data[i].productID+`"
						alt="????????????" width="100" height="100"></td>
						<td><img
							src="${pageContext.request.contextPath}/product/picServlet2?productID=`+data[i].productID+`"
							alt="????????????" width="100" height="100"></td>
						<td><img
							src="${pageContext.request.contextPath}/product/picServlet3?productID=`+data[i].productID+`"
							alt="????????????" width="100" height="100"></td>
						<td>`+data[i].productStock+`</td>
						<th>`+data[i].productPrice+`</th>
						<td>`+data[i].productDesc+`</td>
						<td>`+data[i].source+`</td>
						<th>`+status+`</th>
						<th><a href="${pageContext.request.contextPath}/product/productGetOne/get?productID=`+data[i].productID+`"><button>??????</button></a></th>
					</tr>
				`;
				str = str + templateList;
			}
		}
		var str2=`</table>`;
		
		//console.log($("#searchPID").val());
		//console.log($("#searchPSID").val());
		//console.log($("#searchPSTATUS").val());
		//console.log($("#searchSID").val());
		
		$("#btnSearchAll")
				.click(
						function() {
							$
									.ajax({
										url : "${pageContext.request.contextPath}/product/productSearchProduct/getAll_By_Cond", // ?????????????????????
										type : "POST", // GET | POST | PUT | DELETE | PATCH
										data : "storeID="+$("#searchSID").val()+"&productID="+$("#searchPID").val()
											+"&productSecID="+$("#searchPSID").val()+"&productStatus="+$("#searchPSTATUS").val()
											+"&productStock="+$("#searchPStock").val()+"&productStock2="+$("#searchPStock2").val()
											+"&productPrice="+$("#searchPPrice").val()+"&productPrice2="+$("#searchPPrice2").val()
											+"&productName="+$("#searchPName").val(),
										dataType : "json", // ?????????????????????????????????????????? json | xml | html
										success : function(data) {
											// request ???????????????????????????
											console.log(data);
											aaa(data);
											//console.log(str);
											//console.log(data[0].productID+" ??????");
											console.log(data.length);
											$("#searchRes").html("<h2>?????????????????????"+data.length+"???</h2>"+template+str+str2);
										},
										error : function(XMLHttpRequest) {
											if (XMLHttpRequest.status >= 400) {
												alert("????????????");
											}
										}
									});
						});
	});
</script>
<style>
.error{
	color:red;
}
</style>
</head>

<body>
	<%@ include file="Header.jsp"%>
	<!-- My Account Start -->
	<div class="my-account">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-3">
					<div class="nav flex-column nav-pills" role="tablist"
						aria-orientation="vertical">
						<a class="nav-link active"><i class="fa fa-tachometer-alt"></i>????????????</a>
						<a class="nav-link" id="orders-nav" data-toggle="pill"
							href="#storeReview" role="tab"><i class="fa fa-shopping-bag"></i>????????????</a>
						<a class="nav-link" id="orders-nav" data-toggle="pill"
							href="#storeintroduction" role="tab"><i
							class="fa fa-shopping-bag"></i>????????????</a> <a class="nav-link"><i
							class="fa fa-credit-card"></i>????????????</a> <a class="nav-link"
							id="address-nav" data-toggle="pill" href="#myProduct" role="tab"><i
							class="fa fa-map-marker-alt"></i>????????????</a> <a class="nav-link"
							id="account-nav" data-toggle="pill" href="#addProduct" role="tab"><i
							class="fa fa-user"></i>????????????</a> <a class="nav-link"><i
							class="fa fa-user"></i>????????????</a> <a class="nav-link" id="account-nav"
							data-toggle="pill" href="#orderQuery" role="tab"><i
							class="fa fa-user"></i>????????????</a> <a class="nav-link"><i
							class="fa fa-user"></i>????????????</a> <a class="nav-link" id="account-nav"
							data-toggle="pill" href="#account-tab" role="tab"><i
							class="fa fa-user"></i>??????????????????</a> <a class="nav-link"
							href="index.html"><i class="fa fa-sign-out-alt"></i>Logout</a>
					</div>
				</div>
				<div class="col-md-9">
					<div class="tab-content">
						<div class="tab-pane fade" id="storeReview" role="tabpanel"
							aria-labelledby="address-nav">
							<div class="col-sm-12">
								<div>
									<label>????????????</label>
								</div>
								<div>
									<label>???????????? : </label> <input type="text" name="productName"
										value="??????" />
									<button>??????</button>
								</div>
								<div>
									<table class="table table-striped col-sm-12">
										<tbody>
											<tr>
												<td>????????????</td>
												<td>????????????</td>
												<td>????????????</td>
												<td>????????????</td>
											</tr>
											<tr>
												<td>*****</td>
												<td>??????</td>
												<td>????????????</td>
												<td>GOOD</td>
											</tr>
											<tr>
												<td>*****</td>
												<td>??????</td>
												<td>????????????</td>
												<td>GOOD</td>
											</tr>
											<tr>
												<td>*****</td>
												<td>??????</td>
												<td>????????????</td>
												<td>GOOD</td>
											</tr>
											<tr>
												<td>*****</td>
												<td>??????</td>
												<td>????????????</td>
												<td>GOOD</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="tab-pane fade" id="storeintroduction" role="tabpanel"
							aria-labelledby="address-nav">
							<form id="storeForm" method="post"
								action="${pageContext.request.contextPath}/store/storeServlet/update">
								<div class="col-sm-12">
									<div>
										<h2>??????????????????</h2>
									</div>
									<div>
										<label>????????????????????? : </label> <input type="text" name="storeName"
											class="btn btn-secondary m-2" value="${storeVO2.storeName}" />
									</div>
									<div>
										<label>??????????????????????????? :</label> <input type="text"
											class="btn btn-secondary m-2" name="storeDelBank"
											value="${storeVO2.storeDelBankCode}" />
									</div>
									<div>
										<label>??????????????????????????? :</label> <input type="text"
											class="btn btn-secondary m-2" name="storeBankAccount"
											value="${storeVO2.storeBankAccount}" />
									</div>
									<div>
										<label>????????????????????? :</label> <input type="text"
											class="btn btn-secondary m-2" name="storeAddress"
											value="${storeVO2.storeAddress}" />
									</div>
									<div>
										<div>
											<label>?????????????????????</label><input type="text"
												class="btn btn-secondary m-2" name="phoneNumber"
												value="${storeVO2.phoneNumber}" />
										</div>
									</div>
									<div>
										<div>
											<label>?????????????????????</label><input type="text"
												class="btn btn-secondary m-2" name="taxID"
												value="${storeVO2.taxID}" />
										</div>
									</div>
									<div>
										<input type="submit" class="btn btn-secondary m-2" value="??????" />
									</div>
								</div>
							</form>
						</div>
						<div class="tab-pane fade" id="myProduct" role="tabpanel"
							aria-labelledby="address-nav">
							<div>
								<div class="col-sm-12">
									<label>?????????????????????</label> <input type="text" id="searchPName"
										name="productName" value="" />
								</div>
								<div class="col-sm-12">
									<label>???????????????ID</label> <input type="text" id="searchPID"
										name="productID" value="" /> <label>????????????????????????ID</label> <input
										type="text" id="searchPSID" name="productSecID" value="" />
								</div>
								<div class="col-sm-12">
									<label>????????????????????? ????????????</label> <input type="text" id="searchPStock"
										name="productStock" value="" /> <label>????????????????????? ????????????</label> <input
										type="text" id="searchPStock2" name="productStock2" value="" />
								</div>
								<div class="col-sm-12">
									<label>????????????????????? ????????????</label> <input type="text" id="searchPPrice"
										name="productPrice" value="" /> <label>????????????????????? ????????????</label> <input
										type="text" id="searchPPrice2" name="productPrice2" value="" />
								</div>
								<div class="col-sm-12">
								<label>?????????????????????:</label> <select class="form-select mb-3"
									aria-label="Default select example" name="productStatus"
									id="searchPSTATUS">
									<option selected value="true">?????????</option>
									<option value="false">?????????</option>
								</select> <input type="hidden" name=storeID id="searchSID"
									value="${storeVO2.storeID}" />
								</div>
								<button id="btnSearchAll">????????????</button>
							</div>
							<br />
							<div>
								<h2 class="col-sm-12">??????????????????</h2>
								<div id="searchRes"></div>
							</div>
						</div>
						<div class="tab-pane fade" id="orderQuery" role="tabpanel"
							aria-labelledby="address-nav">
							<div>
								???????????????ID: <input type="text" name="orderID" value="orderID" />
								???????????????ID: <input type="text" name="memberID" value="memberID" />
								?????????????????????: <input type="text" name="orderDate" value="orderDate" />
								?????????????????????: <input type="text" name="orderStatus"
									value="orderStatus" />
								<button>????????????</button>
							</div>
							<br />
							<div>
								<h2 class="col-sm-12">??????????????????</h2>
								<table class="table table-striped col-sm-12">
									<tbody>
										<tr>
											<td>??????ID</td>
											<td>??????ID</td>
											<td>????????????</td>
											<td>????????????</td>
											<td>???????????????</td>
										</tr>
										<tr>
											<td>001</td>
											<td>005</td>
											<td>2021/11/5</td>
											<td>?????????</td>
											<td>50000</td>
										</tr>
										<tr>
											<td>002</td>
											<td>005</td>
											<td>2021/11/5</td>
											<td>?????????</td>
											<td>50000</td>
										</tr>
										<tr>
											<td>003</td>
											<td>005</td>
											<td>2021/11/5</td>
											<td>?????????</td>
											<td>50000</td>
										</tr>
										<tr>
											<td>004</td>
											<td>005</td>
											<td>2021/11/5</td>
											<td>?????????</td>
											<td>50000</td>
										</tr>
										<tr>
											<td>005</td>
											<td>005</td>
											<td>2021/11/5</td>
											<td>?????????</td>
											<td>50000</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
						<div class="tab-pane fade" id="addProduct" role="tabpanel"
							aria-labelledby="account-nav">
							<h4>????????????</h4>
							<div class="row">
								<form
									action="${pageContext.request.contextPath}/product/productServlet/insert"
									method="post" id="the_form" enctype="multipart/form-data">
									<div>
										<label for="p_name">???????????????</label> <input type="text" id="p_name"
											class="form-control" name="productName" />
									</div>
									<div>
										?????????????????? <select class="form-select mb-3"
											aria-label="Default select example" name="productSecID">
											<option value="" selected>?????????</option>
											<c:forEach var="productSecVO" items="${ProductSec}">
												<option value="${productSecVO.productSecID}">${productSecVO.productSecName}</option>
											</c:forEach>
										</select>
									</div>

									<div>
										<label>???????????????</label> <input type="text" min="1" max="100000"
											value="" id="p_count" class="form-control"
											name="productStock" /> <span id="p_count_value"></span>
									</div>
									<div>
										<label>???????????????</label> <input type="text" value="" id="p_count"
											class="form-control" name="productPrice" /> <span
											id="p_count_value"></span>
									</div>
									<div>
										<label>????????????1:</label> <input type="file" id="p_file"
											class="btn btn-secondary m-2" name="upfile1" /> <br /> <label>????????????2???</label>
										<input type="file" id="p_file2" class="btn btn-secondary m-2"
											name="upfile2" /> <br /> <label>????????????3???</label> <input
											type="file" id="p_file3" class="btn btn-secondary m-2"
											name="upfile3" /> <br />
									</div>
									<div class="input-group">
										<span class="input-group-text">???????????????</span>
										<textarea class="form-control" aria-label="With textarea"
											name="productDesc"></textarea>
									</div>
									<div>
										<label>?????????</label> <input type="text" id="p_source"
											class="form-control" name="source" />
									</div>
									<div>
										???????????? <select class="form-select mb-3"
											aria-label="Default select example" name="productStatus">
											<option value="" selected>?????????</option>
											<option value="1">?????????</option>
											<option value="0">?????????</option>
										</select>
									</div>

									<input type="hidden" name="action" value="insert"> <input
										type="submit" value="????????????">
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- My Account End -->

	<%@ include file="Footer.jsp"%>

	<!-- Back to Top -->
	<a href="#" class="back-to-top"><i class="fa fa-chevron-up"></i></a>

	<!-- JavaScript Libraries -->
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/front-end/store/lib/easing/easing.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/front-end/store/lib/slick/slick.min.js"></script>

	<!-- Template Javascript -->
	<script
		src="${pageContext.request.contextPath}/front-end/store/js/main.js"></script>
</body>
</html>