define([
	'controllers/controllers',
	'bootstrap',
	'ztree',
	'datetimepicker'
], function (controllers) {
    'use strict';
    controllers.controller('PostFormController', ['$scope', '$http', 'CommonService', function ($scope, $http, CommonService) {
    	
    	// 获取idPost值
    	var idPost = CommonService.getQueryString("idPost");
    	
    	/*---------------栏目树------------------*/
    	// 定义全局变量
    	var $columnTree = $("#columnTree");
    	
    	// 渲染栏目树
    	var renderColumnTree = function() {
    		var url = "column/getColumnTreeForPostForm.do";
			var params = ["idPost", idPost];
			var setting = CommonService.noEventsCallbackSetting(true, url, params, CommonService.dataFilter);
			$columnTree = $.fn.zTree.init($columnTree, setting);
    	}
    	
    	// 执行渲染
    	renderColumnTree();
    	
    	/*---------------表单填写------------------*/
    	// 是否编辑
    	if(idPost) {
    		$.ajax({
    			url : 'post/getPostById.do',
    			type : 'POST',
    			async : false,
    			data : {idPost:idPost},
    			dataType : 'json',
    			success : function(data) {
    				$("#idPost").removeAttr("disabled");
    				$scope.post = data;
    			}
    		});
    	}
    	
    	// 保存贴子信息
    	var savePost = function(params) {
    		$http({
			    method: 'POST',
			    url: 'post/savePost.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
						window.location.href = "#/post/postList";
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
						window.location.href = "#/post/postList";
					});	
			});
    	}
    	
    	// 更新贴子信息
    	var updatePost = function(params) {
    		$http({
			    method: 'POST',
			    url: 'post/updatePost.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
						window.location.href = "#/post/postList";
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
						window.location.href = "#/post/postList";
					});	
			});
    	}
    	
    	// 保存或更新贴子信息
    	$scope.mergePost = function() {
			var idColumnArr = [];
			var nodes = $columnTree.getCheckedNodes(true);
			// 清除非叶子节点
			CommonService.removeNoLeafNodes(nodes);
			var columnArr = $columnTree.getCheckedNodes(true);
			for(var i in columnArr) {
				idColumnArr.push(columnArr[i].id);
			}
			$scope.formData = CommonService.removeExtraAttribute($scope.post);
			$scope.formData.idColumns = idColumnArr.join(",");
			if($scope.formData.idPost) {
				updatePost($scope.formData);
			} else {
				savePost($scope.formData);
			}
    	}
    }]);
});
