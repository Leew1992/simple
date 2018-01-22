define([
	'controllers/controllers',
	'bootstrap',
	'ztree',
	'datetimepicker'
], function (controllers) {
    'use strict';
    controllers.controller('UserFormController', ['$scope', '$http', 'CommonService', 'fileUpload', function ($scope, $http, CommonService, fileUpload) {
    	
    	// 定义全局变量
    	var idGroup = '0';
    	
    	/*---------------是否编辑------------------*/
    	// 获取idUser值
    	var idUser = CommonService.getQueryString("idUser");
    	
    	// 判断添加或编辑
    	if(idUser) {
    		$scope.title = "用户编辑";
    		// 填充表单数据
    		$http({
    			method: 'GET',
    			url: 'user/getUserById.do?idUser=' + idUser,
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			$scope.user = response.data;
    			$("#avatarImg").attr('src', 'attachment/getImage.do?filePath=' + $scope.user.avatarUrl);
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
    	} else {
    		$scope.title = "用户添加";
    	}
    	
    	/*---------------用户分组------------------*/
    	// 定义全局变量
    	var $groupTree = $("#groupTree");
    	
    	// 渲染分组树
    	var renderGroupTree = function() {
        	var url = "group/getHasCheckedGroupTreeByIdUser.do";
    		var params = ["idUser", idUser];
    		var setting = CommonService.noEventsCallbackSetting(true, url, params, CommonService.dataFilter);
    		$groupTree = $.fn.zTree.init($groupTree, setting);
        }
    	
    	// 执行渲染
    	renderGroupTree();
    	
    	/*---------------头像上传------------------*/
    	// 上传成功回调
    	$scope.uploadSuccess = function(data) {
    		$("#avatarImg").attr('src', 'attachment/getImage.do?filePath=' + data.result);
    	}
    	
    	// 上传失败回调
    	$scope.uploadError = function(data) {
    		console.log("upload error");
    	}
    	
    	// 文件上传
    	$scope.uploadFile = function() {
    		var file = $scope.myFile;
    		if(file) {
    			var uploadUrl = "attachment/uploadImage.do";
    			fileUpload.uploadFileToUrl(file, uploadUrl, $scope.uploadSuccess, $scope.uploadError);
    		}
    	}
    	
    	// 选择文件
    	$scope.selectFile = function(){
    		$("#myFile").click();
        };
        
    	/*---------------表单填写------------------*/
        // 日期插件初始化
        CommonService.initOneDatetimepicker($('#birthday'), 'top-right');
        
        // 保存用户
		var saveUser = function(params) {
			$http({
			    method: 'POST',
			    url: 'user/saveUser.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
						window.location.href = "#/user/userList";
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
						window.location.href = "#/user/userList";
					});
			});
		}
		
		// 更新用户
		var updateUser = function(params) {
			$http({
			    method: 'POST',
			    url: 'user/updateUser.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
						window.location.href = "#/user/userList";
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
						window.location.href = "#/user/userList";
					});	
			});
		}
        
        // 保存或更新用户信息
        $scope.mergeUser = function() {
        	var avatarUrl = $('#avatarImg').attr('src').split('=')[1];
			var nodes = $groupTree.getCheckedNodes(true);
			CommonService.removeNoLeafNodes(nodes);
			var groupArr = $groupTree.getCheckedNodes(true);
			var idGroupArr = [];
			for(var i in groupArr) {
				idGroupArr.push(groupArr[i].id);
			}
			$scope.user.avatarUrl = avatarUrl;
			$scope.formData = CommonService.removeExtraAttribute($scope.user);
			$scope.formData.idGroups = idGroupArr.join(",");
			
			if($scope.formData.idUser) {
				updateUser($scope.formData);
			} else {
				saveUser($scope.formData);
			}
        }
    }]);
});
