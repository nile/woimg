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

        //div.setStyle('left',( (minheightIdx)*190 ) + 'px');
        //div.setStyle('top', ( minheight ) +'px');
        var datahash= div.get('data-hash');
        var img = div.first('#'+datahash);
        img.set('src',div.get('data-img-url'));
        div.on({
                 mouseenter: show_toolbar,
                 mouseleave: hide_toolbar
                 });
        heights[minheightIdx] = heights[minheightIdx]+ inc;
        return minheightIdx;
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
                            var idx = _create_img_paster(div, heights)  ;
                            $("container").first('#c'+idx).append(div);
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
                             var idx = _create_img_paster(div, heights)  ;
                             $("container").first('#c'+idx).append(div);
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
                                    var idx = _create_img_paster(div, heights)  ;
                                    $("container").first('#c'+idx).append(div);
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
            new Lightbox({showButtons:false, showTitle: false}).load(this.options.repaste_url,{params:{'hash':hash}});
        },
        quick_comment: function (e, id){
            if(e.keyCode == 13){
                var val = $(e.target).getValue();
                if(val.length > 0){
                     var comments_url = this.options.quick_comments_url;
                     var xhr = new Xhr(this.options.comment_url,{
                           method: 'post',
                           onSuccess: function (){
                               $(e.target).parent('.img-frame').first('#comments').load(comments_url,{params:{'hash':id}})
                           }
                     });
                     xhr.send({"hash": id, "comment": val});
                }
            }
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

