<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>P2P平台</title>
		<#include "common/links-tpl.ftl" />
		
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		<script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
		<script type="text/javascript" src="/js/plugins-override.js"></script>
		<script type="text/javascript">
			$(function(){
				$.extend($.fn.twbsPagination.defaults,{
					first:"首页",
				    prev:"上一页",
				    next:"下一页",
				    last:"最后一页"
				});
				
				$('#pagination').twbsPagination({
					totalPages : ${pageResult.totalPage}||1,
					currentPage : ${pageResult.currentPage},
					visiblePages : 5,
					onPageClick : function(event, page) {
						$("#currentPage").val(page);
						$("#searchForm").submit();
					}
				});
				
				$("#query").click(function(){
					$("#currentPage").val(1);
					$("#searchForm").submit();
				})
			});
	 </script>
	</head>
	<body>
	
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		<#assign currentNav="account" />
		<!-- 网页导航 -->
		<#include "common/navbar-tpl.ftl" />
		
		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#assign currentMenu="bid" />
					<#include "common/leftmenu-tpl.ftl" />		
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
				
					<form action="/bid_list.do" name="searchForm" id="searchForm" class="form-inline">
						<input type="hidden" id="currentPage" name="currentPage" value="" />
						<div class="form-group">
						    <label>状态</label>
						    <select class="form-control" name="bidRequestState">
						    	<option value="-1">全部</option>
                                <option value="0">待确认</option>
                                <option value="1">招标中</option>
                                <option value="2">借款取消</option>
                                <option value="3">超时</option>
                                <option value="4">投资取消</option>
                                <option value="7">还款中</option>
                                <option value="8">已还清</option>
                                <option value="9">逾期</option>
						    </select>
						    <script type="text/javascript">
						    	$("[name=bidRequestState] option[value='${(qo.bidRequestState)!''}']").attr("selected","selected");
						    </script>
						</div>
						<div class="form-group">
							<button id="query" class="btn btn-success"><i class="icon-search"></i> 查询</button>
						</div>
					</form>
					
					<div class="panel panel-default" style="margin-top: 20px;">
						<div class="panel-heading">
							投资项目
						</div>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>投资借款</th>
									<th>投资时间</th>
									<th>投资金额(元)</th>
									<th>利息</th>
									<th>状态</th>
								</tr>
							</thead>
							<tbody>
								<#list pageResult.listData as data>
									<tr>
										<td><a target="_blank" href="/borrow_info.do?id=${data.bidRequestId}">${data.bidRequestTitle}</a></td>
										<td>${data.bidTime?string("yyyy-MM-dd HH:mm:SS")}</td>
								        <td>${data.availableAmount} 元</td>
								        <td>${data.actualRate} %</td>
								        <td>${data.bidRequestStateDisplay}</td>
									</tr>
								</#list>
							</tbody>
						</table>
						<div style="text-align: center;">
							<ul id="pagination" class="pagination"></ul>
						</div>
					</div>
				</div>
			</div>
		</div>		

	</body>
</html>