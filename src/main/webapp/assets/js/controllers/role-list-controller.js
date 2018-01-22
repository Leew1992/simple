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
    	var assignedIdRole = "0";
    	
    	/*---------------用户分组------------------*/
    	// 用户列表刷新回调函数
    	var callback = function(event, treeId, treeObj) {
    		idGroup = treeObj.id;
           	$roleTable.bootstrapTable('refresh', null);
    	}
    	
    	// 渲染分组树
    	var renderGroupTree = function() {
    		var url = "group/getHasCheckedGroupTreeByIdRole.do";
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
		
		// 增加角色
		$scope.addRole = function() {
			window.location.href = '#/role/roleForm';
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
			return ['\<a style="font-size:13px;color:#000000;" class="assignSystem" href="javascript:void(0)" title="assign">',
					'分配系统</a>  ',
			        '\<a style="font-size:13px;color:#000000;" class="assignMenu" href="javascript:void(0)" title="assign">',
					'分配菜单</a>  ',
			        '\<a style="font-size:13px;color:#000000;" class="edit" href="javascript:void(0)" title="edit">',
					'编辑</a>  ',
					'\<a style="font-size:13px;color:#000000;" class="delete" href="javascript:void(0)" title="edit">',
					'删除</a>  '].join('');
		}
		
		// 定义操作事件
		var operateEvents = {
			'click .assignSystem' : function(e, value, row, index) {
				$systemTree.setting.async.url = "system/getHasCheckedSystemTreeByIdRole.do?idRole="+row.idRole;
				$systemTree.reAsyncChildNodes(null,"refresh",false,null);
				$("#assignIdRole").val(row.idRole);
				$("#systemModal").modal('show');
			},
			'click .assignMenu' : function(e, value, row, index) {
				$assignedSystemTree.setting.async.url = "system/getSystemTreeByIdRole.do?idRole="+row.idRole;
				$assignedSystemTree.reAsyncChildNodes(null,"refresh",false,null);
				assignedIdRole = row.idRole;
				renderAssignableMenuTree();
				// 移除根节点下面的所有子节点
				var menuTreeObj = $.fn.zTree.getZTreeObj("menuTree");
				var rootNode = menuTreeObj.getNodeByParam("id", 0, null);
				menuTreeObj.removeChildNodes(rootNode);
				
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
		var $systemTree = $('#systemTree');
		var $assignedSystemTree = $('#assignedSystemTree');
		var $menuTree = $('#menuTree');
		var assignedIdSystem = "0";
		var assignedMenusMap = {};
		var reAssignedMenusMap = {};
		
		// 获取被选中的节点Id
		var getCheckedNodeIds = function() {
			var idMenuArr = [];
			for(var key in reAssignedMenusMap) {
				var reAssignedMenus = reAssignedMenusMap[key];
				for(var i in reAssignedMenus) {
					var reAssignedMenu = reAssignedMenus[i];
					if(reAssignedMenu.checked) {
						idMenuArr.push(reAssignedMenu.id);
					}
				}
			}
			return idMenuArr;
		}
		
		// 重置被分配的菜单
		var resetAssginedMenus = function(idSystem, nodes) {
			var reAssignedMenus = reAssignedMenusMap[idSystem];
			var newAssignedMenus = [];
			var nodeIdArr = [];
			// 获取被选中的nodeId
			for(var i in nodes) {
				nodeIdArr.push(nodes[i].id);
			}
			// 重置被选中的节点checked为true
			var nodeIds = nodeIdArr.join(",");
			for(var i in reAssignedMenus) {
				var menuId = reAssignedMenus[i].id;
				var reAssignedMenu = reAssignedMenus[i];
				if(nodeIds.indexOf(menuId) > -1) {
					reAssignedMenu.checked = true;
				} else {
					reAssignedMenu.checked = false;
				}
				newAssignedMenus.push(reAssignedMenu);
			}
			reAssignedMenusMap[idSystem] = newAssignedMenus;
		}
		
		// 分配菜单回调
		var assignedMenuCallback = function(event, treeId, treeNode) {
			var menuTreeObj = $.fn.zTree.getZTreeObj("menuTree");
			var checkedNodes = menuTreeObj.getCheckedNodes(true);
			var reAssignedMenus = checkedNodes.concat(checkedNodes);
			resetAssginedMenus(assignedIdSystem, reAssignedMenus);
		}
		
		// 分配系统回调
		var assginedSystemCallback = function(event, treeId, treeNode) {
			assignedIdSystem = treeNode.id;
			var reAssignedMenus = reAssignedMenusMap[assignedIdSystem];
			var setting = CommonService.noEventsParamsAsyncSetting(true, assignedMenuCallback);
			var menuTreeObj = $.fn.zTree.getZTreeObj("menuTree");
			var rootNode = menuTreeObj.getNodeByParam("id", 0, null);
			menuTreeObj.removeChildNodes(rootNode);
			menuTreeObj.addNodes(rootNode, reAssignedMenus);
    	}
		
		// 渲染可被分配的菜单树
		var renderAssignableMenuTree = function() {
			$http({
    			method: 'GET',
    			url: 'menu/getHasCheckedMenuTreeByIdRole.do?idRole='+assignedIdRole,
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			assignedMenusMap = response.data;
    			reAssignedMenusMap = response.data;
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
		}
		
		// 渲染系统树
		var renderSystemTree = function() {
			var url = "system/getHasCheckedSystemTreeByIdRole.do";
			var setting = CommonService.noEventsParamsCallbackSetting(true, url, CommonService.dataFilter);
			$systemTree = $.fn.zTree.init($systemTree, setting);
		}
		
		// 渲染系统树
		var renderAssignedSystemTree = function() {
			var url = "system/getSystemTreeByIdRole.do";
			var setting = CommonService.noEventsParamsSetting(false, url, CommonService.dataFilter, assginedSystemCallback);
			$assignedSystemTree = $.fn.zTree.init($assignedSystemTree, setting);
		}
		
		// 渲染菜单树
		var renderMenuTree = function() {
			var url = "menu/getHasCheckedMenuTreeByIdRoleAndIdSystem.do";
			var setting = CommonService.noEventsParamsSettingForCheck(true, url, CommonService.dataFilter, assignedMenuCallback);
			$menuTree = $.fn.zTree.init($menuTree, setting);
		}
		
		// 分配系统
		$scope.assignSystems = function() {
    		var nodes = $systemTree.getCheckedNodes(true);
			CommonService.removeNoLeafNodes(nodes);
			var systemArr = $systemTree.getCheckedNodes(true);
			var idSystemArr = [];
			for(var i in systemArr) {
				idSystemArr.push(systemArr[i].id);
			}
			var params = {idRole: $("#assignIdRole").val(), idSystems: idSystemArr.join(",")};
    		
    		$http({
			    method: 'POST',
			    url: 'role/assignSystemsForRole.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {});
					$("#systemModal").modal('hide');
			    }, function errorCallback(response) {
			    	$("#systemModal").modal('hide');
			});
		}
		
		// 分配菜单
		$scope.assignMenus = function() {
			var idMenuArr = getCheckedNodeIds();
			var params = {idRole: $("#assignIdRole").val(), idMenus: idMenuArr.join(",")};
			
    		$http({
			    method: 'POST',
			    url: 'role/assignMenusForRole.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {});
					$("#menuModal").modal('hide');
			    }, function errorCallback(response) {
			    	$("#menuModal").modal('hide');
			});
		}
		// 渲染菜单树
		renderMenuTree();
		// 渲染系统树
		renderSystemTree();
		// 渲染已分配的系统树
		renderAssignedSystemTree();
    }]);
});
