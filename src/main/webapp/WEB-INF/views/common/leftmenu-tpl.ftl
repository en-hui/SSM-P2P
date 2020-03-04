<ul id="menu" class="list-group">
	<li class="list-group-item">
		<a href="javascript:;"><span>借贷项目</span></a>
		<ul>
			<li name="bid"><a href="/bid_list.do"><span>投标明细</span></a></li>
			<li name="receive"><a href="/receivables_list.do"><span>收款明细</span></a></li>
			<li name="bidRequest"><a href="/borrow_list.do"><span>借款项目</span></a></li>
			<li name="borrowBidReturn"><a href="/borrowBidReturn_list.do"><span>还款明细</span></a></li>
		</ul>
	</li>
	<li class="list-group-item">
		<a href="#"><span class="text-title">我的账户</span></a>
		<ul class="in">
			<li name="personal"><a href="/personal.do">账户信息</a></li>
			<li name="realAuth"><a href="/realAuth.do">实名认证</a></li>
			<li name="ipLog"><a href="/ipLog.do">登录记录</a></li>
			<li name="userInfo"><a href="/basicInfo.do"> <span>个人资料</span></a></li>
		</ul>
	</li>
</ul>

<#if currentMenu??>
<script type="text/javascript">
	$(".list-group-item li[name=${currentMenu}]").addClass("active");
</script>
</#if>