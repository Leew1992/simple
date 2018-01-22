define([
	'services/services',
	'bootbox'
], function (services, bootbox) {
    'use strict';
    
    services.service('CommonService',  [function(){
    	return {
    		/********************** common ************************/
    		str2DomWithDiv : function(str) {
    			var objE = document.createElement("div"); 
    			objE.innerHTML = str; 
    			return objE; 
    		},
    		getQueryString : function(name) {
    	    	var paramStr = window.location.href.split('?')[1];
    	    	var result = "";
    	    	if(paramStr) {
    	    		var paramArr = paramStr.split("&");
    	    		var paramMap = {};
    	    		for(var i = 0; i < paramArr.length; i ++) {
    	    			var param = paramArr[i].split("=");
    	    			paramMap[param[0]] = param[1];
    	    		}
    	    		result = paramMap[name];
    	    	}
    	    	return result;
    		},
    		getUrlAnchor : function() {
    	    	return '#' + window.location.href.split('#')[1];
    		},
    		dateFormat : function(fmt,date) { 	   //author: meizz
    			var date = new Date();
    			var o = {
    		      "M+" : date.getMonth()+1,                 //月份   
    			  "d+" : date.getDate(),                    //日   
    			  "h+" : date.getHours(),                   //小时   
    			  "m+" : date.getMinutes(),                 //分   
    			  "s+" : date.getSeconds(),                 //秒   
    			  "q+" : Math.floor((date.getMonth()+3)/3), //季度   
    			  "S"  : date.getMilliseconds()             //毫秒   
    			};
    			if(/(y+)/.test(fmt)) {
    				fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   		  
    			}   
    			for(var k in o) {
    				if(new RegExp("("+ k +")").test(fmt)) {
    					fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   	    	
    				}
    			}
    			return fmt;   
    		},
    		
    		/********************** plugin ************************/
    		alert : function(msg,op) {
    			bootbox.alert({
    			   size: "small",
    			   title: "操作提示",
    			   message: msg, 
    			   callback: op
    			});
    		},
    		confirm : function(op) {
    			bootbox.confirm({
    				title: "操作提示",
    				message: "确认是否执行此操作？",
    				size: "small",
    				buttons: {
    					cancel: { label: '取消' },
    					confirm: { label: '确认' }
    				},
    				callback: op
    			});		
    		},
    		
    		/********************** table ************************/
    		initTableCheckBox : function() {
    			var selectArr = [];
    			var $thr = $('table thead tr');  
    			var $checkAllTh = $('<th><input type="checkbox" id="checkAll" name="checkAll" /></th>');  
    			$thr.prepend($checkAllTh);  
    			var $checkAll = $thr.find('input');  
    			$checkAll.click(function(event){  
    				$tbr.find('input').prop('checked',$(this).prop('checked'));  
    				if ($(this).prop('checked')) {  
    					$tbr.find('input').parent().parent().addClass('warning');  
    				} else{  
    					$tbr.find('input').parent().parent().removeClass('warning');  
    				}
    				event.stopPropagation();  
    			});  
    			var $tbr = $('table tbody tr');  
    			var $checkItemTd = $('<td><input type="checkbox" name="checkItem" /></td>');  
    			$tbr.prepend($checkItemTd);  
    			$tbr.find('input').click(function(event){
    				$(this).parent().parent().toggleClass('warning');  
    				$checkAll.prop('checked',$tbr.find('input:checked').length == $tbr.length ? true : false);  
    				if($(event.target).prop('checked') && $tbr.find('input:checked').length == $tbr.length) {
    				} else if($(event.target).prop('checked') && $tbr.find('input:checked').length == 0) {
    				} else if($(event.target).prop('checked') && $tbr.find('input:checked').length != $tbr.length) {
    					selectArr.push($(event.target).parents('tr').attr('id')); 
    				} else {
    					selectArr.pop($(event.target).parents('tr').attr('id')); 
    				}
    				event.stopPropagation();  
    			});
    		},
    		removeNoLeafNodes : function(nodes) {
    			if(nodes){
    		        for(var i=0;i<nodes.length;i++){
    		            var nodeChildrens=nodes[i].children;
    	                if(nodeChildrens){
    	                	nodes[i].nocheck=true;
    	                }
    		        }
    		    }
    		},
    		renderTable : function(getScript,initTable) {
    			var scripts = [];
    			var eachSeries = function(arr, iterator, callback) {
    				callback = callback || function() {
    				};
    				if (!arr.length) {
    					return callback();
    				}
    				var completed = 0;
    				var iterate = function() {
    					iterator(arr[completed], function(err) {
    						if (err) {
    							callback(err);
    							callback = function() {
    							};
    						} else {
    							completed += 1;
    							if (completed >= arr.length) {
    								callback(null);
    							} else {
    								iterate();
    							}
    						}
    					});
    				};
    				iterate();
    			};

    			eachSeries(scripts, getScript, initTable);
    		},
    		getScript : function(url, callback) {
    			var head = document.getElementsByTagName('head')[0];
    			var script = document.createElement('script');
    			script.src = url;

    			var done = false;
    			script.onload = script.onreadystatechange = function() {
    				if (!done
    						&& (!this.readyState || this.readyState == 'loaded' || this.readyState == 'complete')) {
    					done = true;
    					if (callback)
    						callback();
    					script.onload = script.onreadystatechange = null;
    				}
    			};

    			head.appendChild(script);
    			return undefined;
    		},
    		
    		/********************** tree ************************/
    		noEventsParamsAsyncSetting : function (enable, callback) {
    			var setting = {
					check : {
						enable : enable
					},
					view : {
						dblClickExpand : false,
						showLine : true,
						selectedMulti : false
					},
					data : {
						simpleData : {
							enable : true,
							idKey : "id",
							pIdKey : "pId",
							rootPId : null
						}
					},
					callback: {
						onClick: callback
					}	
    			};
    			return setting;
			},
    		noEventsParamsCallbackSetting : function (enable,url,dataFilter) {
    			var setting = {
    				check : {
    					enable : enable
    				},
    				view : {
    					dblClickExpand : false,
    					showLine : true,
    					selectedMulti : false
    				},
    				async: {
    					enable: true,
    					url: url,
    					dataFilter: dataFilter,
    					type: "get"
    				},
    				data : {
    					simpleData : {
    						enable : true,
    						idKey : "id",
    						pIdKey : "pId",
    						rootPId : null
    					}
    				}
    			};
    			return setting;
    		},
    		noEventsParamsSetting : function (enable,url,dataFilter,callback) {
    			var setting = {
    				check : {
    					enable : enable
    				},
    				view : {
    					dblClickExpand : false,
    					showLine : true,
    					selectedMulti : false
    				},
    				async: {
    					enable: true,
    					url: url,
    					dataFilter: dataFilter,
    					type: "get"
    				},
    				data : {
    					simpleData : {
    						enable : true,
    						idKey : "id",
    						pIdKey : "pId",
    						rootPId : null
    					}
    				},
    				callback : {
    					onClick : callback
    				}
    			};
    			return setting;
    		},
    		noEventsParamsSettingForCheck : function (enable,url,dataFilter,callback) {
    			var setting = {
    				check : {
    					enable : enable
    				},
    				view : {
    					dblClickExpand : false,
    					showLine : true,
    					selectedMulti : false
    				},
    				async: {
    					enable: true,
    					url: url,
    					dataFilter: dataFilter,
    					type: "get"
    				},
    				data : {
    					simpleData : {
    						enable : true,
    						idKey : "id",
    						pIdKey : "pId",
    						rootPId : null
    					}
    				},
    				callback : {
    					onCheck : callback
    				}
    			};
    			return setting;
    		},
    		noEventsCallbackSetting : function (enable,url,params,dataFilter) {
    			var setting = {
    				check : {
    					enable : enable
    				},
    				view : {
    					dblClickExpand : false,
    					showLine : true,
    					selectedMulti : false
    				},
    				async: {
    					enable: true,
    					url: url,
    					otherParam: params,
    					dataFilter: dataFilter,
    					type: "get"
    				},
    				data : {
    					simpleData : {
    						enable : true,
    						idKey : "id",
    						pIdKey : "pId",
    						rootPId : null
    					}
    				}
    			};
    			return setting;
    		},
    		noEventsCallbackSettingForRadio : function (enable,url,params,dataFilter) {
    			var setting = {
    				check : {
    					enable : enable,
    					chkStyle : "radio",
    					radioType: "all"
    				},
    				view : {
    					dblClickExpand : false,
    					showLine : true,
    					selectedMulti : false
    				},
    				async: {
    					enable: true,
    					url: url,
    					otherParam: params,
    					dataFilter: dataFilter,
    					type: "get"
    				},
    				data : {
    					simpleData : {
    						enable : true,
    						idKey : "id",
    						pIdKey : "pId",
    						rootPId : null
    					}
    				}
    			};
    			return setting;
    		},
    		noEventsParamsCallbackSettingForRadio : function (enable,url,dataFilter) {
    			var setting = {
    				check : {
    					enable : enable,
    					chkStyle : "radio",
    					radioType: "all"
    				},
    				view : {
    					dblClickExpand : false,
    					showLine : true,
    					selectedMulti : false
    				},
    				async: {
    					enable: true,
    					url: url,
    					dataFilter: dataFilter,
    					type: "get"
    				},
    				data : {
    					simpleData : {
    						enable : true,
    						idKey : "id",
    						pIdKey : "pId",
    						rootPId : null
    					}
    				}
    			};
    			return setting;
    		},
    		noEventsSetting : function (enable,url,params,dataFilter,callback) {
    			var setting = {
    				check : {
    					enable : enable
    				},
    				view : {
    					dblClickExpand : false,
    					showLine : true,
    					selectedMulti : false
    				},
    				async: {
    					enable: true,
    					url: url,
    					otherParam: params,
    					dataFilter: dataFilter,
    					type: "get"
    				},
    				data : {
    					simpleData : {
    						enable : true,
    						idKey : "id",
    						pIdKey : "pId",
    						rootPId : null
    					}
    				},
    				callback : {
    					onClick : callback
    				}
    			};
    			return setting;
    		},
    		noParamsSetting : function (enable,addHoverDom,removeHoverDom,url,dataFilter,callback) {
    			var setting = {
    				check : {
    					enable : enable
    				},
    				view : {
    					addHoverDom : addHoverDom,
    					removeHoverDom : removeHoverDom,
    					dblClickExpand : false,
    					showLine : true,
    					selectedMulti : false
    				},
    				async: {
    					enable: true,
    					url: url,
    					dataFilter: dataFilter,
    					type: "get"
    				},
    				data : {
    					simpleData : {
    						enable : true,
    						idKey : "id",
    						pIdKey : "pId",
    						rootPId : null
    					}
    				},
    				callback : {
    					onClick : callback
    				}
    			};
    			return setting;
    		},
    		setting : function (enable,addHoverDom,removeHoverDom,url,params,dataFilter,callback) {
    			var setting = {
    				check : {
    					enable : enable
    				},
    				view : {
    					addHoverDom : addHoverDom,
    					removeHoverDom : removeHoverDom,
    					dblClickExpand : false,
    					showLine : true,
    					selectedMulti : false
    				},
    				async: {
    					enable: true,
    					url: url,
    					otherParam: params,
    					dataFilter: dataFilter,
    					type: "get"
    				},
    				data : {
    					simpleData : {
    						enable : true,
    						idKey : "id",
    						pIdKey : "pId",
    						rootPId : null
    					}
    				},
    				callback : {
    					onClick : callback
    				}
    			};
    			return setting;
    		},
        	dataFilter : function (treeId, parentNode, responseData) {
        		var root = {"id":"0","name":"全部","open":"true","pId":"-1","desc":"全部"};
        		responseData.push(root);
        		return responseData;
        	},
        	pureDataFilter : function (treeId, parentNode, responseData) {
        		var nodes = eval("(" + responseData.data + ")");
        		return nodes;
        	},
        	
        	/********************** datetimepicker ************************/
        	initOneDatetimepicker: function($date) {
        		$date.datetimepicker({
        			minView : '2',
        			format : 'yyyy-mm-dd',
        			autoclose : true,
        			todayBtn : true,
        			pickerPosition:'top-right'
        		});
        	},
        	initOneDatetimepicker: function($date, pickerPosition) {
        		$date.datetimepicker({
        			minView : '2',
        			format : 'yyyy-mm-dd',
        			autoclose : true,
        			todayBtn : true,
        			pickerPosition: pickerPosition
        		});
        	},
        	initPairDatetimepicker: function($startDate, $endDate) {
        		// 设置时间组件格式
        		this.initOneDatetimepicker($startDate);
        		this.initOneDatetimepicker($endDate);
        		
        		// 设置时间组件change事件
        		$startDate.datetimepicker().on('changeDate', function(ev){
        			$endDate.datetimepicker('setStartDate', ev.date);        		    
        		});
        		$endDate.datetimepicker().on('changeDate', function(ev){
        			$startDate.datetimepicker('setEndDate', ev.date);
        		});
        	},
        	
        	/********************** datetimepicker ************************/
            removeExtraAttribute: function(obj) {
            	delete obj.createdBy;
            	delete obj.createdDate;
            	delete obj.updatedBy;
            	delete obj.updatedDate;
            	return obj;
            }
    	}
    }])
});
