<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>P2P平台</title>
		<#include "common/links-tpl.ftl" />
    <link type="text/css" rel="stylesheet" href="/css/account.css" />
    <script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
    <script type="text/javascript" src="/js/plugins-override.js"></script>
    <script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/bank.js"></script>
    <script type="text/javascript">
        $(function(){
            $('#pagination').twbsPagination({
                totalPages : ${pageResult.totalPage},
                startPage : ${pageResult.currentPage},
                visiblePages : 5,
                first:"首页",
                prev:"上一页",
                next:"下一页",
                last:"最后一页",
                onPageClick : function(event, page) {
                    $("#currentPage").val(page);
                    $("#searchForm").submit();
                }
            });

            $("#query").click(function(){
                $("#currentPage").val(1);
                $("#searchForm").submit();
            });

            $(".beginDate,.endDate").click(function(){
                WdatePicker();
            });

            $(".return_money").click(function(){

                var returnMoney=parseFloat($(this).data("returnmoney"));
                $.ajax({
                    dataType:"json",
                    type:"POST",
                    url:"/returnMoneyTransaction.do",
                    data:{id:$(this).data("rid"),
                        type:2},
                    success:function(data){
                        if(data.success){
                            $.messager.confirm("提示","收款成功",function(){
                                window.location.reload();
                            });
                        }else{
                            $.messager.popup(data.msg);
                        }
                    }
                });
            });
        });
    </script>
</head>
<body>

<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		<#assign currentNav="personal" />
<!-- 网页导航 -->
		<#include "common/navbar-tpl.ftl" />

<div class="container">
    <div class="row">
        <!--导航菜单-->
        <div class="col-sm-3">
					<#assign currentMenu="receive" />
					<#include "common/leftmenu-tpl.ftl" />
        </div>
        <!-- 功能页面 -->
        <div class="col-sm-9">
            <form action="/borrowBidReturn_list.do" name="searchForm" id="searchForm" class="form-inline" >
                <input type="hidden" id="currentPage" name="currentPage" value="" />
                <div class="form-group">
                    <label>时间范围</label>
                    <input type="text" class="form-control beginDate" name="beginDate" value='${(qo.beginDate?string("yyyy-MM-dd"))!""}'/>
                </div>
                <div class="form-group">
                    <label></label>
                    <input type="text" class="form-control endDate" name="endDate" value='${(qo.endDate?string("yyyy-MM-dd"))!""}'/>
                </div>
                <div class="form-group">
                    <label>状态</label>
                    <select class="form-control" name="state">
                        <option value="-1">全部</option>
                        <option value="0">待还款</option>
                        <option value="1">已还款</option>
                        <option value="3">待确认</option>
                        <option value="2">逾期</option>
                    </select>
                    <script type="text/javascript">
                        $("[name=state] option[value='${(qo.state)!''}']").attr("selected","selected");
                    </script>
                </div>
                <div class="form-group">
                    <button id="query" class="btn btn-success"><i class="icon-search"></i> 查询</button>
                </div>
            </form>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <span class="pull-left" style="line-height: 35px;">收款明细</span>
                    <div class="clearfix"></div>
                </div>
                <table class="table">
                    <thead>
                    <tr>
                        <th>借款</th>
                        <th>收款金额</th>
                        <th>收款本金</th>
                        <th>收款利息</th>
                        <th>收款期数</th>
                        <th>收款期限</th>
                        <th>收款状态</th>
                    </tr>
                    </thead>
                    <tbody>
								<#list pageResult.listData as data>
                                <tr>
                                    <td><a href="/borrow_info.do?id=${data.bidRequestId}">${data.bidRequestTitle}</a></td>
                                    <td>${data.totalAmount}元</td>
                                    <td>${data.principal}元</td>
                                    <td>${data.interest}元</td>
                                    <td>${data.monthIndex}期</td>
                                    <td>${data.deadLine?string("yyyy-MM-dd")}</td>
                                    <td>
								        	<#if data.state=3>
                                                <a class="btn btn-success return_money" data-returnmoney="${data.totalAmount?string('0.##')}" data-rid="${data.id}">确认收款</a>
                                            <#else>
                                                ${data.stateDisplay}
                                            </#if>
                                    </td>
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