define([
	'controllers/controllers',
	'bootstrap',
	'ztree',
	'datetimepicker',
	'bootstrapTable'
], function (controllers) {
    'use strict';
    controllers.controller('LoginAccessController', ['$scope', '$http', '$timeout', 'CommonService', function ($scope, $http, $timeout, CommonService) {
    	
    	// 加载访问数据
		$scope.renderLoginAccess = function(dateDimension, nums) {
			$http({
    			method: 'GET',
    			url: 'stat/getStatAccessChart.do?dateDimension=' + dateDimension + '&moduleType=login',
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			renderSiteAccessInRound(response.data);
    			renderSiteAccessInLine(response.data);
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
		}
		
		// 渲染网站访问
		var renderSiteAccessInRound = function(data) {
			// 延迟加载（0.2秒）
			$timeout(function(){
				var myChartE = document.getElementById("PieChart");
				if(myChartE) {
					var ctx = myChartE.getContext('2d');
					var myChart = new Chart(ctx, {
						type: 'pie',
						data: {
							labels: data.labels,
							datasets: [{
								label: data.datasets[0].label,
								backgroundColor: ['rgba(100, 100, 100, 0.2)'],
								borderColor: ['rgba(100, 100, 100, 1)'],
								data: data.datasets[0].data
							}]
						},
						options: {}
					});
				}
			},200);
		}
		
		// 渲染网站访问
		var renderSiteAccessInLine = function(data) {
			// 延迟加载（0.2秒）
			$timeout(function(){
				var myChartE = document.getElementById("LineChart");
				if(myChartE) {
					var ctx = myChartE.getContext('2d');
					var myChart = new Chart(ctx, {
						type: 'line',
						data: {
							labels: data.labels,
							datasets: [{
								label: data.datasets[0].label,
								data: data.datasets[0].data,
								backgroundColor: ['rgba(100, 100, 100, 0.2)'],
								borderColor: ['rgba(100, 100, 100, 1)'],
								borderWidth: 1
							}]
						},
						options: {
							scales: {
								yAxes: [{
									ticks: {
										beginAtZero:true
									}
								}]
							}
						}
					});
				}
			},200);
		}
		
		// 初始化渲染
		var initRender = function() {
			$scope.renderLoginAccess('day', 7);
		}
		
		// 执行渲染
		initRender();
		
    }]);
});
