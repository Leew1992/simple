define([
	'controllers/controllers',
	'bootstrap',
	'ztree',
	'datetimepicker',
	'bootstrapTable'
], function (controllers) {
    'use strict';
    controllers.controller('CommentListController', ['$scope', '$http', 'CommonService', function ($scope, $http, CommonService) {
    	
    	// 定义全局变量
    	var idCategory = '0', $categoryTree = $("#categoryTree");
    	
    	/*---------------类别树------------------*/
    	
    	// 回调函数
    	var callback = function(event, treeId, treeObj) {
    		idCategory = treeObj.id;
    		$commentTable.bootstrapTable('refresh', null);
    	}
    	
    	// 渲染类别树
    	var renderCategoryTree = function() {
    		var url = "category/getCategoryTree.do";
			var setting = CommonService.noEventsParamsSetting(false,url,CommonService.dataFilter,callback);
			$categoryTree = $.fn.zTree.init($categoryTree, setting);
    	}
    	
    	// 执行渲染
    	renderCategoryTree();
    	
    	/*---------------评论列表------------------*/
    	// 定义全局变量 
    	var $commentTable = $('#commentTable'), selections = [];
    	
    	// 查询按钮点击事件
		$scope.searchComment = function() {
			$commentTable.bootstrapTable('refresh', null);
		}
    	
    	// 隐藏模态框
    	$("#myModal").on("hidden.bs.modal", function() {
    		$("#myModal :input").val('');
    	});
    	
    	// 删除评论信息
    	var deleteComment = function(params) {
    		$http({
			    method: 'POST',
			    url: 'comment/batchDeleteComments.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
						$commentTable.bootstrapTable('refresh', null);
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
			    		$commentTable.bootstrapTable('refresh', null);
			    	});	
			});
    	}
    	
    	// 置顶评论信息
    	var stick = function(params) {
    		$http({
			    method: 'POST',
			    url: 'comment/stickCommentById.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
						$commentTable.bootstrapTable('refresh', null);
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
			    		$commentTable.bootstrapTable('refresh', null);
			    	});	
			});
    	}
    	
		// 初始化时间组件
		CommonService.initPairDatetimepicker($(".start-date"), $(".end-date"));
    	
    	// 初始化评论列表
    	var renderCommentTable = function() {
    		$commentTable.bootstrapTable({
    			method: 'get',  
    			url: 'comment/pagingComments.do',  
    			striped: true,
    			pagination: true,
    			pageSize: 10,  
    			pageNumber: 1,
    			pageList: [10, 20, 50, 100, 200, 500],  
    			idField: "idComment",
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
    				title : '贴子',
    				align : 'center',
    				valign : 'middle'
    			}, {
    				field : 'status',
    				title : '状态',
    				sortable : false,
    				align : 'center',
    				formatter : statusFormatter
    			}, {
    				field : 'content',
    				title : '评论',
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
    		$commentTable.on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', function() {
    			$batchDelete.prop('disabled',!$commentTable.bootstrapTable('getSelections').length);
    			selections = getIdSelections();
    		});
    	}
    	
    	// 获取被选中的节点IDs
    	var getIdSelections = function() {
    		return $.map($commentTable.bootstrapTable('getSelections'), function(row) {
    			return row.idComment;
    		});
    	}
    	
    	// 状态格式化
    	var statusFormatter = function(value, row, index) {
    		if(value=='0') {
    			return '隐藏';
    		}
    		if(value=='1') {
    			return '显示';
    		}
    	}
    	
    	// 操作格式化
    	var operateFormatter = function(value, row, index) {
    		return ['\<a style="font-size:13px;color:#000000;" class="stick" href="javascript:void(0)" title="visiable">',
    				'置顶</a>  ',
    				'<a style="font-size:13px;color:#000000;" class="remove" href="javascript:void(0)" title="invisiable">',
    				'删除</a>' ].join('');
    	}
    	
    	// 定义操作事件
    	var operateEvents = {
    		'click .stick' : function(e, value, row, index) {
    			CommonService.confirm(function(result) {
    				if(result) {
    					$commentTable.bootstrapTable('remove', {
    						field : 'id',
    						values : [ row.id ]
    					});
    					var param = {idComment: row.idComment};
    					stick(param);
    				}
    			});
    		},
    		'click .remove' : function(e, value, row, index) {
    			CommonService.confirm(function(result) {
    				if(result) {
    					$commentTable.bootstrapTable('remove', {
    						field : 'id',
    						values : [ row.id ]
    					});
    					var params = {idComments: row.idComment};
    					deleteComment(params);
    				}
    			});
    		}
    	};
    	
    	// 构建查询参数
        function queryParams(params) {
        	var idCategory = idCategory;
        	var startDate = $('.start-date').val();
        	var endDate = $('.end-date').val();
        	var keyWord = $('.keyWord').val();
        	// 字典编码
        	if (idCategory) {
        		params.idCategory = idCategory;
        	} else {
        		params.idCategory = '0';
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
    	renderCommentTable();
    }]);
});
