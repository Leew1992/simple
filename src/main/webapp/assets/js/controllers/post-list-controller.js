define([
	'controllers/controllers',
	'bootstrap',
	'ztree',
	'datetimepicker',
	'bootstrapTable'
], function (controllers) {
    'use strict';
    controllers.controller('PostListController', ['$scope', '$http', 'CommonService', function ($scope, $http, CommonService) {
    	
    	// 定义全局变量
    	var idColumn = '0', $columnTree = $("#columnTree");
    	var $postTable = $('#postTable'), selections = [];
    	
    	/*---------------栏目树------------------*/
    	// 回调函数
    	var callback = function(event, treeId, treeObj) {
    		idColumn = treeObj.id;
           	$('#postTable').bootstrapTable('refresh', null);
    	}
    	
    	// 渲染栏目树
    	var renderColumnTree = function() {
    		var url = "column/getColumnTree.do";
			var setting = CommonService.noEventsParamsSetting(false,url,CommonService.dataFilter,callback);
			$columnTree = $.fn.zTree.init($columnTree, setting);
    	}
    	
    	// 执行渲染
    	renderColumnTree();
    	
    	/*---------------贴子列表------------------*/
    	// 查询按钮点击事件
		$scope.searchPost = function() {
			$postTable.bootstrapTable('refresh', null);
		}
		
		// 增加贴子
		$scope.addPost = function() {
			window.location.href = '#/post/postForm';			
		}
		
		// 初始化时间组件
		CommonService.initPairDatetimepicker($(".start-date"), $(".end-date"));
    	
		// 隐藏模态框
    	$("#myModal").on("hidden.bs.modal", function() {
    		$("#myModal :input").val('');
    	});
    	
    	// 删除贴子信息
    	var deletePost = function(params) {
    		$http({
			    method: 'POST',
			    url: 'post/batchDeletePosts.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
						$postTable.bootstrapTable('refresh', null);
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
			    		$postTable.bootstrapTable('refresh', null);
			    	});	
			});
    	}
		
    	// 初始化贴子列表
    	var renderPostTable = function() {
    		$postTable.bootstrapTable({
    			method: 'get',  
    			url: 'post/pagingPosts.do',  
    			striped: true,
    			pagination: true,
    			pageSize: 10,  
    			pageNumber: 1,
    			pageList: [10, 20, 50, 100, 200, 500],  
    			idField: "idPost",
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
    				field : 'title',
    				title : '标题',
    				align : 'center',
    				valign : 'middle'
    			}, {
    				field : 'content',
    				title : '内容',
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
    		$postTable.on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', function() {
    			$batchRemove.prop('disabled',!$postTable.bootstrapTable('getSelections').length);
    			selections = getIdSelections();
    		});
    	}
    	
    	// 获取被选中的节点IDs
    	var getIdSelections = function() {
    		return $.map($postTable.bootstrapTable('getSelections'), function(row) {
    			return row.idPost;
    		});
    	}
    	
    	// 操作格式化
    	var operateFormatter = function(value, row, index) {
    		return ['\<a style="font-size:13px;color:#000000;" class="edit" href="javascript:void(0)" title="edit">',
    				'编辑</a>  ',
    				'<a style="font-size:13px;color:#000000;" class="remove" href="javascript:void(0)" title="Remove">',
    				'删除</a>' ].join('');
    	}
    	
    	// 定义操作事件
    	var operateEvents = {
    		'click .edit' : function(e, value, row, index) {
    			window.location.href = "#/post/postForm?idPost=" + row.idPost;
    		},
    		'click .remove' : function(e, value, row, index) {
    			CommonService.confirm(function(result) {
    				if(result) {
						var params = {idPosts: row.idPost};
						deletePost(params);
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
    		if (idColumn) {
    			params.idColumn = idColumn;
    		} else {
    			params.idColumn = '0';
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
    	
    	// 渲染贴子表格
		renderPostTable();
    }]);
});
