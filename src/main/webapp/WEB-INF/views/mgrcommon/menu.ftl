<ul id="menu" class="list-group">
	<#--<li class="list-group-item">
		<a href="#" data-toggle="collapse" data-target="#usermanage_detail"><span>用户管理</span></a>
		<ul class="in" id="usermanage_detail">
			&lt;#&ndash;<li class="userlist"><a href="/real_auth_list.do">平台用户管理</a></li>&ndash;&gt;
			<li class="student"><a href="/studentinfo.do">学生数据管理</a></li>
		</ul>
	</li>-->
	<li class="list-group-item">
		<a href="#" data-toggle="collapse" data-target="#permissionmanage_detail"><span>平台管理</span></a>
		<ul class="in" id="permissionmanage_detail">
            <li class="student"><a href="/studentinfo.do">学院学生管理</a></li>
			<li class="systemDictionary"><a href="/systemDictionary_list.do"><span>学院院系管理</span></a></li>
			<li class="systemDictionaryItem"><a href="/systemDictionaryItem_list.do"><span>学院专业管理</span></a></li>
			<li class="ipLog"><a href="/mgripLog.do"><span>登录历史</span></a></li>
		</ul>
	</li>
	<li class="list-group-item">
		<a href="#" data-toggle="collapse" data-target="#auditmanage_detail">
			<span>审核项目</span>
		</a>
		<ul class="in" id="auditmanage_detail">
			<li class="realAuth"><a href="/mgrrealAuth.do">实名认证审核</a></li>
		</ul>
	</li>

</ul>

<#if currentMenu??>
<script type="text/javascript">
	$(".in li.${currentMenu}").addClass("active");
</script>
</#if>