define([
	'controllers/controllers',
	'bootstrap',
	'ztree',
	'datetimepicker'
], function (controllers) {
    'use strict';
    controllers.controller('GroupConfigController', ['$scope', '$http', 'CommonService', function ($scope, $http, CommonService) {
    	// 定义全局变量
    	var idGroup = null, $groupTree = $("#groupTree");
    	
    	/*---------------分组------------------*/
    	// 回调函数
    	var callback = function(event, treeId, treeObj) {
    		idGroup = treeObj.id;
    		$scope.actionType = "编辑";
    		renderForm(idGroup);
    	}
    	
    	// 渲染分组树
    	var renderGroupTree = function() {
        	var url = "group/getGroupTree.do";
        	var params = {"idGroup": idGroup};
    		var setting = CommonService.noEventsParamsSetting(false, url, CommonService.dataFilter, callback);
    		$groupTree = $.fn.zTree.init($groupTree, setting);
        }
    	
    	// 执行渲染
    	renderGroupTree();
    	
    	/*---------------表单填写------------------*/
    	$scope.actionType = "新增";
    	// 打开新增表单
    	$scope.addForm = function() {
    		$scope.actionType = "新增";
    		// 判断是否已选择分组
    		if(!idGroup) {
				CommonService.alert("请先选择分组！",function() {});
				return;					
			}
    		$scope.group = {"idParent": idGroup};
    	}
    	
    	// 渲染表单
    	var renderForm = function(idGroup) {
    		$http({
    			method: 'GET',
    			url: 'group/getGroupById.do?idGroup=' + idGroup,
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			$scope.group = response.data;
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
    	}
    	
    	// 刷新子节点
    	var refreshSubNode = function(newIdGroup) {
    		$http({
    			method: 'GET',
    			url: 'group/getGroupById.do?idGroup=' + newIdGroup,
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			var group = response.data;
    			var newNode = {};
    			newNode.pId = group.idParent;
    			newNode.id = group.idGroup;
    			newNode.name = group.groupName;
    			newNode.desc = group.groupDesc;
    			// 动态添加子节点
    			var treeObj = $.fn.zTree.getZTreeObj("groupTree");
    			var nodes = treeObj.getSelectedNodes();
    			treeObj.addNodes(nodes[0], newNode);
    			
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
    	}
    	
    	// 保存分组
		var saveGroup = function(params) {
			$http({
			    method: 'POST',
			    url: 'group/saveGroup.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					var newIdGroup = response.data.data;
					CommonService.alert(response.data.msg,function() {
						refreshSubNode(newIdGroup);
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
						window.location.href = "#/config/groupConfig";
					});
			});
		}
		
		// 更新分组
		var updateGroup = function(params) {
			$http({
			    method: 'POST',
			    url: 'group/updateGroup.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
						var treeObj = $.fn.zTree.getZTreeObj("groupTree");
						var nodes = treeObj.getSelectedNodes();
						if (nodes.length>0) {
							nodes[0].name = $scope.group.groupName;
							treeObj.updateNode(nodes[0]);
						}
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
						window.location.href = "#/config/groupConfig";
					});	
			});
		}
        // 保存或更新分组信息
        $scope.mergeGroup = function() {
			$scope.formData = CommonService.removeExtraAttribute($scope.group);
			// 是否有父ID
			if(!idGroup) {
				CommonService.alert("请选择父分组！",function() {});
				return;					
			} else {
				// 给父ID赋值
				$scope.formData.idParent = idGroup;
			}
			// 新增还是编辑
			if($scope.formData.idGroup) {
				updateGroup($scope.formData);
			} else {
				saveGroup($scope.formData);
			}
        }
        
        // 删除分组信息
		$scope.deleteGroup = function() {
			// 判断是否已选择分组
			if(!$scope.group) {
				CommonService.alert("请先选择分组！",function() {});
				return;					
			}
			var params = {"idGroup" : idGroup};
			CommonService.confirm(function(result) {
				if(result) {
					$http({
						method: 'POST',
						url: 'group/deleteGroup.do',
						headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
						data: $.param(params)
					}).then(function successCallback(response) {
						CommonService.alert(response.data.msg,function() {
							if(response.data.flag) {
								var treeObj = $.fn.zTree.getZTreeObj("groupTree");
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
			$scope.group = {};
		}
    }]);
});
