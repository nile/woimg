#{if _user 
	&& _user?.id == _target?.user?.id}
	#{doBody/}
#{/if}