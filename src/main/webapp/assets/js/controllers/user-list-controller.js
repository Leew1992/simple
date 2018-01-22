define([
	'controllers/controllers',
	'bootstrap',
	'ztree',
	'datetimepicker',
	'bootstrapTable'
], function (controllers) {
    'use strict';
    controllers.controller('UserListController', ['$scope', '$http', 'CommonService', function ($scope, $http, CommonService) {
    	
    	// 定义全局变量
    	var idGroup = null, $groupTree = $("#groupTree");
    	
    	/*---------------用户分组------------------*/
    	// 用户列表刷新回调函数
    	var callback = function(event, treeId, treeObj) {
    		idGroup = treeObj.id;
           	$userTable.bootstrapTable('refresh', null);
    	}
    	
    	// 渲染分组树
    	var renderGroupTree = function() {
    		var url = "group/getGroupTree.do";
    		var setting = CommonService.noEventsParamsSetting(false,url,CommonService.dataFilter,callback);
    		$groupTree = $.fn.zTree.init($groupTree, setting);    		
    	}
    	
    	// 执行渲染
    	renderGroupTree();
	
		/*---------------角色分配------------------*/
    	// 定义全局变量
    	var $roleTree = $("#roleTree");
    	
    	// 分配角色
    	var assignRoles = function(params) {
    		$http({
			    method: 'POST',
			    url: 'user/assignRolesForUserList.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					$("#roleModal").modal('hide');
					CommonService.alert(response.data.msg,function() {});
			    }, function errorCallback(response) {
			    	$("#roleModal").modal('hide');
			    	CommonService.alert(response.data.msg,function() {});
			});
    	}
    	
		// 分配角色点击事件
		$scope.saveRoleAssign = function() {
			var nodes = $roleTree.getCheckedNodes(true);
			CommonService.removeNoLeafNodes(nodes);
			var roleArr = $roleTree.getCheckedNodes(true);
			var idRoleArr = [];
			for(var i in roleArr) {
				idRoleArr.push(roleArr[i].id);
			}
			var params = {idUser: $("#assignIdUser").val(), idRoles: idRoleArr.join(",")};
			assignRoles(params);
		}
		
		// 渲染角色树
		var renderRoleTree = function() {
			var url = "role/getRoleTreeForUserList.do";
			var setting = CommonService.noEventsParamsCallbackSetting(true, url, CommonService.dataFilter);
			$roleTree = $.fn.zTree.init($roleTree, setting);			
		}
		
		// 执行渲染
		renderRoleTree();
		
		//*---------------用户列表------------------*/
		// 定义全局变量
		var $userTable = $('#userTable'), $searchUser = $('#searchUser'), $addUser = $('#addUser'), selections = [];
		
		// 查询按钮点击事件
		$scope.searchUser = function() {
			$userTable.bootstrapTable('refresh', null);
		}
		
		// 增加用户
		$scope.addUser = function() {
			window.location.href = '#/user/userForm';			
		}
		
		// 删除用户信息
		var deleteUser = function(params) {
			$http({
			    method: 'POST',
			    url: 'user/batchDeleteUsers.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
		    			$userTable.bootstrapTable('refresh', null);
		    		});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
		    			$userTable.bootstrapTable('refresh', null);
		    		});
			});
		}
		
		// 初始化时间组件
		CommonService.initPairDatetimepicker($(".start-date"), $(".end-date"));
		
		// 初始化用户列表
		var renderUserTable = function() {
			$userTable.bootstrapTable({
				method: 'get',  
				url: 'user/pagingUsers.do',  
				striped: true,
				pagination: true,
				pageSize: 10,  
				pageNumber: 1,
				pageList: [10, 20, 50, 100, 200, 500],  
				idField: "idUser",
				sidePagination: "server",// 表格分页的位置  
				queryParams: queryParams, // 参数  
				queryParamsType: "", // 参数格式,发送标准的RESTFul类型的参数请求  
				silent: true,  // 刷新事件必须设置 
				formatLoadingMessage: function () {  
			      return "请稍等，正在加载中...";  
			    },
			    formatNoMatches: function () {  //没有匹配的结果  
			      return '无符合条件的记录';  
			    },
				columns : [ [ {
					field : 'state',
					checkbox : true,
					align : 'center',
					valign : 'middle'
				}, {
					field : 'userName',
					title : '账号',
					align : 'center',
					valign : 'middle'
				}, {
					field : 'userNick',
					title : '昵称',
					sortable : false,
					align : 'center'
				}, {
					field : 'gender',
					title : '性别',
					sortable : false,
					align : 'center',
					formatter : genderFormatter
				}, {
					field : 'createdDate',
					title : '创建时间',
					sortable : true,
					align : 'center'
				}, {
					field : 'updatedDate',
					title : '更新时间',
					sortable : true,
					align : 'center'
				}, {
					field : 'operate',
					title : '操作',
					align : 'center',
					events : operateEvents,
					formatter : operateFormatter
				} ] ]
			});
			$userTable.on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', function() {
				$('#batchRemove').prop('disabled',!$userTable.bootstrapTable('getSelections').length);
				selections = getIdSelections();
			});
		}
		
		// 获取被选中的节点IDs
		var getIdSelections = function() {
			return $.map($userTable.bootstrapTable('getSelections'), function(row) {
				return row.idUser;
			});
		}
		
		// 性别格式化
		var genderFormatter = function(value, row, index) {
			if(value=='1') {
				return '男';
			}
			if(value=='0') {
				return '女';
			}
			if(value=='2') {
				return '其它';
			}
		}
		
		// 操作格式化
		var operateFormatter = function(value, row, index) {
			return ['\<a style="font-size:13px;color:#000000;" class="assignRole" href="javascript:void(0)" title="assign">',
					'分配角色</a>  ',
			        '\<a style="font-size:13px;color:#000000;" class="edit" href="javascript:void(0)" title="edit">',
					'编辑</a>  ',
					'\<a style="font-size:13px;color:#000000;" class="delete" href="javascript:void(0)" title="edit">',
					'删除</a>  '].join('');
		}
		
		// 定义操作事件
		var operateEvents = {
			'click .assignRole' : function(e, value, row, index) {
				$roleTree.setting.async.url = "role/getRoleTreeForUserList.do?idUser="+row.idUser;
				$roleTree.reAsyncChildNodes(null,"refresh",false,null);
				$("#assignIdUser").val(row.idUser);
				$("#roleModal").modal('show');
			},
			'click .edit' : function(e, value, row, index) {
				window.location.href = "#/user/userForm?idUser=" + row.idUser;
			},
			'click .delete' : function(e, value, row, index) {
				CommonService.confirm(function(result) {
					if(result) {
						var params = {idUsers: row.idUser};
						deleteUser(params);
					}
				});
			}
		};
		
		// 构建查询参数
		var queryParams = function(params) {
			var startDate = $('.start-date').val();
			var endDate = $('.end-date').val();
			var keyWord = $('.keyWord').val();
			// 字典编码
			if (idGroup) {
				params.idGroup = idGroup;
			}
			// 查询时间
			if (startDate) {
				params.startDate = startDate;
			}
			if (endDate) {
				params.endDate = endDate;
			}
			// 关键字
			if (keyWord) {
				params.keyWord = keyWord;
			}
			return params;
		}
		
		// 执行渲染
		renderUserTable();
    }]);
});
