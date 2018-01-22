define([
	'controllers/controllers',
	'bootstrap',
	'ztree',
	'datetimepicker'
], function (controllers) {
    'use strict';
    controllers.controller('MenuFormController', ['$scope', '$http', 'CommonService', function ($scope, $http, CommonService) {
    	// 定义全局变量
    	var idSystem = '0', idSystem, $systemTree = $("#systemTree"), $superMenuTree = $("#superMenuTree");
    	
    	// 获取idMenu, idParent值
    	var operateType = CommonService.getQueryString("operateType");
    	var idMenu = CommonService.getQueryString("idMenu");
    	var idParent = CommonService.getQueryString("idParent");
    	
    	// 初始化上级菜单
    	var initSuperMenu = function() {
    		$http({
    			method: 'GET',
    			url: 'menu/getMenuDetailById.do?idMenu=' + idMenu,
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
				$scope.menu.parentMenuName = response.data.menuName;
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
    	}
    	
    	// 菜单编辑
    	if(operateType == "editMenu") {
    		$scope.title = "菜单编辑";
    		// 填充表单数据
    		$http({
    			method: 'GET',
    			url: 'menu/getMenuDetailById.do?idMenu=' + idMenu,
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			$scope.menu = response.data;
    			if($scope.menu.idParent == "0") {
    				$scope.menu.parentMenuName = "全部";
    			}
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
    	}
    	
    	// 添加子菜单
    	if(operateType == "addSubMenu") {
    		$scope.title = "菜单添加";
    		$scope.menu = {};
    		$scope.menu.idParent = idParent;
    		initSuperMenu();
    	}
    	
    	// 添加菜单
    	if(!operateType) {
    		$scope.title = "菜单添加";
    		$scope.menu = {};
    	}
    	
    	/*---------------菜单分组------------------*/
    	// 渲染分组树
    	var renderSystemTree = function() {
        	var url = "system/getHasCheckedSystemTreeByIdMenu.do";
    		var params = ["idMenu", idMenu];
    		var setting = CommonService.noEventsCallbackSettingForRadio(true, url, params, CommonService.dataFilter);
    		$systemTree = $.fn.zTree.init($systemTree, setting);
        }
    	
    	// 渲染上级菜单树
    	var renderSuperMenuTree = function() {
        	var url = "menu/getHasCheckedMenuTreeByIdSystemAndIdMenu.do";
    		var setting = CommonService.noEventsParamsCallbackSettingForRadio(true, url, CommonService.dataFilter);
    		$superMenuTree = $.fn.zTree.init($superMenuTree, setting);
        }
    	
    	// 打开上级菜单树窗口
    	$scope.openSuperMenuTree = function() {
    		var systemArr = $systemTree.getCheckedNodes(true);
    		if(systemArr.length == 0) {
    			CommonService.alert("请选择系统！",function() {});
    			return;
    		}
    		idSystem = systemArr[0].id;
    		$superMenuTree.setting.async.url = "menu/getHasCheckedMenuTreeByIdSystemAndIdMenu.do?idSystem="+idSystem+"&idMenu="+idParent;
    		$superMenuTree.reAsyncChildNodes(null,"refresh",false,null);
    		$("#superMenuModal").modal('show');
    	}
    	
    	// 选择上级菜单
    	$scope.selectSuperMenu = function() {
    		var superMenuArr = $superMenuTree.getCheckedNodes(true);
    		$scope.menu.idParent = superMenuArr[0].id;
    		$scope.menu.parentMenuName = superMenuArr[0].name;
    		$("#superMenuModal").modal('hide');
    		console.log(superMenuArr);
    	}
    	
    	// 执行渲染
    	renderSystemTree();
    	
    	// 执行渲染
    	renderSuperMenuTree();
    	
    	/*---------------表单填写------------------*/
    	// 保存菜单
		var saveMenu = function(params) {
			$http({
			    method: 'POST',
			    url: 'menu/saveMenu.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
						window.location.href = "#/menu/menuList";
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
						window.location.href = "#/menu/menuList";
					});
			});
		}
		
		// 更新菜单
		var updateMenu = function(params) {
			$http({
			    method: 'POST',
			    url: 'menu/updateMenu.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
						window.location.href = "#/menu/menuList";
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
						window.location.href = "#/menu/menuList";
					});	
			});
		}
		
        // 保存或更新菜单信息
        $scope.mergeMenu = function() {
			var systemArr = $systemTree.getCheckedNodes(true);
			$scope.formData = CommonService.removeExtraAttribute($scope.menu);
			$scope.formData.idSystem = systemArr[0].id;
			
			if($scope.formData.idMenu) {
				updateMenu($scope.formData);
			} else {
				saveMenu($scope.formData);
			}
        }
    }]);
});
