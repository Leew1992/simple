define([
	'controllers/controllers',
	'bootstrap',
	'ztree',
	'datetimepicker',
	'bootstrapTable',
	'jqueryTmpl',
	'jqueryTreeTable'
], function (controllers) {
    'use strict';
    controllers.controller('MenuListController', ['$scope', '$http', 'CommonService', function ($scope, $http, CommonService) {
    	
    	// 定义全局变量
    	var idSystem = "0", $systemTree = $("#systemTree");
    	
    	/*---------------菜单分组------------------*/
    	// 菜单列表刷新回调函数
    	var callback = function(event, treeId, treeNode) {
    		idSystem = treeNode.id;
    		treeMenuOptions.data.idSystem = treeNode.id;
    		$.ajax(treeMenuOptions);
    	}
    	
    	// 渲染系统树
    	var renderSystemTree = function() {
    		var url = "system/getSystemTree.do";
    		var setting = CommonService.noEventsParamsSetting(false,url,CommonService.dataFilter,callback);
    		$systemTree = $.fn.zTree.init($systemTree, setting);    		
    	}
    	
    	// 执行渲染
    	renderSystemTree();
    	
    	/*---------------菜单列表------------------*/
    	var treeMenuOptions = {
    		url : 'menu/listMenusByIdSystem.do',
    		type : 'POST',
    		async : false,
    		data : {idSystem: "0"},
    		dataType : 'json',
    		success : function(res) {
    			$('#menuTable tr [type="checkbox"]').parent().remove();
    			$('#tBodyMenu').empty();
    			$("#menuList").tmpl(res).appendTo('#tBodyMenu');
    			var option = {
    				theme:'vsStyle',
    				expandLevel : 99,
    				beforeExpand : function($treeTable, id) {
    					
    				},
    				onSelect : function($treeTable, id) {
    					window.console && console.log('onSelect:' + id);
    				}
    			};
    			option.theme = 'default';
    			$('#menuTable').treeTable(option);
    			CommonService.initTableCheckBox();
    			
    			// 新增子菜单
    			$(".addSubMenu").click(function(e) {
    				var idMenu = $(e.target).parents('tr').attr('id');
    				var idParent = $(e.target).parents('tr').attr('pid');
    				window.location.href = "#/menu/menuForm?operateType=addSubMenu&idMenu=" + idMenu + "&idParent=" + idMenu;
    			});
    			// 编辑子菜单
    			$(".editMenu").click(function(e) {
    				var idMenu = $(e.target).parents('tr').attr('id');
    				var idParent = $(e.target).parents('tr').attr('pid');
    				window.location.href = "#/menu/menuForm?operateType=editMenu&idMenu=" + idMenu + "&idParent=" + idParent;
    			});
    			// 删除子菜单
    			$(".deleteMenu").click(function(e) {
    				CommonService.confirm(function(result) {
    					if(result) {
    						deleteMenuOptions.data.idMenus = $(e.target).parents('tr').attr('id');
    						$.ajax(deleteMenuOptions);						
    					}
    				});
    			});
    		}
    	};
    	
    	// 渲染菜单树
    	$.ajax(treeMenuOptions);
    	
    	// 启用菜单
    	var enabledOptions = {
    		url : 'menu/batchEnableMenus.do',
    		type : 'POST',
    		async : false,
    		data : {},
    		dataType : 'json',
    		success : function(data) {
    			CommonService.alert(data.msg,function() {
    				$.ajax(treeMenuOptions);
    			});
    		}
    	};
    	
    	// 停用菜单
    	var disabledOptions = {
    		url : 'menu/batchDisableMenus.do',
    		type : 'POST',
    		async : false,
    		data : {},
    		dataType : 'json',
    		success : function(data) {
    			CommonService.alert(data.msg,function() {
    				$.ajax(treeMenuOptions);
    			});
    		}
    	};
    	
    	// 保存菜单
    	var saveMenuOptions = {
    		url : 'menu/saveMenu.do',
    		type : 'POST',
    		async : false,
    		data : {},
    		dataType : 'json',
    		success : function(data) {
    			$("#menuModal").modal('hide');
    			CommonService.alert(data.msg,function() {
    				$.ajax(treeMenuOptions);
    			});
    		}
    	};
    	
    	// 添加子菜单
    	var saveSubMenuOptions = {
    		url : 'menu/saveSubMenu.do',
    		type : 'POST',
    		async : false,
    		data : {},
    		dataType : 'json',
    		success : function(data) {
    			$("#menuModal").modal('hide');
    			CommonService.alert(data.msg,function() {
    				$.ajax(treeMenuOptions);
    			});
    		}
    	};
    	
    	// 更新菜单
    	var updateMenuOptions = {
    		url : 'menu/updateMenu.do',
    		type : 'POST',
    		async : false,
    		data : {},
    		dataType : 'json',
    		success : function(data) {
    			$("#menuModal").modal('hide');
    			CommonService.alert(data.msg,function() {
    				$.ajax(treeMenuOptions);
    			});
    		}
    	};
    	
    	// 删除菜单
    	var deleteMenuOptions = {
    		url : 'menu/deleteMenuById.do',
    		type : 'POST',
    		async : false,
    		data : {},
    		dataType : 'json',
    		success : function(data) {
    			CommonService.alert(data.msg,function() {
    				$.ajax(treeMenuOptions);
    			});
    		}
    	};
    	
    	// 保存菜单事件
    	$("#confirm").click(function() {
    		// 保存根菜单
    		if(!$("#idMenu").val() && $("#idParent").val()=='0') {
    			var params = $("#menuForm").serialize()+"&idSystem="+idSystem;
    			saveMenuOptions.data = params;
    	    	$.ajax(saveMenuOptions);
    		}
    		// 保存子菜单
    		if(!$("#idMenu").val() && $("#idParent").val()!='0') {
    			var params = $("#menuForm").serialize();
    			saveSubMenuOptions.data = params;
    			$.ajax(saveSubMenuOptions);										
    		}
    		// 更新菜单
    		if($("#idMenu").val() && $("#idParent").val()) {
    			var params = $("#menuForm").serialize()+"&idSystem="+idSystem;
    			updateMenuOptions.data = params;
    	    	$.ajax(updateMenuOptions);
    		}
    	});
    	
    	// 隐藏模态框
    	$("#myModal").on("hidden.bs.modal", function() {
    		$("#myModal :input").val('');
    	});
    	
    	// 清空输入框
    	$("#menuModal").on("hidden.bs.modal", function() {
    		$("#menuModal :input").val('');
    	});
    	
    	// 新增菜单
    	var addMenu = function() {
    		$("#menuLabel").html("添加根菜单");
    		$("#idMenu").attr("disabled", "disabled");
    		$("#roleForm input").val('');
    		$("#roleForm textarea").val('');
    		$("#idParent").val('0');
    		$("#menuModal").modal('show');    		
    	}
    	
    	// 启用菜单
    	var enabled = function() {
    		var checkStr = [];
        	$("#tBodyMenu input:checkbox:checked").each(function(element) {
        		checkStr.push($(this).parents('tr').attr('id'));
        	});
        	if(checkStr.length == 0) {
        		CommonService.alert("请选择启用的菜单！",function() {
    				$.ajax(treeMenuOptions);
    			});
        		return;
        	}
        	enabledOptions.data.idMenus = checkStr.join(',');
        	$.ajax(enabledOptions);
    	}
    	
    	// 禁用菜单
    	var disabled = function() {
    		var checkStr = [];
        	$("#tBodyMenu input:checkbox:checked").each(function(element) {
        		checkStr.push($(this).parents('tr').attr('id'));
        	});
        	if(checkStr.length == 0) {
        		CommonService.alert("请选择停用的菜单！",function() {
    				$.ajax(treeMenuOptions);
    			});
        		return;
        	}
        	disabledOptions.data.idMenus = checkStr.join(',');
        	$.ajax(disabledOptions);
    	}
    }]);
});
