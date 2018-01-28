define([ 
	'controllers/controllers',
	'Chart'
], function(controllers, Chart) {
	'use strict';
	controllers.controller('IndexController', [ '$scope', '$http', '$timeout', 'CommonService', function($scope, $http, $timeout, CommonService) {
		
		// 获取用户信息
		var generateMenus = function() {
			var promise = $http({
    			method: 'GET',
    			url: 'portle/generateMenus.do',
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			// 初始化菜单赋权
    			$scope.menus = response.data;
    			// 菜单和模块映射
    			mappingMenusToModule();
    			// 首页初始化
    			forwardPreviousPage();
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
		}
		
		// 映射菜单到模块
		var mappingMenusToModule = function() {
			$scope.menusMap = {};
			for(var mod in $scope.menus) {
				for(var menu in $scope.menus[mod]) {
					var menuUrl = $scope.menus[mod][menu].menuUrl;
					$scope.menusMap[menuUrl] = mod;
				}
			}
		}
		
		// 获取用户信息
		var getUserInfo = function() {
			$http({
    			method: 'GET',
    			url: 'user/getCurrentUser.do',
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			$scope.currentUser = response.data;
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
		}
		
		// 动态切换模块
		$scope.changeModule = function(modName, menuUrl) {
			// 菜单跳转
			if(modName && menuUrl) {
				$scope.menuItems = $scope.menus[modName];
				window.location.href = menuUrl;
			} else if (modName && !menuUrl) {
				$scope.menuItems = $scope.menus[modName];
				window.location.href = $scope.menus[modName][0].menuUrl;			
			} else {
				$scope.menuItems = $scope.menus['index'];
				window.location.href = $scope.menus['index'][0].menuUrl;
			}
		}
		
		// 跳转到上一个页面
		var forwardPreviousPage = function() {
			// 获取锚点
			var anchor = CommonService.getUrlAnchor();
			// 模块名称
			var modName = $scope.menusMap[anchor];
			// 切换模块
			$scope.changeModule(modName, anchor);
		}
		
		// 初始化渲染
		var initRender = function(){
			// 生成菜单数据
			generateMenus();
			// 获取用户信息
			getUserInfo();
		}
		
		// 执行渲染
		initRender();
		
	} ]);
});
