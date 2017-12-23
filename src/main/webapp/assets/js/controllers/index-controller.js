define([ 
	'controllers/controllers',
	'Chart'
], function(controllers, Chart) {
	'use strict';
	controllers.controller('IndexController', [ '$scope', '$timeout', 'CommonService', function($scope, $timeout, CommonService) {
		
		// 初始化菜单数据
		$scope.menus = {
			index: [ {menuUrl: '#/index/dashboard', menuName: '看板'}, {menuUrl: '#/index/report', menuName: '报表'}, {menuUrl: '#/index/config', menuName: '配置'} ],
			user: [ {menuUrl: '#/user/userList', menuName: '用户列表'}, {menuUrl: '#/user/userForm', menuName: '用户添加'}, {menuUrl: '#/user/userAuth', menuName: '用户授权' } ],
			role: [ {menuUrl: '#/role/roleList', menuName: '角色列表'}, {menuUrl: '#/role/roleForm', menuName: '角色添加'}, {menuUrl: '#/role/roleAuth', menuName: '角色授权' } ],
			menu: [ {menuUrl: '#/menu/menuList', menuName: '菜单列表'}, {menuUrl: '#/menu/menuForm', menuName: '菜单添加'}, {menuUrl: '#/menu/menuAuth', menuName: '菜单授权' } ],
			post: [ {menuUrl: '#/post/postList', menuName: '贴子列表'}, {menuUrl: '#/post/postForm', menuName: '贴子添加'}, {menuUrl: '#/post/postAuth', menuName: '贴子授权' } ],
			comment: [ {menuUrl: '#/comment/commentList', menuName: '评论列表'}, {menuUrl: '#/comment/commentForm', menuName: '评论添加'}, {menuUrl: '#/comment/commentAuth', menuName: '评论授权' } ],
			config: [ {menuUrl: '#/config/groupConfig', menuName: '分组配置'}, {menuUrl: '#/config/systemConfig', menuName: '系统配置'}, {menuUrl: '#/config/columnConfig', menuName: '栏目配置' }, {menuUrl: '#/config/categoryConfig', menuName: '类别配置' } ]
		};
		
		// 动态切换模块
		$scope.switchModule = function(modName, menuUrl) {
			// 获取模块编码
			if(modName == 'index') {
				$scope.menuItems = $scope.menus.index;
			}
			if(modName == 'user') {
				$scope.menuItems = $scope.menus.user;
			}
			if(modName == 'role') {
				$scope.menuItems = $scope.menus.role;
			}
			if(modName == 'menu') {
				$scope.menuItems = $scope.menus.menu;
			}
			if(modName == 'post') {
				$scope.menuItems = $scope.menus.post;
			}
			if(modName == 'comment') {
				$scope.menuItems = $scope.menus.comment;
			}
			if(modName == 'config') {
				$scope.menuItems = $scope.menus.config;
			}
			
			// 菜单跳转
			if(validMenuIsInMod(menuUrl, $scope.menuItems) && menuUrl) {
				window.location.href = menuUrl;
			} else {
				window.location.href = $scope.menuItems[0].menuUrl;
			}
		}
		
		// 判断此模块中是否存在此菜单
		var validMenuIsInMod = function(menuUrl, menuItems) {
			for(var i in menuItems) {
				if(menuItems[i].menuUrl == menuUrl) {
					return true;
				}
			}
			return false;
		}
		
		// 初始化渲染
		var initRender = function(){
			// 首页初始化
			$scope.menuItems = $scope.menus.index;
			// 模块名称
			var modName = anchor.split("/")[1];
			// 切换模块
			$scope.switchModule(modName, anchor);
		}
		
		// 获取url后面的锚点(anchor)
		var anchor = CommonService.getUrlAnchor();
		
		// 执行渲染
		initRender();
		
		// 延迟加载（0.2秒）
		$timeout(function(){
			var myChartE = document.getElementById("myChart");
			if(myChartE) {
				var ctx = myChartE.getContext('2d');
				var myChart = new Chart(ctx, {
					type: 'line',
					data: {
						labels: ["六月", "七月", "八月", "九月", "十月", "十一月"],
						datasets: [{
							label: '访问量',
							data: [12, 19, 3, 5, 2, 3],
							backgroundColor: [
							                  'rgba(100, 100, 100, 0.2)'
							                  ],
							                  borderColor: [
							                                'rgba(100, 100, 100, 1)'
							                                ],
							                                borderWidth: 1
						}]
					},
					options: {
						scales: {
							yAxes: [{
								ticks: {
									beginAtZero:true
								}
							}]
						}
					}
				});
			}
        },200); 
		
		
	} ]);
});
