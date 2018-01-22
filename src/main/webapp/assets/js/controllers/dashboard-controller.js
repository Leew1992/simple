define([ 
	'controllers/controllers',
	'Chart'
], function(controllers, Chart) {
	'use strict';
	controllers.controller('DashboardController', [ '$scope', '$http', '$timeout', 'CommonService', function($scope, $http, $timeout, CommonService) {
		
		// 渲染网站数据
		$scope.renderSiteData = function(dateDimension) {
			$http({
    			method: 'GET',
    			url: 'stat/getStatData.do?dateDimension=' + dateDimension,
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			$scope.statData = response.data;
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
		}
		
		// 渲染栏目访问
		$scope.renderColumnAccess = function(dateDimension) {
			$http({
    			method: 'GET',
    			url: 'stat/getStatAccess.do?dateDimension=' + dateDimension,
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			$scope.statAccess = response.data;
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
		}
		
		// 渲染网站访问
		var loadSiteAccess = function() {
			$http({
    			method: 'GET',
    			url: 'stat/getStatDayAccessForLogin.do?dateDimension=day',
    			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
    		}).then(function successCallback(response) {
    			renderSiteAccess(response.data);
    		}, function errorCallback(response) {
    			console.log(response.msg);
    		});
		}
		
		var renderSiteAccess = function(data) {
			// 延迟加载（0.2秒）
			$timeout(function(){
				var myChartE = document.getElementById("myChart");
				if(myChartE) {
					var ctx = myChartE.getContext('2d');
					var myChart = new Chart(ctx, {
						type: 'line',
						data: {
							labels: data.labels,
							datasets: [{
								label: data.datasets[0].label,
								data: data.datasets[0].data,
								backgroundColor: [
								                  'rgba(100, 100, 100, 0.2)'
								                  ],
								                  borderColor: [
								                                'rgba(100, 100, 100, 1)'
								          
								                                ],
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
		var initRender = function(){
			$scope.renderSiteData('day');
			$scope.renderColumnAccess('day');
			loadSiteAccess();
		}
		
		// 执行渲染
		initRender();
		
	} ]);
});
