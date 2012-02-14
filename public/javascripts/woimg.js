var MyClass = new Class({});
var WoImg = new Class({
	popular: function(){
		Xhr.load('/page', {
				onSuccess: function(req){
					var text = req.responseText;
					var temp = new Element('div');
					temp.html(text);
					$('loading-hiddent-div').append(temp);
					var imgs = temp.find('#loading-container')[0].children();
					var i = 0;
					var heights = [0,0,0,0,0];
					for( ;i<imgs.length; i++){
						var div = imgs[i];
						var j = 0;
						var minheight = 100000000000;
						var minheightIdx = 0;
						for(;j<heights.length;j++){
							if(heights[j]<minheight){
								minheightIdx = j;
								minheight = heights[j];
							}
						}
						var inc = div.dimensions().height + 10;
						
						div.setStyle('left',( (minheightIdx)*190 ) + 'px');
						div.setStyle('top', ( minheight ) +'px');
						var datahash= div.get('data-hash');
						var img = div.find('#'+datahash)[0];
						img.set('src',div.get('data-img-url'));
						$("container").append(div);
						heights[minheightIdx] = heights[minheightIdx]+ inc;
					}
					
				}
			});	
	},
	boardpage: function(board){
		Xhr.load('/boardpage', {
				params: {'hash': board},
				onSuccess: function(req){
					var text = req.responseText;
					var temp = new Element('div');
					temp.html(text);
					$('loading-hiddent-div').append(temp);
					var imgs = temp.find('#loading-container')[0].children();
					var i = 0;
					var heights = [0,0,0,0,0];
					for( ;i<imgs.length; i++){
						var div = imgs[i];
						var j = 0;
						var minheight = 100000000000;
						var minheightIdx = 0;
						for(;j<heights.length;j++){
							if(heights[j]<minheight){
								minheightIdx = j;
								minheight = heights[j];
							}
						}
						var inc = div.dimensions().height + 10;
						
						div.setStyle('left',( (minheightIdx)*190 ) + 'px');
						div.setStyle('top', ( minheight ) +'px');
						var datahash= div.get('data-hash');
						var img = div.find('#'+datahash)[0];
						img.set('src',div.get('data-img-url'));
						$("container").append(div);
						heights[minheightIdx] = heights[minheightIdx]+ inc;
					}
					
				}
			});	
	}
});
