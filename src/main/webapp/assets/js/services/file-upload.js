define(['services/services'], function (services) {
    'use strict';
    services.service('fileUpload', ['$http', function($http){
    	this.uploadFileToUrl = function(file, uploadUrl, success, error){
            var fd = new FormData();
            fd.append('file', file);
            $http.post(uploadUrl, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).success(function(data){
            	success(data);
            }).error(function(){
            	error(data);
            });
        }
    }])
});
