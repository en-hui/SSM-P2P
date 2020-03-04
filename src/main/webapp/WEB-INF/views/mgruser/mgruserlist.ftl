<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>P2P平台(系统管理平台)</title>
	<#include "../mgrcommon/header.ftl"/>
    <script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>

    <script type="text/javascript">
        $(function(){
            $(function() {
                $("#pagination").twbsPagination({
                    totalPages:${pageResult.totalPage},
                    visiblePages:5,
                    startPage:${qo.currentPage},
                    first:"首页",
                    prev:"上一页",
                    next:"下一页",
                    last:"尾页",
                    onPageClick:function(event,page){
                        $("#currentPage").val(page);
                        $("#searchForm").submit();
                    }
                });

                $("#query").click(function(){
                    $("#currentPage").val(1);
                    $("#searchForm").submit();
                });

            });
        });
    </script>
</head>

<body>
<div class="container">
		<#include "../mgrcommon/top.ftl"/>
    <div class="row">
        <div class="col-sm-3">
				<#assign currentMenu = "userlist" />
				<#include "../mgrcommon/menu.ftl" />
        </div>
        <div class="col-sm-9">
            <div class="page-header">
                <h3>平台用户管理</h3>
            </div>
            <div class="row">
                <!-- 提交分页的表单 -->
                <form id="searchForm" class="form-inline" method="post" action="/real_auth_list.do">
                    <input type="hidden" name="currentPage" id="currentPage" value=""/>
                    <div class="form-group">
                    </div>
                    <div class="form-group">
                        <label>学号</label>
                        <input class="form-control" type="text" name="studyId" value="${(qo.studyId)!''}">
                    </div>
                    <div class="form-group">
                        <button id="query" type="button" class="btn btn-success"><i class="icon-search"></i> 查询</button>
                    </div>
                </form>
            </div>
            <div class="row">
                <table class="table">
                    <thead>
                    <tr>

                        <th>学号</th>
                        <th>姓名</th>
                        <th>手机号</th>

                    </tr>
                    </thead>
                    <tbody>
						<#list pageResult.listData as vo>
                        <tr>

                            <td>${vo.studyId}</td>
                            <td>${vo.realName}</td>
                            <td>${vo.phoneNumber}</td>

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