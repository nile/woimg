var MyClass = new Class({});
var WoImg = new Class({
	popular: function(){
		Xhr.load('/popular', {
				onSuccess: function(req){
					var imgs = req.responseJSON;
					imgs.each(function (a){
						var ae = new Element('a');
						ae.set('href', a.viewLink);
						var img = new Element('img');
						img.set('src', a.smallLink);
						ae.append(img);
						$("popular").append(ae);
					});
				}
			});	
	},
	latest: function(){
		Xhr.load('/latest', {
				onSuccess: function(req){
					var imgs = req.responseJSON;
					imgs.each(function (a){
						var ae = new Element('a');
						ae.set('href', a.viewLink);
						var img = new Element('img');
						img.set('src', a.smallLink);
						ae.append(img);
						$("latest").append(ae);
					});
				}
			});	
	}
});
