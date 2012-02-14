%{user = _arg}%
#{if user.avatar}<img class="grid_2 left" style="width:50px;" src="${user.avatar}">#{/if}
#{else}<img class="grid_2 left" style="width:50px;" src="@{'/public/images/user-default.png'}">#{/else}
