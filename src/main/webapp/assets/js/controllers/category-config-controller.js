define([
	'controllers/controllers',
	'bootstrap',
	'ztree',
	'datetimepicker'
], function (controllers) {
    'use strict';
    controllers.controller('CategoryConfigController', ['$scope', '$http', 'CommonService', function ($scope, $http, CommonService) {
    	// 定义全局变量
    	var idCategory = null, $categoryTree = $("#categoryTree");
    	
    	/*---------------类别------------------*/
    	// 回调函数
    	var callback = function(event, treeId, treeObj) {
    		idCategory = treeObj.id;
    		$scope.actionType = "编辑";
    		renderForm(idCategory);
    	}
    	
    	// 渲染类别树
    	var renderCategoryTree = function() {
        	var url = "category/getCategoryTree.do";
        	var params = {"idCategory": idCategory};
    		var setting = CommonService.noEventsParamsSetting(false, url, CommonService.dataFilter, callback);
    		$categoryTree = $.fn.zTree.init($categoryTree, setting);
        }
    	
    	// 执行渲染
    	renderCategoryTree();
    	
    	/*---------------表单填写------------------*/
    	$scope.actionType = "新增";
    	// 新增表单
    	$scope.addForm = function() {
    		$scope.actionType = "新增";
    		// 判断是否已选择类别
			if(!idCategory) {
				CommonService.alert("请先选择类别！",function() {});
				return;					
			}
    		$scope.category = {"idParent": idCategory};
    	}
    	
    	// 渲染表单
    	var renderForm = function(idCategory) {
    		$http({
    			method: 'GET',
    			url: 'category/getCategoryById.do?idCategory=' + idCategory,
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			$scope.category = response.data;
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
    	}
    	
    	// 刷新子节点
    	var refreshSubNode = function(newIdCategory) {
    		$http({
    			method: 'GET',
    			url: 'category/getCategoryById.do?idCategory=' + newIdCategory,
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			var category = response.data;
    			var newNode = {};
    			newNode.pId = category.idParent;
    			newNode.id = category.idCategory;
    			newNode.name = category.categoryName;
    			newNode.desc = category.categoryDesc;
    			// 动态添加子节点
    			var treeObj = $.fn.zTree.getZTreeObj("categoryTree");
    			var nodes = treeObj.getSelectedNodes();
    			treeObj.addNodes(nodes[0], newNode);
    			
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
    	}
    	
    	// 保存类别
		var saveCategory = function(params) {
			$http({
			    method: 'POST',
			    url: 'category/saveCategory.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					var newIdCategory = response.data.data;
					CommonService.alert(response.data.msg,function() {
						refreshSubNode(newIdCategory);
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
						window.location.href = "#/config/categoryConfig";
					});
			});
		}
		
		// 更新类别
		var updateCategory = function(params) {
			$http({
			    method: 'POST',
			    url: 'category/updateCategory.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
						var treeObj = $.fn.zTree.getZTreeObj("categoryTree");
						var nodes = treeObj.getSelectedNodes();
						if (nodes.length>0) {
							nodes[0].name = $scope.category.categoryName;
							treeObj.updateNode(nodes[0]);
						}
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
						window.location.href = "#/config/categoryConfig";
					});	
			});
		}
		
        // 保存或更新类别信息
        $scope.mergeCategory = function() {
			$scope.formData = CommonService.removeExtraAttribute($scope.category);
			// 是否有父ID
			if(!idCategory) {
				CommonService.alert("请选择父类别！",function() {});
				return;					
			} else {
				$scope.formData.idParent = idCategory;
			}
			// 新增还是编辑
			if($scope.formData.idCategory) {
				updateCategory($scope.formData);
			} else {
				saveCategory($scope.formData);
			}
        }
        
        // 删除类别信息
		$scope.deleteCategory = function() {
			// 判断是否已选择类别
			if(!$scope.category) {
				CommonService.alert("请先选择类别！",function() {});
				return;					
			}
			var params = {"idCategory" : idCategory};
			CommonService.confirm(function(result) {
				if(result) {
					$http({
						method: 'POST',
						url: 'category/deleteCategory.do',
						headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
						data: $.param(params)
					}).then(function successCallback(response) {
						CommonService.alert(response.data.msg,function() {
							if(response.data.flag) {
								var treeObj = $.fn.zTree.getZTreeObj("categoryTree");
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
			$scope.category = {};
		}
    }]);
});
