define([
	'controllers/controllers',
	'bootstrap',
	'ztree',
	'datetimepicker',
	'bootstrapTable'
], function (controllers) {
    'use strict';
    controllers.controller('RoleListController', ['$scope', '$http', 'CommonService', function ($scope, $http, CommonService) {
    	
    	// 定义全局变量
    	var idGroup = '0', $groupTree = $("#groupTree");
    	
    	/*---------------用户分组------------------*/
    	// 用户列表刷新回调函数
    	var callback = function(event, treeId, treeObj) {
    		idGroup = treeObj.id;
           	$roleTable.bootstrapTable('refresh', null);
    	}
    	
    	// 渲染分组树
    	var renderGroupTree = function() {
    		var url = "group/getGroupTreeForRoleForm.do";
    		var setting = CommonService.noEventsParamsSetting(false,url,CommonService.dataFilter,callback);
    		$groupTree = $.fn.zTree.init($groupTree, setting);    		
    	}
    	
    	// 执行渲染
    	renderGroupTree();
    	
    	//*---------------角色列表------------------*/
		// 定义全局变量
		var $roleTable = $('#roleTable'), $searchRole = $('#searchRole'), selections = [];
		
		// 查询按钮点击事件
		$scope.searchRole = function() {
			$roleTable.bootstrapTable('refresh', null);
		}
		
		// 删除角色信息
		var deleteRole = function(params) {
			$http({
			    method: 'POST',
			    url: 'role/batchDeleteRoles.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
		    			$roleTable.bootstrapTable('refresh', null);
		    		});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
		    			$roleTable.bootstrapTable('refresh', null);
		    		});
			});
		}
		
		// 初始化时间组件
		CommonService.initPairDatetimepicker($(".start-date"), $(".end-date"));
    	
		// 初始化用户列表
		var renderRoleTable = function() {
			$roleTable.bootstrapTable({
				method: 'get',  
				url: 'role/pagingRoles.do',  
				striped: true,
				pagination: true,
				pageSize: 10,  
				pageNumber: 1,
				pageList: [10, 20, 50, 100, 200, 500],  
				idField: "idRole",
				sidePagination: "server",//表格分页的位置  
				queryParams: queryParams, //参数  
				queryParamsType: "", //参数格式,发送标准的RESTFul类型的参数请求  
				silent: true,  //刷新事件必须设置 
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
					field : 'roleName',
					title : '账号',
					align : 'center',
					valign : 'middle'
				}, {
					field : 'roleDesc',
					title : '描述',
					sortable : false,
					align : 'center'
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
			$roleTable.on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', function() {
				$batchRemove.prop('disabled',!$table.bootstrapTable('getSelections').length);
				selections = getIdSelections();
			});
		}
		
		// 获取被选中的节点IDs
		var getIdSelections = function() {
			return $.map($roleTable.bootstrapTable('getSelections'), function(row) {
				return row.idUser;
			});
		}
		
		// 操作格式化
		var operateFormatter = function(value, row, index) {
			return ['\<a style="font-size:13px;color:#000000;" class="assign" href="javascript:void(0)" title="assign">',
					'分配</a>  ',
			        '\<a style="font-size:13px;color:#000000;" class="edit" href="javascript:void(0)" title="edit">',
					'编辑</a>  ',
					'\<a style="font-size:13px;color:#000000;" class="delete" href="javascript:void(0)" title="edit">',
					'删除</a>  '].join('');
		}
		
		// 定义操作事件
		var operateEvents = {
			'click .assign' : function(e, value, row, index) {
				$menuTree.setting.async.url = "menu/getMenuTreeForMenuAssign.do?idRole="+row.idRole;
				$menuTree.reAsyncChildNodes(null,"refresh",false,null);
				$("#assignIdRole").val(row.idRole);
				$("#menuModal").modal('show');
			},
			'click .edit' : function(e, value, row, index) {
				window.location.href = "#/role/roleForm?idRole=" + row.idRole;
			},
			'click .delete' : function(e, value, row, index) {
				CommonService.confirm(function(result) {
					if(result) {
						var params = {idRoles: row.idRole};
						deleteRole(params);
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
			} else {
				params.idGroup = '0';
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
		
		// 渲染角色表格
		renderRoleTable();
		
		//*---------------角色分配------------------*/
		// 定义全局变量
		var $menuTree = $('#menuTree');
		
		// 渲染菜单树
		var renderMenuTree = function() {
			var url = "menu/getMenuTreeForMenuAssign.do";
			var setting = CommonService.noEventsParamsCallbackSetting(true, url, CommonService.dataFilter);
			$menuTree = $.fn.zTree.init($menuTree, setting);
		}
		
		// 分配菜单
		$scope.assignMenus = function() {
    		var nodes = $menuTree.getCheckedNodes(true);
			CommonService.removeNoLeafNodes(nodes);
			var menuArr = $menuTree.getCheckedNodes(true);
			var idMenuArr = [];
			for(var i in menuArr) {
				idMenuArr.push(menuArr[i].id);
			}
			var params = {idRole: $("#assignIdRole").val(), idMenus: idMenuArr.join(",")};
    		
    		$http({
			    method: 'POST',
			    url: 'role/assignMenusForRoleList.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					$("#menuModal").modal('hide');
			    }, function errorCallback(response) {
			    	$("#menuModal").modal('hide');
			});
		}
		// 渲染菜单树
		renderMenuTree();
    }]);
});
