define([
	'controllers/controllers',
	'bootstrap',
	'ztree',
	'datetimepicker',
	'summernote'
], function (controllers) {
    'use strict';
    controllers.controller('PostFormController', ['$scope', '$http', 'fileUpload', 'CommonService', function ($scope, $http, fileUpload, CommonService) {
    	
    	// 获取idPost值
    	var idPost = CommonService.getQueryString("idPost");
    	
    	/*---------------栏目树------------------*/
    	// 定义全局变量
    	var $columnTree = $("#columnTree"), $summernote = $("#summernote");
    	
    	// 渲染栏目树
    	var renderColumnTree = function() {
    		var url = "column/getHasCheckedColumnTreeByIdPost.do";
			var params = ["idPost", idPost];
			var setting = CommonService.noEventsCallbackSetting(true, url, params, CommonService.dataFilter);
			$columnTree = $.fn.zTree.init($columnTree, setting);
    	}
    	
    	// 执行渲染
    	renderColumnTree();
    	
    	/*---------------表单填写------------------*/
    	// 上传成功回调
    	$scope.uploadSuccess = function(data) {
    		$summernote.summernote('insertImage', 'attachment/getImage.do?filePath=' + data.result, data.result);
    	}
    	
    	// 上传失败回调
    	$scope.uploadError = function(data) {
    		console.log("upload error");
    	}
    	
    	// 富文本编辑框初始化
    	$summernote.summernote({
    		placeholder: '',
            tabsize: 2,
            height: 200,
            callbacks: {
                onImageUpload: function(files) {
            		if(files.length > 0) {
            			var uploadUrl = "attachment/uploadImage.do?idPost=" + idPost;
            			fileUpload.uploadFileToUrl(files[0], uploadUrl, $scope.uploadSuccess, $scope.uploadError);
            		}
                }
              }
    	});
    	
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
    				// 内容赋值
    				$summernote.summernote('code', data.content);
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
			$scope.post.content = $summernote.summernote('code');
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
