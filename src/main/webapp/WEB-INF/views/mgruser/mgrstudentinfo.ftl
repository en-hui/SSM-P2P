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

                //点击添加学生按钮
                $("#addStudentBtn").click(function(){
                    $("#editForm")[0].reset();
                    $("#studentModal").modal("show");
                });

                //点击保存
                $("#editForm").ajaxForm(function(data){

                    if(data.success==true){
                        $.messager.confirm("提示","保存成功",function(){
                            window.location.reload();
                        });
                    }else{
                        $.messager.popup(data.msg);
                    }

                });
                $("#saveBtn").click(function(){
                    if($("#stuid").val()!="" && $("#stuname").val()!=""){
                        $("#editForm").submit();
                    }else{
                        $.messager.popup("请完整填写信息");
                    }

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
				<#assign currentMenu = "student" />
				<#include "../mgrcommon/menu.ftl" />
        </div>
        <div class="col-sm-9">
            <div class="page-header">
                <h3>学院学生管理</h3>
            </div>
            <div class="row">
                <!-- 提交分页的表单 -->
                <form id="searchForm" class="form-inline" method="post" action="/studentinfo.do">
                    <input type="hidden" name="currentPage" id="currentPage" value=""/>
                    <div class="form-group">
                    </div>
                    <div class="form-group">
                        <label>学号</label>
                        <input class="form-control" type="text" name="studyId" value="${(qo.studyId)!''}">
                    </div>
                    <div class="form-group">
                        <button id="query" type="button" class="btn btn-success"><i class="icon-search"></i> 查询</button>
                        <a href="javascript:void(-1);" class="btn btn-success" id="addStudentBtn">添加学生信息</a>
                    </div>
                </form>
            </div>
            <div class="row">
                <table class="table">
                    <thead>
                    <tr>

                        <th>学号</th>
                        <th>姓名</th>

                    </tr>
                    </thead>
                    <tbody>
						<#list pageResult.listData as vo>
                        <tr>

                            <td>${vo.stuid}</td>
                            <td>${vo.stuname}</td>

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


<div id="studentModal" class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑/增加</h4>
            </div>
            <div class="modal-body">
                <form id="editForm" class="form-horizontal" method="post" action="/addstudentinfo.do" style="margin: -3px 118px">
                    <input id="systemDictionaryId" type="hidden" name="id" value="" />
                    <div class="form-group">
                        <label class="col-sm-2 control-label">学号</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="stuid" name="stuid" placeholder="学号">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">姓名</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="stuname" name="stuname" placeholder="姓名">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a href="javascript:void(0);" class="btn btn-success" id="saveBtn" aria-hidden="true">保存</a>
                <a href="javascript:void(0);" class="btn" data-dismiss="modal" aria-hidden="true">关闭</a>
            </div>
        </div>
    </div>
</div>

</body>
</html>