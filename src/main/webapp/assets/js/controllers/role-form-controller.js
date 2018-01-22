define([
	'controllers/controllers',
	'bootstrap',
	'ztree',
	'datetimepicker'
], function (controllers) {
    'use strict';
    controllers.controller('RoleFormController', ['$scope', '$http', 'CommonService', function ($scope, $http, CommonService) {
    	// 定义全局变量
    	var idGroup = '0', $groupTree = $("#groupTree");
    	
    	// 获取idRole值
    	var idRole = CommonService.getQueryString("idRole");
    	
    	if(idRole) {
    		$scope.title = "角色编辑";
    		// 填充表单数据
    		$http({
    			method: 'GET',
    			url: 'role/getRoleById.do?idRole=' + idRole,
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			$scope.role = response.data;
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
    	} else {
    		$scope.title = "角色添加";
    	}
    	
    	/*---------------角色分组------------------*/
    	// 渲染分组树
    	var renderGroupTree = function() {
        	var url = "group/getHasCheckedGroupTreeByIdRole.do";
    		var params = ["idRole", idRole];
    		var setting = CommonService.noEventsCallbackSettingForRadio(true, url, params, CommonService.dataFilter);
    		$groupTree = $.fn.zTree.init($groupTree, setting);
        }
    	
    	// 执行渲染
    	renderGroupTree();
    	
    	/*---------------表单填写------------------*/
    	// 保存角色
		var saveRole = function(params) {
			$http({
			    method: 'POST',
			    url: 'role/saveRole.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
						window.location.href = "#/role/roleList";
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
						window.location.href = "#/role/roleList";
					});
			});
		}
		
		// 更新角色
		var updateRole = function(params) {
			$http({
			    method: 'POST',
			    url: 'role/updateRole.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
						window.location.href = "#/role/roleList";
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
						window.location.href = "#/role/roleList";
					});	
			});
		}
		
        // 保存或更新角色信息
        $scope.mergeRole = function() {
			var nodes = $groupTree.getCheckedNodes(true);
			var groupArr = $groupTree.getCheckedNodes(true);
			$scope.formData = CommonService.removeExtraAttribute($scope.role);
			$scope.formData.idGroup = groupArr[0].id;
			
			if($scope.formData.idRole) {
				updateRole($scope.formData);
			} else {
				saveRole($scope.formData);
			}
        }
    }]);
});
