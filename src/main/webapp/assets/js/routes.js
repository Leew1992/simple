define(['./app'], function (app) {
    'use strict';
    return app.config(['$routeProvider', function ($routeProvider) {
    	$routeProvider.when('/index/dashboard', {
			templateUrl : 'views/dashboard.html',
			controller : 'IndexController'
		});
    	
    	$routeProvider.when('/user/userList', {
			templateUrl : 'views/user-list.html',
			controller : 'UserListController'
		});
		
		$routeProvider.when('/user/userForm', {
			templateUrl : 'views/user-form.html',
			controller : 'UserFormController'
		});
		
		$routeProvider.when('/role/roleList', {
			templateUrl : 'views/role-list.html',
			controller : 'RoleListController'
		});
		
		$routeProvider.when('/role/roleForm', {
			templateUrl : 'views/role-form.html',
			controller : 'RoleFormController'
		});
		
		$routeProvider.when('/menu/menuList', {
			templateUrl : 'views/menu-list.html',
			controller : 'MenuListController'
		});
		
		$routeProvider.when('/menu/menuForm', {
			templateUrl : 'views/menu-form.html',
			controller : 'MenuFormController'
		});
		
		$routeProvider.when('/post/postList', {
			templateUrl : 'views/post-list.html',
			controller : 'PostListController'
		});
		
		$routeProvider.when('/post/postForm', {
			templateUrl : 'views/post-form.html',
			controller : 'PostFormController'
		});
		
		$routeProvider.when('/comment/commentList', {
			templateUrl : 'views/comment-list.html',
			controller : 'CommentListController'
		});
		
		$routeProvider.when('/config/groupConfig', {
			templateUrl : 'views/group-config.html',
			controller : 'GroupConfigController'
		});
    	
    	$routeProvider.when('/config/systemConfig', {
			templateUrl : 'views/system-config.html',
			controller : 'SystemConfigController'
		});
    	
    	$routeProvider.when('/config/columnConfig', {
			templateUrl : 'views/column-config.html',
			controller : 'ColumnConfigController'
		});
    	
    	$routeProvider.when('/config/categoryConfig', {
			templateUrl : 'views/category-config.html',
			controller : 'CategoryConfigController'
		});
		
		$routeProvider.otherwise( {
			redirectTo : '/index/dashboard'
		});
	}]);
});
