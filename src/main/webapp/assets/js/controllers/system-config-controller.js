define([
	'controllers/controllers',
	'bootstrap',
	'ztree',
	'datetimepicker'
], function (controllers) {
    'use strict';
    controllers.controller('SystemConfigController', ['$scope', '$http', 'CommonService', function ($scope, $http, CommonService) {
    	// 定义全局变量
    	var idSystem = null, $systemTree = $("#systemTree");
    	
    	/*---------------系统------------------*/
    	// 回调函数
    	var callback = function(event, treeId, treeObj) {
    		idSystem = treeObj.id;
    		$scope.actionType = "编辑";
    		renderForm(idSystem);
    	}
    	
    	// 渲染系统树
    	var renderSystemTree = function() {
        	var url = "system/getSystemTree.do";
    		var setting = CommonService.noEventsParamsSetting(false, url, CommonService.dataFilter, callback);
    		$systemTree = $.fn.zTree.init($systemTree, setting);
        }
    	
    	// 执行渲染
    	renderSystemTree();
    	
    	/*---------------表单填写------------------*/
    	$scope.actionType = "新增";
    	// 新增表单
    	$scope.addForm = function() {
    		$scope.actionType = "新增";
    		// 判断是否已选择系统
			if(!idSystem) {
				CommonService.alert("请先选择系统！",function() {});
				return;					
			}
    		$scope.system = {"idParent": idSystem};
    	}
    	
    	// 渲染表单
    	var renderForm = function(idSystem) {
    		$http({
    			method: 'GET',
    			url: 'system/getSystemById.do?idSystem=' + idSystem,
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			$scope.system = response.data;
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
    	}
    	
    	// 刷新子节点
    	var refreshSubNode = function(newIdSystem) {
    		$http({
    			method: 'GET',
    			url: 'system/getSystemById.do?idSystem=' + newIdSystem,
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			var system = response.data;
    			var newNode = {};
    			newNode.pId = system.idParent;
    			newNode.id = system.idSystem;
    			newNode.name = system.systemName;
    			newNode.desc = system.systemDesc;
    			// 动态添加子节点
    			var treeObj = $.fn.zTree.getZTreeObj("systemTree");
    			var nodes = treeObj.getSelectedNodes();
    			treeObj.addNodes(nodes[0], newNode);
    			
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
    	}
    	
    	// 保存系统
		var saveSystem = function(params) {
			$http({
			    method: 'POST',
			    url: 'system/saveSystem.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					var newIdSystem = response.data.data;
					CommonService.alert(response.data.msg,function() {
						refreshSubNode(newIdSystem);
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
						window.location.href = "#/config/systemConfig";
					});
			});
		}
		
		// 更新系统
		var updateSystem = function(params) {
			$http({
			    method: 'POST',
			    url: 'system/updateSystem.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
						var treeObj = $.fn.zTree.getZTreeObj("systemTree");
						var nodes = treeObj.getSelectedNodes();
						if (nodes.length>0) {
							nodes[0].name = $scope.system.systemName;
							treeObj.updateNode(nodes[0]);
						}
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
						window.location.href = "#/config/systemConfig";
					});	
			});
		}
		
        // 保存或更新系统信息
        $scope.mergeSystem = function() {
			$scope.formData = CommonService.removeExtraAttribute($scope.system);
			// 是否有父ID
			if(!idSystem) {
				CommonService.alert("请选择父系统！",function() {});
				return;					
			} else {
				$scope.formData.idParent = idSystem;
			}
			// 新增还是编辑
			if($scope.formData.idSystem) {
				updateSystem($scope.formData);
			} else {
				saveSystem($scope.formData);
			}
        }
        
        // 删除系统信息
		$scope.deleteSystem = function() {
			// 判断是否已选择系统
			if(!$scope.system) {
				CommonService.alert("请先选择系统！",function() {});
				return;					
			}
			var params = {"idSystem" : idSystem};
			CommonService.confirm(function(result) {
				if(result) {
					$http({
						method: 'POST',
						url: 'system/deleteSystem.do',
						headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
						data: $.param(params)
					}).then(function successCallback(response) {
						CommonService.alert(response.data.msg,function() {
							if(response.data.flag) {
								var treeObj = $.fn.zTree.getZTreeObj("systemTree");
								var nodes = treeObj.getSelectedNodes();
								if (nodes.length>0) {
									treeObj.removeNode(nodes[0]);
								}
							}
						});
					}, function errorCallback(response) {
						CommonService.alert(response.data.msg,function() {});
					});
				}
			});
			// 重置表单
			$scope.system = {};
		}
    }]);
});
