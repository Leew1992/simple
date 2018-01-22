define([
	'controllers/controllers',
	'bootstrap',
	'ztree',
	'datetimepicker'
], function (controllers) {
    'use strict';
    controllers.controller('ColumnConfigController', ['$scope', '$http', 'CommonService', function ($scope, $http, CommonService) {
    	// 定义全局变量
    	var idColumn = null, $columnTree = $("#columnTree");
    	
    	/*---------------栏目------------------*/
    	// 回调函数
    	var callback = function(event, treeId, treeObj) {
    		idColumn = treeObj.id;
    		$scope.actionType = "编辑";
    		renderForm(idColumn);
    	}
    	
    	// 渲染栏目树
    	var renderColumnTree = function() {
        	var url = "column/getColumnTree.do";
        	var params = {"idColumn": idColumn};
    		var setting = CommonService.noEventsParamsSetting(false, url, CommonService.dataFilter, callback);
    		$columnTree = $.fn.zTree.init($columnTree, setting);
        }
    	
    	// 执行渲染
    	renderColumnTree();
    	
    	/*---------------表单填写------------------*/
    	$scope.actionType = "新增";
    	// 新增表单
    	$scope.addForm = function() {
    		$scope.actionType = "新增";
    		// 判断是否已选择栏目
			if(!idColumn) {
				CommonService.alert("请先选择栏目！",function() {});
				return;					
			}
    		$scope.column = {"idParent": idColumn};
    	}
    	
    	// 渲染表单
    	var renderForm = function(idColumn) {
    		$http({
    			method: 'GET',
    			url: 'column/getColumnById.do?idColumn=' + idColumn,
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			$scope.column = response.data;
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
    	}
    	
    	// 刷新子节点
    	var refreshSubNode = function(newIdColumn) {
    		$http({
    			method: 'GET',
    			url: 'column/getColumnById.do?idColumn=' + newIdColumn,
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			var column = response.data;
    			var newNode = {};
    			newNode.pId = column.idParent;
    			newNode.id = column.idColumn;
    			newNode.name = column.columnName;
    			newNode.desc = column.columnDesc;
    			// 动态添加子节点
    			var treeObj = $.fn.zTree.getZTreeObj("columnTree");
    			var nodes = treeObj.getSelectedNodes();
    			treeObj.addNodes(nodes[0], newNode);
    			
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
    	}
    	
    	// 保存栏目
		var saveColumn = function(params) {
			$http({
			    method: 'POST',
			    url: 'column/saveColumn.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					var newIdColumn = response.data.data;
					CommonService.alert(response.data.msg,function() {
						refreshSubNode(newIdColumn);
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
						window.location.href = "#/config/columnConfig";
					});
			});
		}
		
		// 更新栏目
		var updateColumn = function(params) {
			$http({
			    method: 'POST',
			    url: 'column/updateColumn.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
						var treeObj = $.fn.zTree.getZTreeObj("columnTree");
						var nodes = treeObj.getSelectedNodes();
						if (nodes.length>0) {
							nodes[0].name = $scope.column.columnName;
							treeObj.updateNode(nodes[0]);
						}
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
						window.location.href = "#/config/columnConfig";
					});	
			});
		}
		
        // 保存或更新栏目信息
        $scope.mergeColumn = function() {
			$scope.formData = CommonService.removeExtraAttribute($scope.column);
			// 是否有父ID
			if(!idColumn) {
				CommonService.alert("请选择父栏目！",function() {});
				return;					
			} else {
				// 给父ID赋值
				$scope.formData.idParent = idColumn;
			}
			// 新增还是编辑
			if($scope.formData.idColumn) {
				updateColumn($scope.formData);
			} else {
				saveColumn($scope.formData);
			}
        }
        
        // 删除栏目信息
		$scope.deleteColumn = function() {
			// 判断是否已选择栏目
			if(!$scope.column) {
				CommonService.alert("请先选择栏目！",function() {});
				return;					
			}
			var params = {"idColumn" : idColumn};
			CommonService.confirm(function(result) {
				if(result) {
					$http({
						method: 'POST',
						url: 'column/deleteColumn.do',
						headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
						data: $.param(params)
					}).then(function successCallback(response) {
						CommonService.alert(response.data.msg,function() {
							if(response.data.flag) {
								var treeObj = $.fn.zTree.getZTreeObj("columnTree");
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
			$scope.column = {};
		}
    }]);
});
