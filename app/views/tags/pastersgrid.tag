%{
_pasters=_arg;
_size = _pasters.size();
}%
<div style="text-align:center;">
#{list items: _pasters, as: 'p'}
       #{a @Application.view(p.hash)}<img id="${p.img.hash}" src="${p.img.thumbLink()}" style="width:66px"/>#{/a}
#{/list}
#{list ( (_size)..<9), as: 't'}
  	<a href="#"><img src="@{'/public/images/place-holder.png'}" style="border:1px solid #eee;width:64px"/></a>
#{/list}
</div>