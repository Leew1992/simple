define([
	'controllers/controllers',
	'bootstrap',
	'ztree',
	'datetimepicker',
	'bootstrapTable'
], function (controllers) {
    'use strict';
    controllers.controller('AttachmentListController', ['$scope', '$http', 'CommonService', function ($scope, $http, CommonService) {
    	
    	// 定义全局变量
    	var dateValue = null, $dateTree = $("#dateTree");
    	
    	/*---------------日期树------------------*/
    	
    	// 回调函数
    	var callback = function(event, treeId, treeObj) {
    		dateValue = treeObj.id;
    		$attachmentTable.bootstrapTable('refresh', null);
    	}
    	
    	// 渲染日期树
    	var renderDateTree = function() {
    		var url = "menu/getMonthMenuTree.do?startMonth=201701";
			var setting = CommonService.noEventsParamsSetting(false,url,CommonService.dataFilter,callback);
			$dateTree = $.fn.zTree.init($dateTree, setting);
    	}
    	
    	// 执行渲染
    	renderDateTree();
    	
    	/*---------------评论列表------------------*/
    	// 定义全局变量 
    	var $attachmentTable = $('#attachmentTable'), selections = [];
    	
    	// 查询按钮点击事件
		$scope.searchAttachment = function() {
			$attachmentTable.bootstrapTable('refresh', null);
		}
		
		// 渲染附件信息
		var getAttachmentDetail = function(idAttachment) {
			// 填充表单数据
    		$http({
    			method: 'GET',
    			url: 'attachment/getAttachmentById.do?idAttachment=' + idAttachment,
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			$scope.attachment = response.data;
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
		}
    	
    	// 隐藏模态框
    	$("#myModal").on("hidden.bs.modal", function() {
    		$("#myModal :input").val('');
    	});
    	
    	// 删除评论信息
    	var deleteAttachment = function(params) {
    		$http({
			    method: 'POST',
			    url: 'date/batchDeleteAttachments.do',
			    headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
			    data: $.param(params)
			}).then(function successCallback(response) {
					CommonService.alert(response.data.msg,function() {
						$attachmentTable.bootstrapTable('refresh', null);
					});
			    }, function errorCallback(response) {
			    	CommonService.alert(response.data.msg,function() {
			    		$attachmentTable.bootstrapTable('refresh', null);
			    	});	
			});
    	}
    	
		// 初始化时间组件
		CommonService.initPairDatetimepicker($(".start-date"), $(".end-date"));
    	
    	// 初始化评论列表
    	var renderAttachmentTable = function() {
    		$attachmentTable.bootstrapTable({
    			method: 'get',  
    			url: 'attachment/pagingAttachments.do',  
    			striped: true,
    			pagination: true,
    			pageSize: 10,  
    			pageNumber: 1,
    			pageList: [10, 20, 50, 100, 200, 500],  
    			idField: "idAttachment",
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
    				field : 'attachName',
    				title : '名称',
    				align : 'center',
    				valign : 'middle'
    			},{
    				field : 'attachSize',
    				title : '大小',
    				sortable : false,
    				align : 'center'
    			},{
    				field : 'attachPath',
    				title : '路径',
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
    		$attachmentTable.on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', function() {
    			$batchDelete.prop('disabled',!$attachmentTable.bootstrapTable('getSelections').length);
    			selections = getIdSelections();
    		});
    	}
    	
    	// 获取被选中的节点IDs
    	var getIdSelections = function() {
    		return $.map($attachmentTable.bootstrapTable('getSelections'), function(row) {
    			return row.idAttachment;
    		});
    	}
    	
    	// 操作格式化
    	var operateFormatter = function(value, row, index) {
    		return ['\<a style="font-size:13px;color:#000000;" class="view" href="javascript:void(0)" title="visiable">',
    				'查看</a>  ',
    				'<a style="font-size:13px;color:#000000;" class="remove" href="javascript:void(0)" title="invisiable">',
    				'删除</a>' ].join('');
    	}
    	
    	// 定义操作事件
    	var operateEvents = {
    		'click .view' : function(e, value, row, index) {
    			getAttachmentDetail(row.idAttachment);
    			$("#attachmentModal").modal('show');
    		},
    		'click .remove' : function(e, value, row, index) {
    			CommonService.confirm(function(result) {
    				if(result) {
    					$attachmentTable.bootstrapTable('remove', {
    						field : 'id',
    						values : [ row.id ]
    					});
    					var params = {idAttachments: row.idAttachment};
    					deleteAttachment(params);
    				}
    			});
    		}
    	};
    	
    	// 构建查询参数
        function queryParams(params) {
        	var startDate = $('.start-date').val();
        	var endDate = $('.end-date').val();
        	var keyWord = $('.keyWord').val();
        	// 字典编码
			if (dateValue) {
				params.dateValue = dateValue;
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
    	renderAttachmentTable();
    }]);
});
