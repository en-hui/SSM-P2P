<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>P2P平台</title>
		<!-- 包含一个模板文件,模板文件的路径是从当前路径开始找 -->
		<#include "common/links-tpl.ftl" />
		<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		
		<script type="text/javascript">
			$(function(){
				if($("#showBindPhoneModal").size()>0){
					//点击立刻绑定,弹出模式窗口
					$("#showBindPhoneModal").click(function(){
						$("#bindPhoneForm")[0].reset(); 
						$("#bindPhoneModal").modal("show");
					});
					//给发送短信按钮添加时间
					$("#sendVerifyCode").click(function(){
						var _this=$(this);
						_this.attr("disabled",true);
						//1,发送一个Ajax请求;
						$.ajax({
							url:"/sendVerifyCode.do",
							dataType:"json",
							type:"POST",
							data:{phoneNumber:$("#phoneNumber").val()},
							success:function(data){
								if(data.success){
									var sec=90;
									var timer=window.setInterval(function(){
										sec--;
										if(sec>0){
											_this.text(sec+"秒重新发送");
										}else{
											//去掉定时器
											window.clearInterval(timer);
											_this.text("重新发送验证码");
											_this.attr("disabled",false);
										}
									},1000);
								}else{
									$.messager.popup(data.msg);
									_this.attr("disabled",false);
								}
							}
						});
					});
					
					//给提交绑定窗口按钮添加事件
					$("#bindPhoneForm").ajaxForm(function(data){
						if(data.success){
							window.location.reload();
						}else{
							$.messager.popup(data.msg);
						}
					});
					$("#bindPhone").click(function(){
						$("#bindPhoneForm").submit();
					});
				};
				
				if($("#showBindEmailModal").size()>0){
					//点击立刻绑定,弹出模式窗口
					$("#showBindEmailModal").click(function(){
						$("#bindEmailForm")[0].reset();
						$("#bindEmailModal").modal("show");
					});
					//给提交绑定窗口按钮添加事件
					$("#bindEmailForm").ajaxForm(function(data){
						if(data.success){
							window.location.reload();
						}else{
							$.messager.popup(data.msg);
						}
					});
					$("#bindEmail").click(function(){
						$("#bindEmailForm").submit();
					});
				}

				if($("#showEmail").size()>0){
				    $("#showEmail").click(function () {
						$("#showEmailModal").modal("show");
                    });
				}

				if($("#showPhone").size()>0){
				    $("#showPhone").click(function () {
						$("#showPhoneModal").modal("show");
                    });
				}
			})
		</script>
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		<!-- 网页导航 -->
		<!-- 在当前的freemarker的上下文中,添加一个变量,变量的名字叫currentNav,变量的值叫personal -->
		<#assign currentNav="personal" />
		<#include "common/navbar-tpl.ftl" />
		
		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#assign currentMenu="personal" />
					<#include "common/leftmenu-tpl.ftl" />
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<div class="panel panel-default">
						<div class="panel-body el-account">
							<div class="el-account-info">
								<div class="pull-left el-head-img">
									<img class="icon" src="/images/ms.png" />
								</div>
								<div class="pull-left el-head">
									<p>用户名：${logininfo.username}</p>
									<p>最后登录时间：2019-03-25 15:30:10</p>
								</div>

								<div class="clearfix"></div>
							</div>
							

							
							<div class="row h4 account-info">
								<div class="col-sm-4">
									待收利息：<span class="text-primary">${account.unReceiveInterest}元</span>
								</div>
								<div class="col-sm-4">
									待收本金：<span class="text-primary">${account.unReceivePrincipal}元</span>	
								</div>
								<div class="col-sm-4">
									待还本息：<span class="text-primary">${account.unReturnAmount}元</span>
								</div>
							</div>
							
							<div class="el-account-info top-margin">
								<div class="row">

                                    <div class="col-sm-4">
                                        <div class="el-accoun-auth">
                                            <div class="el-accoun-auth-left">
                                                <img src="images/shouji.jpg" />
                                            </div>
                                            <div class="el-accoun-auth-right">
                                                <h5>手机绑定</h5>
												<#if userinfo.isBindPhone >
												<p>
                                                    已绑定
                                                    <a href="javascript:;" id="showPhone">查看</a>
                                                </p>
												<#else>
												<p>
                                                    未绑定
                                                    <a href="javascript:;" id="showBindPhoneModal">立刻绑定</a>
                                                </p>
												</#if>
                                            </div>
                                            <div class="clearfix"></div>
                                            <p class="info">可以用作实名校验数据，提高安全性</p>
                                        </div>
                                    </div>


									<div class="col-sm-4">
										<div class="el-accoun-auth">
											<div class="el-accoun-auth-left">
												<img src="images/shiming.png" />
											</div>
											<div class="el-accoun-auth-right">
												<h5>实名认证</h5>
												<#if userinfo.isRealAuth >
												<p>
													已认证
													<a href="/realAuth.do">查看</a>
												</p>
												<#else>
												<p>
													未认证
													<a href="/realAuth.do" id="">立刻认证</a>
												</p>
												</#if>
											</div>
											<div class="clearfix"></div>
											<p class="info">实名认证之后才能在平台借款</p>
										</div>
									</div>

									<div class="col-sm-4">
										<div class="el-accoun-auth">
											<div class="el-accoun-auth-left">
												<img src="images/youxiang.jpg" />
											</div>
											<div class="el-accoun-auth-right">
												<h5>邮箱绑定</h5>
												<#if userinfo.isBindEmail>
												<p>
													已绑定
													<a href="javascript:;" id="showEmail">查看</a>
												</p>
												<#else>
												<p>
													未绑定
													<a href="javascript:;" id="showBindEmailModal">去绑定</a>
												</p>
												</#if>
											</div>
											<div class="clearfix"></div>
											<p class="info">您可以设置邮箱来接收重要信息</p>
										</div>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>		
		
		<#if !userinfo.isBindPhone>
		<div class="modal fade" id="bindPhoneModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			<h4 class="modal-title" id="exampleModalLabel">绑定手机</h4>
		      </div>
		      <div class="modal-body">
				<form class="form-horizontal" id="bindPhoneForm" method="post" action="/bindPhone.do">
					<div class="form-group">
						    <label for="phoneNumber" class="col-sm-2 control-label">手机号:</label>
						    <div class="col-sm-4">
						      <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" />
						      <button id="sendVerifyCode" class="btn btn-primary" type="button" autocomplate="off">发送验证码</button>
						    </div>
						</div>
						<div class="form-group">
						    <label for="verifyCode" class="col-sm-2 control-label">验证码:</label>
						    <div class="col-sm-4">
						      <input type="text" class="form-control" id="verifyCode" name="verifyCode" />
						    </div>
						</div>
				</form>
		      </div>
		      <div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="bindPhone">保存</button>
		      </div>
		    </div>
		  </div>
		</div>
		</#if>
		
		
		<#if !userinfo.isBindEmail>
		<div class="modal fade" id="bindEmailModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			<h4 class="modal-title" id="exampleModalLabel">绑定邮箱</h4>
		      </div>
		      <div class="modal-body">
				<form class="form-horizontal" id="bindEmailForm" method="post" action="/sendEmail.do">
					<div class="form-group">
					    <label for="email" class="col-sm-2 control-label">Email:</label>
					    <div class="col-sm-4">
					      <input type="text" class="form-control" id="email" name="email" />
					    </div>
					</div>
				</form>
		      </div>
		      <div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="bindEmail">保存</button>
		      </div>
		    </div>
		  </div>
		</div>
		</#if>

		<#if userinfo.isBindEmail>
		    <div class="modal fade" id="showEmailModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="exampleModalLabel">绑定邮箱</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <label for="email" class="col-sm-2 control-label">Email:</label>
                                    <div class="col-sm-4">
                                        <input class="form-control" id="email" value="${userinfo.email}" style="width: 200px" readonly="readonly"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
		</#if>

		<#if userinfo.isBindPhone>
		<div class="modal fade" id="showPhoneModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="exampleModalLabel">绑定手机</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label for="phoneNumber" class="col-sm-2 control-label">手机号:</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="phoneNumber" value="${userinfo.phoneNumber}" readonly="readonly"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
		</#if>

		
		
		<#include "common/footer-tpl.ftl" />
	</body>
</html>