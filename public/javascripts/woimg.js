var WoImg = new Class((function(opt) {
    var options ;
    var _create_img_paster = function (div, heights){
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
        div.on({
                 mouseenter: show_toolbar,
                 mouseleave: hide_toolbar
                 });
        heights[minheightIdx] = heights[minheightIdx]+ inc;
    };
    return {
        initialize: function(opt) {
            this.options = opt;
        },
        popular: function(url){
            Xhr.load(url, {
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
                            _create_img_paster(div, heights)  ;
                            $("container").append(div);
                        }

                    }
                });
        },
        boardpage: function(url){
            Xhr.load(url, {
                    params: {},
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
                             _create_img_paster(div, heights)  ;
                            $("container").append(div);
                        }
                    }
                });
        },
        category_page: function( url){
             Xhr.load(url, {
                            params: {},
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
                                     _create_img_paster(div, heights)  ;
                                    $("container").append(div);
                                }
                            }
                        });
        },

        add_from_website:function (){
            new Lightbox({showButtons:false, showTitle: false}).show($('dlg-add-from-website').clone());
        },
        add_from_upload: function(){
            new Lightbox({showButtons:false, showTitle: false}).show($('dlg-add-from-upload').clone());
        },
        create_board:function(){
            new Lightbox({showButtons:false, showTitle: false}).show($('dlg-add-create-board').clone());
        },
        repaste: function(hash){
            new Lightbox({showButtons:false, showTitle: false}).load(this.options.repasteurl,{params:{'hash':hash}});
        }
	}
})());


var url_find_images = '/Application/findimages'
function find_images(event){
	input = $(event.target).parent('form').first('[name=url]')
	var url = input.getValue();
	var xhr = new Xhr(url_find_images,{
		method: 'post',
		onSuccess: function(r){
			imgs = r.responseJSON;
			preview_img = $(event.target).parent('form').first('#preview_img');
			preview_img.data('imgs',r.responseJSON);
			if(imgs.length>0){
				preview_img.set('src',imgs[0].url);
				preview_img.data('idx',0);
				imgurl = $(event.target).parent('form').first('[name=imgurl]')
				imgurl.setValue(imgs[0].url);
			}
			
		}
	});
	xhr.send({'url':url});
} 
function prev_image(event){
	preview_img = $(event.target).parent('form').first('#preview_img');
	imgs = preview_img.data('imgs');
	idx = preview_img.data('idx');
	if(idx>0){
		idx--;
		preview_img.set('src',imgs[idx].url);
		preview_img.data('idx',idx);
        imgurl = $(event.target).parent('form').first('[name=imgurl]')
        imgurl.setValue(imgs[0].url);
	}
}
function next_image(event){
	preview_img = $(event.target).parent('form').first('#preview_img');
	imgs = preview_img.data('imgs');
	idx = preview_img.data('idx');
	if( idx < imgs.length-1 ){
		idx++;
		preview_img.set('src',imgs[idx].url);
		preview_img.data('idx',idx);
        imgurl = $(event.target).parent('form').first('[name=imgurl]')
        imgurl.setValue(imgs[0].url);
	}
}

function show_categories(event,url){
    if( !$('info-div').visible() ){
        $('categories').load(url);
    }
    $('info-div').fade();
}

function show_toolbar(e){
    if($(e.target).hasClass('img-frame'))
          $(e.target).first(".paster-toolbar").fade('in');
      else
          e.stop();
}
function hide_toolbar(e){
    if($(e.target).hasClass('img-frame'))
        $(e.target).first(".paster-toolbar").fade('out');
    else
        e.stop();
}
$(document).onReady(function(){
	$$('ul.horizontal-menu').each(function(element, i){
		element.children('li')
			.each('onClick',function(event){
				$$('ul.submenu-current').each(function(e,i){
					e.setStyle('display','none');;
					e.toggleClass('submenu-current');
					});
				element = event.target.parent('li');
				if(element.first('ul')){
					submenu = element.first('ul');
					submenu.toggleClass('submenu-current');
					submenu.setStyle('display','inline');
					submenu.highlight();
				}
			});
	});
});
