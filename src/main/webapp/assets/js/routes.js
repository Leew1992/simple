define(['app'], function (app) {
    'use strict';
    return app.config(['$routeProvider', function ($routeProvider) {
    	$routeProvider.when('/index/dashboard', {
			templateUrl : 'index/dashboard.do',
			controller : 'DashboardController'
		});
    	
    	// 内容管理
    	$routeProvider.when('/post/postList', {
			templateUrl : 'post/postList.do',
			controller : 'PostListController'
		});
		
		$routeProvider.when('/post/postForm', {
			templateUrl : 'post/postForm.do',
			controller : 'PostFormController'
		});
		
		$routeProvider.when('/comment/commentList', {
			templateUrl : 'comment/commentList.do',
			controller : 'CommentListController'
		});
		
		$routeProvider.when('/column/columnConfig', {
			templateUrl : 'config/columnConfig.do',
			controller : 'ColumnConfigController'
		});
    	
    	$routeProvider.when('/category/categoryConfig', {
			templateUrl : 'config/categoryConfig.do',
			controller : 'CategoryConfigController'
		});
    	
    	// 权限控制
    	$routeProvider.when('/user/userList', {
			templateUrl : 'user/userList.do',
			controller : 'UserListController'
		});
		
		$routeProvider.when('/user/userForm', {
			templateUrl : 'user/userForm.do',
			controller : 'UserFormController'
		});
		
		$routeProvider.when('/role/roleList', {
			templateUrl : 'role/roleList.do',
			controller : 'RoleListController'
		});
		
		$routeProvider.when('/role/roleForm', {
			templateUrl : 'role/roleForm.do',
			controller : 'RoleFormController'
		});
		
		$routeProvider.when('/menu/menuList', {
			templateUrl : 'menu/menuList.do',
			controller : 'MenuListController'
		});
		
		$routeProvider.when('/menu/menuForm', {
			templateUrl : 'menu/menuForm.do',
			controller : 'MenuFormController'
		});
		
		$routeProvider.when('/group/groupConfig', {
			templateUrl : 'config/groupConfig.do',
			controller : 'GroupConfigController'
		});
    	
    	$routeProvider.when('/system/systemConfig', {
			templateUrl : 'config/systemConfig.do',
			controller : 'SystemConfigController'
		});
    	
    	$routeProvider.when('/stat/loginAccess', {
			templateUrl : 'stat/loginAccess.do',
			controller : 'LoginAccessController'
		});
    	
    	$routeProvider.when('/stat/columnAccess', {
			templateUrl : 'stat/columnAccess.do',
			controller : 'ColumnAccessController'
		});
    	
    	$routeProvider.when('/stat/attachmentUpload', {
			templateUrl : 'stat/attachmentUpload.do',
			controller : 'AttachmentUploadController'
		});
    	
    	$routeProvider.when('/attachment/attachmentList', {
			templateUrl : 'attachment/attachmentList.do',
			controller : 'AttachmentListController'
		});
    	
    	$routeProvider.when('/relation/relationCalculate', {
			templateUrl : 'relation/relationCalculate.do',
			controller : 'RelationCalculateController'
		});
    	
		$routeProvider.otherwise( {
			redirectTo : 'index/dashboard.do'
		});
	}]);
});
