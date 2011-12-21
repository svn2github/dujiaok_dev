		
/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2010, alibaba.com All rights reserved.
 * @version 1.1
 * @author shengding
 * @date 2010-2-10
 * @requires core/xui-core.js
 * @requires external/alirte/*.*
 */
 /**
 * Editor  
 * @namespace xui.widgets
 * @class Editor
 * @constructor
 * @param {String} id  ����
 * @param {Object} attrs  �������� eg��{}
 */
xui.widgets.Editor = function(id,attrs){
	this.id = id;
	this.attributes = attrs;
	/**
	*@field {Number} width  ���
	*/
	this.width =( this.attributes && this.attributes.width)?this.attributes.width:'98%';
	/**
	*@field {Number} height  �߶�
	*/
	this.height =( this.attributes && this.attributes.height)?this.attributes.height:'98%';
}
xui.widgets.Editor.prototype = {
	init:function(callback){
		tinyMCE.init({
	        // General options
			mode : "exact",
			elements : this.id,

	       // mode: "textareas", 		//�༭��ʹ�õ�����Դ����textarea
	        theme: "advanced", 		//�༭���Ļ�����������Ϊadvanced
	        skin: 'aliRTE', 			//�༭��Ƥ��aliRTE
	        //������������û�б�Ҫʱ��Ҫ����Ķ�
	        //pluginsΪ�༭�����صĲ�����ã�ɾ��plugins�еĲ�����Զ�ɾ����صİ�ť������ɾ���������İ�ť������ɾ����ع���
	        //plugins����ali��ͷ�Ĳ����ΪaliRTE���Ʋ������ص���������ں�������
	        plugins: "contextmenu,inlinepopups,paste,table,aliImage,emotions,aliFilter,aliCount,aliGroup,aliViewAndCode,aliResize,aliTracelog,aliMultiLanguage,preview",

	        //�༭����С����
	        width: this.width,
	        height: this.height,

	        // Theme options
	        //�༭�����������ã�theme_advanced_buttons1����������һ�У�theme_advanced_buttons2���������ڶ���
	        //�ڹ�������ɾ����ť������ɾ����ع��ܣ�Ʃ��ɾ��table��ť��������Ҽ��˵��н���Ȼ���Կ���������༭��񡱵Ĺ���
	        //��ť�г��ֵ�group��ťΪ������ť��ϣ�����aliGroup�������ص��������49��
	        theme_advanced_buttons1: "undo,fontselect,fontsizeselect,forecolor,backcolor,|,bold,italic,group,group,|,justifyleft,justifycenter,justifyright,|,group,group,|,charmap,emotions,|,link,table,preview",
	        theme_advanced_buttons2: "",
	        theme_advanced_toolbar_location: "top", 			//�����������ڱ༭������
	        theme_advanced_toolbar_align: "left", 			//�������а�ť���뷽ʽΪ�����
	        theme_advanced_statusbar_location: "bottom", 	//״̬�������ڱ༭���ײ�
	        //�༭�����ִ�С�Զ������ã�Ĭ��Ϊ"1,2,3,4,5,6,7"
	        //���÷���Ϊ����ʾ�İ�=��С�����硰һ��=26pt;Сһ=24pt��
	        //ע�⣺Ϊ�˱�֤��utf-8��gb2312�Ȳ�ͬ�����ҳ���ж�����������ʾ���֣�����ĺ��ֶ�����unicode���룬�磺��һ�š�Ϊ��\u4E00\u53F7��
	        theme_advanced_font_sizes: "\u4E00\u53F7=26pt;\u5C0F\u4E00=24pt;\u4E8C\u53F7=22pt;\u5C0F\u4E8C=18pt;\u4E09\u53F7=16pt;\u5C0F\u4E09=15pt;\u56DB\u53F7=14pt;\u5C0F\u56DB=12pt;\u4E94\u53F7=10pt;\u5C0F\u4E94=9pt;\u516D\u53F7=7.5pt;\u5C0F\u516D=6.5pt;\u4E03\u53F7=5.5pt;\u516B\u53F7=5pt",
	        //�༭�����������Զ������ã�Ĭ��ΪӢ�����壬��������Ļ����£���������ô���
	        //���÷���Ϊ����ʾ�İ�=���塱���硰����=simsun;����=simhei��
	        //ע�⣺Ϊ�˱�֤��utf-8��gb2312�Ȳ�ͬ�����ҳ���ж�����������ʾ���֣�����ĺ��ֶ�����unicode���룬�磺�����塱Ϊ��\u5B8B\u4F53��
	        theme_advanced_fonts: "\u5B8B\u4F53=simsun;\u9ED1\u4F53=simhei;\u6977\u4F53=\u6977\u4F53_GB2312;\u96B6\u4E66=\u96B6\u4E66;\u5E7C\u5706=\u5E7C\u5706;\u4EFF\u5B8B=\u4EFF\u5B8B_GB2312;\u5FAE\u8F6F\u96C5\u9ED1=\u5FAE\u8F6F\u96C5\u9ED1",
	        //�����Ƿ�����༭���仯��С
	        theme_advanced_resizing: true,

	        //�༭����������
	        //�������ģ�cn
	        //�������ģ�zh
	        //Ӣ�ģ�en
	        language: 'cn',

	        //aliGroup Config
	        //������ϰ�ť���������
	        //group_setΪ�������飬ÿ�������Ӧһ����ϰ�ť������ĳ��Ⱥ͹�������ť�����е�"group"����������ͬ
	        //ÿ��������iconΪ��ϰ�ť��ʾ��ͼ�꣬buttonsΪ���ܰ�ť���
	        group_set: [
				{
				    icon: '/themes/advanced/skins/aliRTE/img/icon-underline.gif',
				    buttons: 'underline,strikethrough'
				},
				{
				    icon: '/themes/advanced/skins/aliRTE/img/icon-sup.gif',
				    buttons: 'sup,sub'
				},
				{
				    icon: '/themes/advanced/skins/aliRTE/img/icon-bullist.gif',
				    buttons: 'bullist,numlist'
				},
				{
				    icon: '/themes/advanced/skins/aliRTE/img/icon-indent.gif',
				    buttons: 'indent,outdent'
				}
			],

	        //aliCount Config
	        //�༭���ַ�������
	        textTotal: 200000000,

	        //aliResize config
	        //�༭����С�仯���ã��ֱ�Ϊ���߶ȣ���С�߶ȣ������߶�
	        resize_set: {
	            maxHeight: 600,
	            minHeight: 320,
	            stepHeight: 70
	        },

	        //aliTracelog Config
	        //�༭��������ʱ���͵�tracelog
	        tracelog: 'blog_aliRTE',

	        //i18n Rewrite Config
	        //�Զ����İ����ã��������÷��������custom_i18n.docx
	        custom_i18n: {
	            'cn.aliResize': {
	                isLargest: '\u7F16\u8F91\u5668\u5DF2\u7ECF\u662F\u6700\u5927\u4E86',
	                isSmallest: '\u7F16\u8F91\u5668\u5DF2\u7ECF\u662F\u6700\u5C0F\u4E86'
	            },
	            'en.advlink_dlg': {
	                msginfo: "You can insert all links.",
	                errorMsg: "URL permission denied"
	            },
	            'cn.aliimage': {
	                validFailNotLogin: '\u60A8\u9700\u8981\u767B\u5F55\u624D\u53EF\u4EE5\u4E0A\u4F20\u56FE\u7247',
	                validFailNormal: '\u62B1\u6B49\uFF0C\u670D\u52A1\u5668\u9519\u8BEF\uFF0C\u56FE\u7247\u4E0A\u4F20\u5931\u8D25\u3002',
	                availableDomainsInfo: '\u53EA\u5141\u8BB8\u63D2\u5165\u4EE5\u4E0B\u57DF\u540D\u4E0B\u7684\u56FE\u7247\uFF08{#domain}\uFF09',
	                validFailInvalidDomain: '\u56FE\u7247\u5730\u5740\u4E0D\u5408\u6CD5\uFF0C\u8BF7\u91CD\u65B0\u8F93\u5165'
	            }
	        },

	        //table config
	        //����༭���������
	        table_default_border: 1, 					//���Ĭ�ϱ߿�
	        table_default_width: '100%', 				//���Ĭ�Ͽ��
	        table_default_class: 'aliRTE-table', 	//���Ĭ��className��û�б�Ҫ�벻Ҫ�޸�
	        table_col_limit: 12, 						//����������
	        table_row_limit: 128, 						//����������

	        //ali-image-insert config
	        //����༭ͼƬ������
	        aliimage_config: {
	          
	        allow_all: false,
	        is_ali_only: false,
	        white_list: ['*.aliui.com', '*.aliued.com'],
	        compressSize: 3 * 1024 * 1024, //���뱾��ͼƬ-��Ҫ����ѹ����ͼƬ��С
	        sizeLimitEach: 200 * 1024, 	//���뱾��ͼƬ-ѹ���������ϴ���ͼƬ��С
	        fileTypes: '', 			//���뱾��ͼƬ-�ϴ�ͼƬ������
	        fileCountLimit: 10, 			//���뱾��ͼƬ-һ���ϴ�ͼƬ������
	        //�ϴ���ַ
	        uploadUrl: 'http://110.75.194.130/upload/blog/upload.html',
	        //��֤��ַ
	        checkUrl: 'http://blog.china.alibaba.com/misc/arandaHttpCheck.htm',
	        imgPrefix: 'http://i05.c.aliimg.com/blog/upload/'
	        //ͼƬǰ׺
	        },

	        //Example content CSS (should be your site CSS)
	        //�ڱ༭���������ⲿCSS���Ա�ﵽ���������ã��ñ༭���ڵ���ʾЧ���ͷ��������ʵЧ������һ��
	        content_css: "css/content.css",

	        // Drop lists for link/image/media/template dialogs
	        template_external_list_url: "lists/template_list.js",
	        external_link_list_url: "lists/link_list.js",
	        external_image_list_url: "lists/image_list.js",
	        media_external_list_url: "lists/media_list.js",

	        // Replace values for the template plugin
	        template_replace_values: {
	            username: "Some User",
	            staffid: "991234"
	        },
			oninit : callback
	    });
	},
	/**
	 *������ݣ������Ƿ�����ʾhtmlģʽ����textģʽ
	 * @return {String}
	*/
	get:function(){
		tinyMCE.activeEditor.execCommand('beforeSubmit');
		return tinyMCE.getInstanceById(this.id).getContent();
		//return tinyMCE.editors.ta.getContent();
	},
	/** 
	 *��ȡtext����
	 * @return {String}
	*/
	getText:function(){
		
	},
	/**
	 *�������ݣ������HTMLģʽ
	 * @param {String}
	*/
	set:function(sHTML){
		tinyMCE.getInstanceById(this.id).setContent(sHTML);
	},
	/**
	 *�������ݣ������HTMLģʽ
	 * @param {String}
	*/
	insert:function(sHTML){
		//tinyMCE.getInstanceById(this.id).insertContent(sHTML);
		tinyMCE.execCommand('mceInsertContent', false, sHTML);
	},
	/**
	*�л�HTML��textģʽ
	 * @param {Boolean} isShow trueΪ��ʾHTMLģʽ��falseΪtextģʽ
	*/
	display:function(isShow){
	},
	activeEditor:function(){
		tinyMCE.activeEditor.execCommand('beforeSubmit');
	}
}