CKEDITOR.plugins.add( 'words', {
    requires : [ 'richcombo' ],
    init : function( editor ) {
        var config = editor.config
        ,   lang   = editor.lang.format
        ;

        var commonlyUsedWords = [];
        // commonlyUsedWords == [value, drop_text, drop_label]
        commonlyUsedWords[0]=["内容生动丰富，语言新颖清爽，结构独特合理。", "内容生动丰富，语言新颖清爽，结构独特合理。", "内容生动丰富，语言新颖清爽，结构独特合理。"];
        commonlyUsedWords[1]=["文章真实生动，情真意切，极具感染力。", "文章真实生动，情真意切，极具感染力。", "文章真实生动，情真意切，极具感染力。"];
        commonlyUsedWords[2]=["主题凝炼、集中，议论深刻。", "主题凝炼、集中，议论深刻。", "主题凝炼、集中，议论深刻。"];
        commonlyUsedWords[3]=["小学生能有这样的体会，令人刮目相看。", "小学生能有这样的体会，令人刮目相看。", "小学生能有这样的体会，令人刮目相看。"];
        commonlyUsedWords[4]=["文章语言通俗易懂，贴近生活实际，读来令人倍感亲切。", "文章语言通俗易懂，贴近生活实际，读来令人倍感亲切。", "文章语言通俗易懂，贴近生活实际，读来令人倍感亲切。"];
        commonlyUsedWords[5]=["文章蕴含着朴素而深刻的哲理，读来令人精神振奋。", "文章蕴含着朴素而深刻的哲理，读来令人精神振奋。", "文章蕴含着朴素而深刻的哲理，读来令人精神振奋。"];
        commonlyUsedWords[6]=["文章语言清新，内容丰富生动、详略得当，不失为一篇佳作。", "文章语言清新，内容丰富生动、详略得当，不失为一篇佳作。", "文章语言清新，内容丰富生动、详略得当，不失为一篇佳作。"];
        commonlyUsedWords[7]=["全文清新秀逸，亲切委婉，朴素而不落俗套，值得借鉴。", "全文清新秀逸，亲切委婉，朴素而不落俗套，值得借鉴。", "全文清新秀逸，亲切委婉，朴素而不落俗套，值得借鉴。"];
        commonlyUsedWords[8]=["文章有详有略，言之有序，内容生动具体，值得一读。", "文章有详有略，言之有序，内容生动具体，值得一读。", "文章有详有略，言之有序，内容生动具体，值得一读。"];
        commonlyUsedWords[9]=["文章内容新颖，结构合理，流畅连贯，自然通达，细节描写颇具匠心，极富功底。", "文章内容新颖，结构合理，流畅连贯，自然通达，细节描写颇具匠心，极富功底。", "文章内容新颖，结构合理，流畅连贯，自然通达，细节描写颇具匠心，极富功底。"];
        commonlyUsedWords[10]=["语言虽然并不华丽，但却极为准确生动，情感丰富而真实，读来津津有味。", "语言虽然并不华丽，但却极为准确生动，情感丰富而真实，读来津津有味。", "语言虽然并不华丽，但却极为准确生动，情感丰富而真实，读来津津有味。"];
        commonlyUsedWords[11]=["文章的“读”与“感”联系紧密，事例叙述生动，打动人心，具有真情实感。", "文章的“读”与“感”联系紧密，事例叙述生动，打动人心，具有真情实感。", "文章的“读”与“感”联系紧密，事例叙述生动，打动人心，具有真情实感。"];
        commonlyUsedWords[12]=["全文语言流畅，行文舒展自如，自然洒脱，称得上是一篇较成功的作品。", "全文语言流畅，行文舒展自如，自然洒脱，称得上是一篇较成功的作品。", "全文语言流畅，行文舒展自如，自然洒脱，称得上是一篇较成功的作品。"];
        commonlyUsedWords[13]=["语言朴素，中心明确，构思合理，行文层次清楚。", "语言朴素，中心明确，构思合理，行文层次清楚。", "语言朴素，中心明确，构思合理，行文层次清楚。"];
        commonlyUsedWords[14]=["你的文章太棒了，写得太精彩了！", "你的文章太棒了，写得太精彩了！", "你的文章太棒了，写得太精彩了！"];
        commonlyUsedWords[15]=["没想到啊，你的写作水平这么高，有什么秘诀，教教我。", "没想到啊，你的写作水平这么高，有什么秘诀，教教我。", "没想到啊，你的写作水平这么高，有什么秘诀，教教我。"];
        commonlyUsedWords[16]=["文采斐然，值得我学习。", "文采斐然，值得我学习。", "文采斐然，值得我学习。"];
        commonlyUsedWords[17]=["你的文章比较空洞哦，还需要丰富一下内容才更好。", "你的文章比较空洞哦，还需要丰富一下内容才更好。", "你的文章比较空洞哦，还需要丰富一下内容才更好。"];
        commonlyUsedWords[18]=["我想给你100分，担心你会骄傲，99分吧，继续努力。", "我想给你100分，担心你会骄傲，99分吧，继续努力。", "我想给你100分，担心你会骄傲，99分吧，继续努力。"];
        commonlyUsedWords[19]=["看好你，一定会成为大作家。", "看好你，一定会成为大作家。", "看好你，一定会成为大作家。"];
        commonlyUsedWords[20]=["文章能写成这样，已经有很大进步，加油！", "文章能写成这样，已经有很大进步，加油！", "文章能写成这样，已经有很大进步，加油！"];
        commonlyUsedWords[21]=["喜欢你的文章，还想拜读你的新作品。", "喜欢你的文章，还想拜读你的新作品。", "喜欢你的文章，还想拜读你的新作品。"];
        commonlyUsedWords[22]=["不错不错，总是看到你在进步。", "不错不错，总是看到你在进步。", "不错不错，总是看到你在进步。"];
        commonlyUsedWords[23]=["这篇文章看上去不像你写的，希望我是小看人了。", "这篇文章看上去不像你写的，希望我是小看人了。", "这篇文章看上去不像你写的，希望我是小看人了。"];
        
        editor.ui.addRichCombo( 'Words',
        {
            label : "快速评论",
            title :"通过选择评论组中评论项快速输入您需要的评论内容",
            voiceLabel : "通过选择评论组中评论项快速输入您需要的评论内容",
            className : 'cke_format',
            multiSelect : false,
            panel :
            {
                // Create style objects for all defined styles.
                css : [ config.contentsCss, CKEDITOR.getUrl( editor.skinPath + 'editor.css' ) ],
                voiceLabel : lang.panelVoiceLabel
            },
            init : function()
            {
                this.startGroup( "默认组" );
                // Gets the list of tags from the settings.
                //this.add('value', 'drop_text', 'drop_label');
                for (var this_commonlyUsedWord in commonlyUsedWords){
                    this.add(commonlyUsedWords[this_commonlyUsedWord][0], commonlyUsedWords[this_commonlyUsedWord][1], commonlyUsedWords[this_commonlyUsedWord][2]);
                }
                /*
                this.startGroup( "快速评论组2" );
                // Gets the list of tags from the settings.
                //this.add('value', 'drop_text', 'drop_label');
                for (var this_commonlyUsedWord in commonlyUsedWords){
                    this.add(commonlyUsedWords[this_commonlyUsedWord][0], commonlyUsedWords[this_commonlyUsedWord][1], commonlyUsedWords[this_commonlyUsedWord][2]);
                }
                */
            },
            onClick : function( value )
            {         
                //editor.focus();
                //editor.fire( 'saveSnapshot' );
                editor.insertHtml(value);
                //editor.fire( 'saveSnapshot' );
            }
        });
    }
} );