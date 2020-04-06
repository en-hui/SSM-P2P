<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>P2P平台</title>
		<#include "common/links-tpl.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
		<script>

            //ajax实现二级联动
            function ajaxLinkage(){
                var departmentValue = $('#department option:selected').val();
                if(departmentValue!=null && departmentValue!=""){
                    $.ajax({
                        url:"/ajaxLinkage.do",
                        dataType:"json",
                        type:"POST",
                        data:{departmentValue:departmentValue},
                        success:function(data){
                            $("#profession").empty();
                            for(var i=0;i<data.length;i++){
                                $("#profession").append("<option value='"+data[i].id+"'>"+data[i].title+"</option>");
                            }
                            $("#profession option[value=${(userinfo.profession.id)!-1}]").attr("selected",true);
                        }
                    });
                }
            }

            //页面初始化
			$(function(){

			    ajaxLinkage();
                //AJAX提交表单
				$("#userInfoForm").ajaxForm(function(){
				    var studyId = $("#studyId").val();
				    var clazz = $("#clazz").val();
				    if( clazz==""){
				        $.messager.popup("请完整填写信息");
				        return false;
                    }else{
                        $.messager.confirm("提示","修改成功!",function(){
                            window.location.reload();
                        })
                    }

				})
			})
		</script>		
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		
		<#assign currentNav="personal"/>
		<!-- 网页导航 -->
		<#include "common/navbar-tpl.ftl" />
		
		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#assign currentMenu="userInfo" />
					<#include "common/leftmenu-tpl.ftl" />
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<div class="panel panel-default">
						<div class="panel-heading">
							个人资料
						</div>
						<form id="userInfoForm" class="form-horizontal" action="/basicInfo_save.do" method="post" style="width: 700px;">
							<div class="form-group">
								<label class="col-sm-4 control-label">
									用户名
								</label>
								<div class="col-sm-8">
									<p class="form-control-static">${logininfo.username}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">
									真实姓名
								</label>
								<div class="col-sm-8">
									<p class="form-control-static">
										<#if (userinfo.isRealAuth)>
											${userinfo.realName}
										<#else>
											未认证
											<a href="/realAuth.do">[马上认证]</a>
										</#if>
									</p>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-4 control-label">
									身份证号码
								</label>
								<div class="col-sm-8">
									<p class="form-control-static">	
										<#if (userinfo.isRealAuth)>
											${userinfo.idNumber}
										<#else>
											未认证
											<a href="/realAuth.do">[马上认证]</a>
										</#if>
									</p>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-4 control-label">
									手机号码
								</label>
								<div class="col-sm-8">
									<label style="width: 250px;" class="form-control">${(userinfo.phoneNumber)!''}</label>
								</div>
							</div>


							
							<div class="form-group">
								<label class="col-sm-4 control-label">
									院系
								</label>

								<div class="col-sm-8">
									<select class="form-control" id="department" name="department.id" style="width: 180px" autocomplate="off" onchange="ajaxLinkage()">
                                        <#list departments as item>
                                            <option value="${item.id}">${item.title}</option>
                                        </#list>
									</select>
									<script type="text/javascript">
										$("#department option[value=${(userinfo.department.id)!-1}]").attr("selected",true);
									</script>
								</div>

							</div>
							
							<div class="form-group">
								<label class="col-sm-4 control-label">
									专业
								</label>

								<div class="col-sm-8">
									<select class="form-control" id="profession" name="profession.id" style="width: 180px" autocomplate="off" >

									</select>
									<script type="text/javascript">
										$("#profession option[value=${(userinfo.profession.id)!-1}]").attr("selected",true);
									</script>
								</div>
							</div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">
                                    班级
                                </label>
                                <div class="col-sm-8">
									<#if userinfo.clazz??>
										<input style="width: 250px;" type="text" class="form-control" id="clazz" name="clazz" value="${userinfo.clazz?c}">
                                    <#else>
										<input style="width: 250px;" type="text" class="form-control" id="clazz" name="clazz" value="">
									</#if >
								</div>
                            </div>

							




							
							<div class="form-group">
								<button id="submitBtn" type="submit" class="btn btn-primary col-sm-offset-5" data-loading-text="数据保存中" autocomplate="off">
									保存数据
								</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>		

	</body>
</html>