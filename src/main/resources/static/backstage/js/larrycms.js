var navtab;
var reload_;
layui.config({
	base: 'backstage/js/lib/' //layui自定义layui组件目录
}).extend({
	larry: 'larry',
	navtab: 'navtab',
	elemnts: 'elements',
	common: 'common'
});
layui.use(['elements', 'jquery', 'layer', 'larry', 'navtab', 'form', 'common'], function () {
	var $ = layui.jquery,
	layer = layui.layer,
	device = layui.device(),
	elements = layui.elements(),
	larry = layui.larry(),
	form = layui.form(),
	common = layui.common;
	navtab = layui.navtab({
		elem: '#larry-tab'
	});

	// 页面禁止双击选中
	$('body').bind("selectstart", function () {
		return false;
	});

	$(document).ready(function () {
		// 浏览器兼容检查
		if (device.ie && device.ie < 9) {
			layer.alert('最低支持ie9，您当前使用的是古老的 IE' + device.ie + '！');
		}
		
		// 001界面初始化
		AdminInit();
		//绑定导航数据
		/*$.ajaxSettings.async = false;
        $.getJSON('../backstage/datas/top_menu.json?t=' + new Date(), {
            Param: 'index_menu'
        }, function (result) {
            larry.set({
                elem: '#menu',
                data: result,
                cached: false
            });
            larry.render();
        });*/

		var result = [
		              {
		            	  "title": "系统管理",
		            	  "icon": "larry-xitongshezhi1",
		            	  "pid": "0"
		              },
		              {
		            	  "title": "内容管理",
		            	  "icon": "larry-neirongguanli",
		            	  "pid": "35"
		              },
		              {
		            	  "title": "微信公众",
		            	  "icon": "larry-weixingongzhongpingtai",
		            	  "pid": "40"
		              },
		              {
		            	  "title": "扩展模块",
		            	  "icon": "larry-ht_expand",
		            	  "pid": "46"
		              },
		              {
		            	  "title": "例子模块",
		            	  "icon": "larry-ht_expand",
		            	  "pid": "47"
		              }];
		var type = $("input[name='type_'").val();
		if(type == 3){//治疗师菜单项加载
			result = [{
          	  "title": "业务管理",
          	  "icon": "larry-xitongshezhi1",
          	  "pid": "1"
            }];
		}
		if(type == 1){//主治医师菜单加载
			result = [{
          	  "title": "业务管理",
          	  "icon": "larry-xitongshezhi1",
          	  "pid": "2"
            }];
		}
		if(type == 2){//评估师菜单加载
			result = [{
          	  "title": "业务管理",
          	  "icon": "larry-xitongshezhi1",
          	  "pid": "3"
            }];
		}
		if(type == 0){//治疗师菜单项加载
			result = [{
          	  "title": "业务管理",
          	  "icon": "larry-xitongshezhi1",
          	  "pid": "4"
            }];
		}
		larry.set({
			elem: '#menu',
			data: result,
			cached: false
		});
		larry.render();

		var $menu = $('#menu');
		$menu.find('li.layui-nav-item').each(function () {
			var $that = $(this);
			//绑定一级导航的点击事件
			$that.on('click', function () {
				var id = $that.data('pid');
				/*$.ajaxSettings.async = false;

                $.getJSON('../backstage/datas/pid_' + id + '.json?t=' + new Date(), {
                    pid: id,
                    Param: 'index_menu'
                }, function (result) {
                    larry.set({
                        elem: '#larrySideNav',
                        data: result,
                        spreadOne: true
                    });
                    larry.render();
                    //监听左侧导航点击事件
                    larry.on('click(side)', function (data) {
                        navtab.tabAdd(data.field);
                    });
                });*/
				var params = {};
				
				params.pid_0 = [{"pid":"101","title":"我的面板","icon":"larry-gerenxinxi5","spread":"true","children":[{"title":"个人信息","icon":"larry-gerenxinxi1","href":"html/personInfo.html"},{"title":"修改密码","icon":"larry-xiugaimima2","href":"html/changepwd.html"},{"title":"日志信息","icon":"larry-rizhi2","href":"html/myloginfo.html"}]},{"pid":"102","title":"用户管理","icon":"larry-10103","spread":"true","children":[{"title":"用户列表","icon":"larry-yonghuliebiao1","href":"html/main.html"},{"title":"角色列表","icon":"larry-jiaoseguanli1","href":"html/temp.html"},{"title":"菜单管理","icon":"larry-caidanguanli","href":"html/temp.html"}]},{"pid":"103","title":"会员管理","icon":"larry-huiyuanguanli2","spread":"true","children":[{"title":"会员注册列表","icon":"larry-kehuliebiao","href":"html/temp.html"},{"title":"会员留言管理","icon":"larry-liuyan","href":"html/temp.html"},{"title":"会员等级管理","icon":"larry-fengxianbaozhang","href":"html/temp.html"}]},{"pid":"104","title":"系统设置","icon":"larry-xitongshezhi1","spread":"true","children":[{"title":"系统基本参数","icon":"larry-circularxiangxi","href":"html/temp.html"},{"title":"SQL命令行工具","icon":"larry-sql2","href":"html/temp.html"},{"title":"系统日志管理","icon":"larry-xitongrizhi1","href":"html/temp.html"},{"title":"验证安全管理","icon":"larry-yanzheng","href":"html/temp.html"},{"title":"图片水印设置","icon":"larry-shuiyin","href":"html/temp.html"},{"title":"安全字典设置","icon":"larry-zidian","href":"html/temp.html"},{"title":"短信接口配置","icon":"larry-duanxin1","href":"html/temp.html"},{"title":"系统错误修复","icon":"larry-xiufu","href":"html/temp.html"},{"title":"数据库管理","icon":"larry-sql1","href":"html/temp.html"}]},{"pid":"105","title":"友链管理","icon":"larry-a157","href":"html/temp.html"}];
				params.pid_35 = [{"title":"网站管理","icon":"larry-30wangzhanguanli","spread":"true","children":[{"title":"网站栏目管理","icon":"larry-lanmuguanli","href":"html/temp.html"},{"title":"所有文章列表","icon":"larry-iconfontfilesfill","href":"html/temp.html"},{"title":"待审核的文章","icon":"larry-daishenhe1","href":"html/temp.html"},{"title":"我发布的文章","icon":"larry-fabu2","href":"html/temp.html"},{"title":"Tags管理","icon":"larry-tags","href":"html/temp.html"},{"title":"内容回收站","icon":"larry-iconfonthuishouzhan","href":"html/temp.html"}]},{"title":"模型管理","icon":"larry-moxingguanli","spread":"true","children":[{"title":"内容模型管理","icon":"larry-moxing1","href":"html/temp.html"},{"title":"单页文档管理","icon":"larry-danye","href":"html/temp.html"},{"title":"联动类别管理","icon":"larry-liandong","href":"html/temp.html"},{"title":"自由列表管理","icon":"larry-zidingyicaidan1","href":"html/temp.html"},{"title":"自定义表单","icon":"larry-iconzidingyibiaodan","href":"html/temp.html"}]},{"title":"附件管理","icon":"larry-fujianguanli","spread":"true","children":[{"title":"上传新文件","icon":"larry-shangchuanfujian","href":"html/temp.html"},{"title":"附件数据管理","icon":"larry-fujian","href":"html/temp.html"},{"title":"文件管理器","icon":"larry-wenjianguanli2","href":"html/temp.html"}]},{"title":"采集管理","icon":"larry-eee","spread":"true","children":[{"title":"采集节点管理","icon":"larry-shujucaiji","href":"html/temp.html"},{"title":"临时内容管理","icon":"larry-linshi1","href":"html/temp.html"},{"title":"导入采集规则","icon":"larry-guize","href":"html/temp.html"},{"title":"监控采集模式","icon":"larry-jiankong1","href":"html/temp.html"},{"title":"采集未下载内容","icon":"larry-xiazai2","href":"html/temp.html"}]},{"title":"广告管理","icon":"larry-iconguanggaoguanli","spread":"true","children":[{"title":"自定义广告","icon":"larry-zidingyiguanggaolan","href":"html/temp.html"},{"title":"网盟广告","icon":"larry-guanggaolianmeng","href":"html/temp.html"}]},{"title":"批量维护","icon":"larry-piliang","spread":"true","children":[{"title":"一键更新网站","icon":"larry-yijian","href":"html/temp.html"},{"title":"更新系统缓存","icon":"larry-xitonghuancun","href":"html/temp.html"},{"title":"更新主页HTML","icon":"larry-tubiaozitimoban","href":"html/temp.html"},{"title":"更新栏目HTML","icon":"larry-lanmu1","href":"html/temp.html"},{"title":"更新文档HTML","icon":"larry-wendang","href":"html/temp.html"},{"title":"更新网站地图","icon":"larry-wangzhanditu","href":"html/temp.html"},{"title":"重复文档检测","icon":"larry-zhongfu","href":"html/temp.html"},{"title":"数据库内容替换","icon":"larry-tihuan1","href":"html/temp.html"},{"title":"自动摘要","icon":"larry-zhaiyao","href":"html/temp.html"},{"title":"搜索关键词维护","icon":"larry-guanjianci","href":"html/temp.html"}]}];
				params.pid_40= [{"title":"微信配置","icon":"larry-weixinguanli","spread":"true","children":[{"title":"微信接口设置","icon":"larry-api1","href":"html/temp.html"},{"title":"微信支付配置","icon":"larry-iconzfb","href":"html/temp.html"}]}];
				params.pid_46= [{"title":"支付宝支付配置","icon":"larry-zhifubao","href":"html/temp.html"}];
				params.pid_47= [{"title":"单列表页面","icon":"larry-neirongguanli","href":"public/toDange"}
				,{"title":"多列表页面","icon":"larry-neirongguanli","href":"public/toDange"}
				,{"title":"框架文档参考","icon":"larry-neirongguanli","href":"http://www.layui.com/doc"}
				,{"title":"详情页面","icon":"larry-neirongguanli","href":"html/temp.html"}
				,{"title":"新增tab","icon":"larry-neirongguanli","href":"html/temp.html"}
				,{"title":"按钮颜色规范","icon":"larry-neirongguanli","href":"public/toBtnPage"}
				,{"title":"事件流页面","icon":"larry-neirongguanli","href":"html/temp.html"}
				,{"title":"弹出层页面","icon":"larry-neirongguanli","href":"html/temp.html"}];
				
				//治疗室菜单
				params.pid_1= [
				                {"pid":"10","title":"康复诊疗中心","icon":"larry-gerenxinxi5","spread":"true",
				                 "children":[{"title":"康复诊疗","icon":"larry-gerenxinxi1","href":"sys/kangfuIndex"},
				                             {"title":"评价会议","icon":"larry-logoshuiyin","href":"sys/major/meetingList"}
				                            /* {"title":"随访记录","icon":"larry-xiugaimima2","href":"html/personInfo.html"}*/
				                            ]
				                }
				               //,{"pid":"11","title":"框架示例","icon":"larry-logoshuiyin","spread":"true","children":[{"title":"多列表页面","icon":"larry-neirongguanli","href":"sys/major/toDange"}]}
				              ];
				//主治医生菜单
				params.pid_2= [
					            {"pid":"20","title":"康复诊疗中心","icon":"larry-gerenxinxi5","spread":"true","children":[{"title":"康复诊疗","icon":"larry-gerenxinxi1","href":"sys/major/treatment"},{"title":"评价会议","icon":"larry-logoshuiyin","href":"sys/major/meetingList"}]}
					         /*  ,{"pid":"21","title":"统计报表","icon":"larry-logoshuiyin","spread":"true","children":[]}*/
					          ];
				//评估师菜单
				params.pid_3= [{"pid":"30","title":"康复诊疗中心","icon":"larry-gerenxinxi5","spread":"true","children":[{"title":"康复诊疗","icon":"larry-gerenxinxi1","href":"sys/appraiser/kangfu"},{"title":"评价会议","icon":"larry-logoshuiyin","href":"sys/major/meetingList"}]}
					           //,{"pid":"31","title":"统计报表","icon":"larry-logoshuiyin","spread":"true","children":[]}
				//,{"title":"多列表页面","icon":"larry-neirongguanli","href":"public/toDange"}
				//,{"title":"框架文档参考","icon":"larry-neirongguanli","href":"http://www.layui.com/doc"}
				//,{"title":"按钮颜色规范","icon":"larry-neirongguanli","href":"public/toBtnPage"}
					          ];
				
				params.pid_4= [
				                {"pid":"40","title":"基础管理","icon":"larry-xiangmuguanli","spread":"true",
				                 "children":[{"title":"医生管理","icon":"larry-gereninfo","href":"sys/tdoctor/go"},
				                             {"title":"科室管理","icon":"larry-gerenxinxi1","href":"sys/bdepartment/go"},
				                             {"title":"职务管理","icon":"larry-gerenxinxi2","href":"sys/bposition/go"},
				                             {"title":"项目管理","icon":"larry-xiangmuguanli","href":"sys/tproject/go"},
				                             {"title":"项目组管理","icon":"larry-xiugai","href":"sys/tprojectgroup/go"},
				                             {"title":"科室组管理","icon":"larry-logoshuiyin","href":"sys/bdeptgroup/go"},
				                             {"title":"ICD-10管理","icon":"larry-zidingyicaidan1","href":"sys/bdisicd10/go"},
				                             {"title":"项目疾病管理","icon":"larry-caiji","href":"sys/tprojectdis/go"},
				                             {"title":"评定量表管理","icon":"larry-dms","href":"sys/tgauge/go"},
				                             {"title":"常用地址管理","icon":"larry-iconguanggaoguanli","href":"sys/bmeetingaddress/go"},
				                            ]
				                }
				               ,{"pid":"41","title":"业务管理","icon":"larry-logoshuiyin","spread":"true",
				            	 "children":[{"title":"短信记录","icon":"larry-neirongguanli","href":"sys/ssms/go"},
				            	             {"title":"病患管理","icon":"larry-gerenxinxi1","href":"sys/rusers/treatment"}
				            	            ]
				                }
				              ];
				
				var result = params["pid_"+id];
				
				larry.set({
					elem: '#larrySideNav',
					data: result,
					spreadOne: true
				});
				larry.render();
				//监听左侧导航点击事件
				larry.on('click(side)', function (data) {
					var datas = data.field;
					navtab.tabAdd(datas);
				});
			});
		});
		// 左侧导航点击事件
		$menu.find('li')[0].click();
		//展开第一个菜单栏
		$(".layui-nav-tree  > li")[0].click();
		
		//默认第一个选中
		//$("#larrySideNav").find("li").eq(0).addClass('layui-this');
		$.ajaxSettings.async = true;


	});

	$('#larry-tab').bind("contextmenu", function () {
		return false;
	});

	// 常用操作
	$('#buttonRCtrl').find('dd').each(function () {

		$(this).on('click', function () {

			var eName = $(this).children('a').attr('data-eName');
			navtab.tabCtrl(eName);
		});
	});
	$('.lis').each(function () {
		$(this).on('click', function () {
			var eName = $(this).attr('data-target');
			if(eName == "refreshCur"){
				reload_();
			}
			navtab.tabCtrl(eName);
		});
	});
	// 窗口自适应    
	$(window).on('resize', function () {
		AdminInit();
		// iframe窗口自适应
		var $content = $('#larry-tab .layui-tab-content');
		//old 153
		$content.height($(this).height() - 106);
		$content.find('iframe').each(function () {
			$(this).height($content.height());
		});
	}).resize();
	//刷新

	// 刷新iframe
	$("#refresh_iframe").click(function () {
		$(".layui-tab-content .layui-tab-item").each(function () {
			if ($(this).hasClass('layui-show')) {
				$(this).children('iframe')[0].contentWindow.location.reload(true);
			}
		});
	});

	function AdminInit() {
		$('.layui-layout-admin').height($(window).height());
		$('body').height($(window).height());
		$('#larry-body').width($('.layui-layout-admin').width() - $('#larry-side').width());
		$('#larry-footer').width($('.layui-layout-admin').width() - $('#larry-side').width());
	}
	reload_ = function(){
		$(".layui-tab-content .layui-tab-item").each(function () {
			if ($(this).hasClass('layui-show')) {
				$(this).children('iframe')[0].contentWindow.location.reload(true);
			}
		});
	};
	//清除缓存
	$('#clearCached').on('click', function () {
		larry.cleanCached();
		layer.alert('缓存清除完成!本地存储数据也清理成功！', {icon: 1, title: '系统提示'}, function () {
			location.reload();//刷新
		});
	});

	// 设置主题
	var fScreen = localStorage.getItem("fullscreen_info");
	var themeName = localStorage.getItem('themeName');
	if (themeName) {
		$("body").attr("class", "");
		$("").addClass("larryTheme-" + themeName);
	}
	if (fScreen && fScreen != 'false') {
		var fScreenIndex = layer.alert('按ESC退出全屏', {
			title: '进入全屏提示信息',
			skin: 'layui-layer-lan',
			closeBtn: 0,
			anim: 4,
			offset: '100px'
		}, function () {
			entryFullScreen();
			$('#FullScreen').html('<i class="larry-icon larry-quanping"></i>退出全屏');
			layer.close(fScreenIndex);
		});
	}
	$('#larryTheme').on('click', function () {
		var fScreen = localStorage.getItem('fullscreen_info');
		var themeName = localStorage.getItem('themeName');
		layer.open({
			type: 1,
			title: false,
			closeBtn: true,
			shadeClose: false,
			shade: 0.35,
			area: ['450px', '300px'],
			isOutAnim: true,
			resize: false,
			anim: Math.ceil(Math.random() * 6),
			content: $('#LarryThemeSet').html(),
			success: function (layero, index) {
				if (fScreen && fScreen != 'false') {
					$("input[lay-filter='fullscreen']").attr("checked", "checked");
				}
				if (themeName) {
					$("#themeName option[value='" + themeName + "']").attr("selected", "selected");
				}
				form.render();
			}
		});

		// 全屏开启
		form.on('switch(fullscreen)', function (data) {
			var fValue = data.elem.checked;
			localStorage.setItem('fullscreen_info', fValue); //fullscreen_info:fValue

		});
		// 主题设置
		form.on('select(larryTheme)', function (data) {
			var themeValue = data.value;
			localStorage.setItem('themeName', themeValue);//themeName:themeValue
			if (themeName) {
				$("body").attr("class", "");
				$("body").addClass("larryTheme-" + themeName);
			}
			form.render('select');
		});

		// form.on('submit(submitlocal)',function(data){

		// })
	});


	// 全屏切换
	$('#FullScreen').bind('click', function () {
		var fullscreenElement =
			document.fullscreenElement ||
			document.mozFullScreenElement ||
			document.webkitFullscreenElement;
		if (fullscreenElement == null) {
			entryFullScreen();
			$(this).html('<i class="larry-icon larry-quanping"></i>退出全屏');
		} else {
			exitFullScreen();
			$(this).html('<i class="larry-icon larry-quanping"></i>全屏');
		}
	});
	var rightIndex = 0;

	$("html").click(function(){
		layer.close(rightIndex);
	});
	$(document).on("mouseover", ".larry-iframe",function(){
		if(rightIndex != null && typeof(rightIndex) != "undefined"){
			layer.close(rightIndex);
		}
	});
	//右键需要做点什么东西
	$(document).on("mousedown", "#layui-tab-title li", function(e){ 
		layer.close(rightIndex);
//		if($(this).attr("lay-id") == 0){
//			return;
//		}
		$(this)[0].click();
		if(3 == e.which){
//			console.log(e);
			rightIndex = layer.open({
				type: 1,
				shade: false,
				shadeClose :true,
				closeBtn:0,
				offset: [(e.pageY)+'px', e.pageX+'px'],
				title: false, //不显示标题
				content: $('#rightMenu') //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
			});
		}
	}); 


	// 进入全屏：
	function entryFullScreen() {
		var docE = document.documentElement;
		if (docE.requestFullScreen) {
			docE.requestFullScreen();
		} else if (docE.mozRequestFullScreen) {
			docE.mozRequestFullScreen();
		} else if (docE.webkitRequestFullScreen) {
			docE.webkitRequestFullScreen();
		}
	}

	// 退出全屏
	function exitFullScreen() {
		var docE = document;
		if (docE.exitFullscreen) {
			docE.exitFullscreen();
		} else if (docE.mozCancelFullScreen) {
			docE.mozCancelFullScreen();
		} else if (docE.webkitCancelFullScreen) {
			docE.webkitCancelFullScreen();
		}
	}

	// 顶部左侧导航控制开关
	$('#toggle').click(function () {
		var sideWidth = $('#larry-side').width();
		var bodyW = $('#larry-body').width();
		if (sideWidth === 200) {
			if(!$("#larry-body").is(":animated")){
				bodyW += 203;
				$('#larry-body').animate({
					left: '0',
					width: bodyW
				});
				$('#larry-footer').animate({
					left: '0',
					width: bodyW
				});
				$('#larry-side').animate({
					width: '0'
				});
			}
		} else {
			if(!$("#larry-body").is(":animated")){
				bodyW -= 203;
				$('#larry-body').animate({
					left: '203px',
					width: bodyW
				},{queue:true});
				$('#larry-footer').animate({
					left: '203px',
					width: bodyW
				},{queue:true});
				$('#larry-side').animate({
					width: '200px'
				},{queue:true});
			}

		}
	});
	// 锁屏控制
//	$('#lock').mouseover(function () {
//	layer.tips('请按Alt+L快速锁屏！', '#lock', {
//	tips: [1, '#FF5722'],
//	time: 2000
//	});
//	});
	// 快捷键锁屏设置
	$(document).keydown(function (e) {
		if (e.altKey && e.which == 76) {
			lockSystem();
		}
	});
	checkLockStatus('0');
	// 锁定屏幕
	function lockSystem() {
		/*var url = '../backstage/datas/lock.json';
        $.get(
            url,
            function (data) {
                if (data == '1') {
                    checkLockStatus(1);
                } else {
                    layer.alert('锁屏失败，请稍后再试！');
                }
            });*/
		checkLockStatus(1);
		startTimer();
	}

	//解锁屏幕
	function unlockSystem() {
		// 与后台交互代码已移除，根据需求定义或删除此功能

		checkLockStatus(0);
	}

	// 点击锁屏
	$('#lock').click(function () {
		lockSystem();
	});
	// 解锁进入系统
	$('#unlock').click(function () {
		unlockSystem();
	});
	// 监控lock_password 键盘事件
	$('#lock_password').keypress(function (e) {
		var key = e.which;
		if (key == 13) {
			unlockSystem();
		}
	});

	function startTimer() {
		var today = new Date();
		var h = today.getHours();
		var m = today.getMinutes();
		var s = today.getSeconds();
		m = m < 10 ? '0' + m : m;
		s = s < 10 ? '0' + s : s;
		$('#time').html(h + ":" + m + ":" + s);
		t = setTimeout(function () {
			startTimer();
		}, 500);
	}

	// 锁屏状态检测
	function checkLockStatus(locked) {
		// 锁屏
		if (locked == 1) {
			$('.lock-screen').show();
			$('#locker').show();
			$('#layui_layout').hide();
			$('#lock_password').val('');
		} else {
			$('.lock-screen').hide();
			$('#locker').hide();
			$('#layui_layout').show();
		}
	}
	//TODO
	$(document).on("contextmenu", ".layui-layer-content",function(event){
		event.cancelBubble = true;
		event.returnValue = false; 
		return false; 
	});

	$('#dianzhan').click(function (event) {
		layer.open({
			type: 1,
			title: false,
			closeBtn: true,
			shadeClose: false,
			shade: 0.15,
			area: ['505px', '288px'],
			content: '<img src="images/dianzhan.jpg"/>'
		});
	});
	// 登出系统
	$('#logout').on('click', function () {
		var url = window.location.href.split("/sys")[0];
		common.logOut('退出登陆提示！', '你真的确定要退出系统吗？', url);
	});
	
	
	// 左侧导航菜单控制
	// $('#larrySideNav').on('click', function() {
	// 	if($('#larrySideNav .layui-this').length>0){
	//     $('.sys-public-menu .layui-nav li').removeClass('layui-this');
	// 	}
	// });
	// $('.sys-public-menu .layui-nav li').on('click',function(){
	//     $('#larrySideNav .layui-this').removeClass('layui-this');
	// });
});